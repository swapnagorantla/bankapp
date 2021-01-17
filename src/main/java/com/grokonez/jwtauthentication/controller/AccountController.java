package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.AccountStatementRequest;
import com.grokonez.jwtauthentication.message.request.TransferBalanceRequest;
import com.grokonez.jwtauthentication.message.response.Response;
import com.grokonez.jwtauthentication.model.Account;
import com.grokonez.jwtauthentication.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/create")
    public List<Account> create(@RequestBody Account account) {
        accountService.save(account);
        return accountService.findAll();
    }

    @RequestMapping("/all")
    public List<Account> all() {
        return accountService.findAll();
    }

    @RequestMapping("/sendmoney")
    public Response sendMoney(
            @RequestBody TransferBalanceRequest transferBalanceRequest
    ) {

        return Response.ok().setPayload(accountService.sendMoney(
                        transferBalanceRequest
                )
        );
    }
    @RequestMapping("/statement")
    public Response getStatement(
            @RequestBody AccountStatementRequest accountStatementRequest

    ){
        return Response.ok().setPayload(
                accountService.getStatement(accountStatementRequest.getAccountNumber())
        );

    }

}
