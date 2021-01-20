package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.message.request.AccountStatementRequest;
import com.grokonez.jwtauthentication.message.request.TransferBalanceRequest;
import com.grokonez.jwtauthentication.message.request.accountForm;
import com.grokonez.jwtauthentication.message.request.transactionPdfRequest;
import com.grokonez.jwtauthentication.model.Account;
import com.grokonez.jwtauthentication.model.AccountStatement;
import com.grokonez.jwtauthentication.model.Transaction;
import com.grokonez.jwtauthentication.security.services.AccountService;
import com.grokonez.jwtauthentication.security.services.TransPdfExporter;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/create")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public List<Account> create(@Valid @RequestBody accountForm account) {
        Account account1 = new Account(account.getAccountNumber(),account.getCurrentBalance(),account.getAccountType(),account.getCustomerId());
        accountService.save(account1);
        return accountService.findAll();
    }

    @RequestMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public List<Account> all() {
        return accountService.findAll();
    }

    @RequestMapping("/sendmoney")
            @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> sendMoney(
            @Valid  @RequestBody TransferBalanceRequest transferBalanceRequest
    ) {
        TransferBalanceRequest tl = new TransferBalanceRequest(transferBalanceRequest.getFromAccountNumber(),transferBalanceRequest.getToAccountNumber(),transferBalanceRequest.getAmount());
        accountService.sendMoney(tl);
        return ResponseEntity.ok().body("transferred money");
    }
    @RequestMapping("/statement")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<AccountStatement> getStatement(
            @Valid @RequestBody AccountStatementRequest accountStatementRequest

    ){
        AccountStatementRequest accountRequest = new AccountStatementRequest(accountStatementRequest.getAccountNumber(),accountStatementRequest.getAccountType());
        return ResponseEntity.ok().body(
                accountService.getStatement(accountRequest.getAccountNumber(),accountRequest.getAccountType())
        );

    }


    @GetMapping("/pdf")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public void exportToPDF(HttpServletResponse response, @Valid @RequestBody transactionPdfRequest transactionPdfRequest) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transaction_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Transaction> listTransaction = accountService.findTransactionByTransactionDateTimeBetween(transactionPdfRequest.getDate1(),transactionPdfRequest.getDate2());
         TransPdfExporter tran = new TransPdfExporter(listTransaction);
            tran.export(response);

    }


    @RequestMapping("/interest")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String> getInterest(
            @Valid @RequestBody AccountStatementRequest accountStatementRequest

    ){
        double interestRate =3.5;

        AccountStatementRequest accountRequest = new AccountStatementRequest(accountStatementRequest.getAccountNumber(),accountStatementRequest.getAccountType());
        AccountStatement accStmt = accountService.getStatement(accountRequest.getAccountNumber(),accountRequest.getAccountType());
        BigDecimal principle = accStmt.getCurrentBalance();
        double annualInterest = ((principle.doubleValue() * interestRate * 1) / 100);
        double totalBalance = principle.doubleValue() + annualInterest;


        return ResponseEntity.ok().body("Annual Interest :: " + annualInterest + " Total balance :: "+ totalBalance) ;

    }
}
