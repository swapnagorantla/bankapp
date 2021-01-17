package com.grokonez.jwtauthentication.model;
import org.hibernate.type.BigDecimalType;

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

    private BigDecimalType transactionAmount;

    private Timestamp transactionDateTime;

    public Transaction(long l, String fromAccountNumber, BigDecimal amount, Timestamp timestamp) {
    }
}