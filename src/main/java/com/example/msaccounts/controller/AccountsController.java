package com.example.msaccounts.controller;

import com.example.msaccounts.model.Account;
import com.example.msaccounts.model.Customer;
import com.example.msaccounts.repository.AccountRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    private final AccountRepository accountsRepository;

    public AccountsController(AccountRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @PostMapping("/myAccount")
    public Account getAccountDetails(@RequestBody Customer customer) {
        return accountsRepository.findByCustomerId(customer.getCustomerId());
    }
}
