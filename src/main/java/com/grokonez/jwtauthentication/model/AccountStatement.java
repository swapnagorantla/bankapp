package com.grokonez.jwtauthentication.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountStatement {
    BigDecimal currentBalance;

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    List<Transaction> transactionHistory;

    public AccountStatement(BigDecimal currentBalance, List<Transaction> transactionList) {
        this.currentBalance = currentBalance;
       Transaction tran = new Transaction();
       List<Transaction> trans = new ArrayList<>();
        transactionHistory = new ArrayList<>();

       for(Transaction tr : transactionList){
           tran.setAccountNumber(tr.getAccountNumber());
           tran.setTransactionAmount(tr.getTransactionAmount());
           tran.setTransactionDateTime(tr.getTransactionDateTime());
       }
       trans.add(tran);
        System.out.println(trans.size());
        transactionHistory.addAll(trans);
    }
}
