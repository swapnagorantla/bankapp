package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountNumberEquals(String accountNumber);

}