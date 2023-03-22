package com.example.msaccounts.client;

import com.example.msaccounts.model.Cards;
import com.example.msaccounts.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("cards")
public interface CardsFeignClient {

    @PostMapping(value = "/myCards", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Cards> getCardDetails(@RequestBody Customer customer);
}
