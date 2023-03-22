package com.example.msaccounts.client;

import com.example.msaccounts.model.Customer;
import com.example.msaccounts.model.Loans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("loans")
public interface LoansFeignClient {

    @PostMapping(value = "/myLoans", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Loans> getLoansDetails(@RequestBody Customer customer);
}
