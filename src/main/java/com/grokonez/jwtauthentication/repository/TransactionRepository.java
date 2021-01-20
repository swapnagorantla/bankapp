package com.grokonez.jwtauthentication.repository;

import com.grokonez.jwtauthentication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberEquals(String accountNumber);
    List<Transaction> findTransactionByTransactionDateTimeBetween(Timestamp date1 , Timestamp date2);
}
