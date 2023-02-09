package com.example.msaccounts.repository;

import com.example.msaccounts.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByCustomerId(int customerId);
}
