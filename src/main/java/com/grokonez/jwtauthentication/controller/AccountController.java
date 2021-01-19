package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.AccountStatementRequest;
import com.grokonez.jwtauthentication.message.request.TransferBalanceRequest;
import com.grokonez.jwtauthentication.message.request.accountForm;
import com.grokonez.jwtauthentication.message.request.customerForm;
import com.grokonez.jwtauthentication.message.response.Response;
import com.grokonez.jwtauthentication.model.Account;
import com.grokonez.jwtauthentication.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/create")
    public List<Account> create(@Valid @RequestBody accountForm account) {
        Account account1 = new Account(account.getAccountNumber(),account.getCurrentBalance(),account.getAccType(),account.getCustomerId());
        accountService.save(account1);
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
