package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.AccountStatementRequest;
import com.grokonez.jwtauthentication.message.request.TransferBalanceRequest;
import com.grokonez.jwtauthentication.message.request.accountForm;
import com.grokonez.jwtauthentication.model.Account;
import com.grokonez.jwtauthentication.model.AccountStatement;
import com.grokonez.jwtauthentication.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/create")
    public List<Account> create(@Valid @RequestBody accountForm account) {
        Account account1 = new Account(account.getAccountNumber(),account.getCurrentBalance(),account.getAccountType(),account.getCustomerId());
        accountService.save(account1);
        return accountService.findAll();
    }

    @RequestMapping("/all")
    public List<Account> all() {
        return accountService.findAll();
    }

    @RequestMapping("/sendmoney")
    public ResponseEntity<String> sendMoney(
            @Valid  @RequestBody TransferBalanceRequest transferBalanceRequest
    ) {
        TransferBalanceRequest tl = new TransferBalanceRequest(transferBalanceRequest.getFromAccountNumber(),transferBalanceRequest.getToAccountNumber(),transferBalanceRequest.getAmount());
        accountService.sendMoney(tl);
        return ResponseEntity.ok().body("transferred money");
    }
    @RequestMapping("/statement")
    public ResponseEntity<AccountStatement> getStatement(
            @Valid @RequestBody AccountStatementRequest accountStatementRequest

    ){
        AccountStatementRequest accountRequest = new AccountStatementRequest(accountStatementRequest.getAccountNumber(),accountStatementRequest.getAccountType());
        return ResponseEntity.ok().body(
                accountService.getStatement(accountRequest.getAccountNumber(),accountRequest.getAccountType())
        );

    }

}
