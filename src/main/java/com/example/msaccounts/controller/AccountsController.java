package com.example.msaccounts.controller;

import com.example.msaccounts.config.AccountsServiceConfig;
import com.example.msaccounts.model.Account;
import com.example.msaccounts.model.Customer;
import com.example.msaccounts.model.Properties;
import com.example.msaccounts.repository.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AccountsController {

    private final AccountRepository accountsRepository;
    private final AccountsServiceConfig accountsConfig;

    public AccountsController(AccountRepository accountsRepository, AccountsServiceConfig accountsConfig) {
        this.accountsRepository = accountsRepository;
        this.accountsConfig = accountsConfig;
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
}
