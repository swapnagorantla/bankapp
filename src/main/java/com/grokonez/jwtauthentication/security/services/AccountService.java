package com.grokonez.jwtauthentication.security.services;

import com.grokonez.jwtauthentication.message.request.TransferBalanceRequest;
import com.grokonez.jwtauthentication.model.Account;
import com.grokonez.jwtauthentication.model.AccountStatement;
import com.grokonez.jwtauthentication.model.Transaction;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    Transaction sendMoney(
            TransferBalanceRequest transferBalanceRequest
    );
    AccountStatement getStatement(String accountNumber,String accountType);
    List<Transaction> findAllTransactions();
}
