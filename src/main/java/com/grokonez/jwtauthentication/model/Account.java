package com.grokonez.jwtauthentication.model;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

    public Account(String accountNumber, BigDecimal currentBalance,String accountType,String customerId) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Id
    @GeneratedValue
    private Long accountId;

    String accountNumber;

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    BigDecimal currentBalance;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    String accountType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    String customerId;




}