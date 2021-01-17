package com.grokonez.jwtauthentication.model;

import java.math.BigDecimal;
import java.util.List;

public class AccountStatement {
    BigDecimal currentBalance;
    List<Transaction> transactionHistory;

    public AccountStatement(BigDecimal currentBalance, List<Transaction> byAccountNumberEquals) {
    }
}
