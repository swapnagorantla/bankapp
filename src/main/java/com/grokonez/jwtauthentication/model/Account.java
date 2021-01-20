package com.grokonez.jwtauthentication.model;
import javax.persistence.*;


import java.math.BigDecimal;



@Entity
@Table(name = "account", uniqueConstraints = {

        @UniqueConstraint(columnNames = {
                "account_number"
        })
})

public class Account {

    public Account(String accountNumber, BigDecimal currentBalance,String accountType,Long customerId) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public Account() {

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

    @Column(name="account_number")
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    Long customerId;




}