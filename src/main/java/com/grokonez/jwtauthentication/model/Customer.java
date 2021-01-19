package com.grokonez.jwtauthentication.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer", uniqueConstraints = {

        @UniqueConstraint(columnNames = {
                "email"
        })
})

public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="first_Name")
    private String firstName;

    @Column(name="last_Name")
    private String lastName;

    @Column(name="email", nullable=false, length=200)
    private String email;



    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Customer(String firstName, String lastName, String email,String accType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Column(name="account_type")
    private String accountType;



}
