package com.example.msaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomerDetails {

    private Account account;
    private List<Loans> loans;
    private List<Cards> cards;
}
