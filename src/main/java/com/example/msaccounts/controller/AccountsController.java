package com.example.msaccounts.controller;

import com.example.msaccounts.client.CardsFeignClient;
import com.example.msaccounts.client.LoansFeignClient;
import com.example.msaccounts.config.AccountsServiceConfig;
import com.example.msaccounts.model.*;
import com.example.msaccounts.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AccountsController {

    private final AccountRepository accountsRepository;
    private final AccountsServiceConfig accountsConfig;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    public AccountsController(AccountRepository accountsRepository, AccountsServiceConfig accountsConfig, CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountsRepository = accountsRepository;
        this.accountsConfig = accountsConfig;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {
        return accountsRepository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
        return ow.writeValueAsString(properties);
    }

    @PostMapping("/myCustomerDetails")
//    @CircuitBreaker(name = "detailsForCustomerSupport", fallbackMethod = "myCustomerDetailsFallback")
    @Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallback")
    public CustomerDetails myCustomerDetails(@RequestBody Customer customer) {
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(customer);
        List<Cards> cards = cardsFeignClient.getCardDetails(customer);

        return new CustomerDetails(account, loans, cards);
    }

    private CustomerDetails myCustomerDetailsFallback(Customer customer, Throwable t) {
        Account account = accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans = loansFeignClient.getLoansDetails(customer);

        return new CustomerDetails(account, loans, List.of());
    }
}
