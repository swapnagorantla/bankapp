package com.grokonez.jwtauthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    private Long transactionId;

    private String accountNumber;

    public Transaction() {

    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Timestamp getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Timestamp transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    private BigDecimal transactionAmount;

    private Timestamp transactionDateTime;

    public Transaction(long l, String fromAccountNumber, BigDecimal amount, Timestamp timestamp) {
        this.accountNumber = fromAccountNumber;
        this.transactionAmount = amount;
        this.transactionDateTime = timestamp;
    }
}