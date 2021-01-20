package com.grokonez.jwtauthentication.message.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class transactionPdfRequest {

    private Timestamp date1;
    private Timestamp date2;

    public transactionPdfRequest(Timestamp date1, Timestamp date2) {
        this.date1 = date1;
        this.date2 = date2;
    }
}
