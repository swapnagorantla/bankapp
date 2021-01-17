package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.Exception.RecordNotFoundException;
import com.grokonez.jwtauthentication.model.Customer;
import com.grokonez.jwtauthentication.security.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")

public class customerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> list = customerService.getAllCustomers();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        Optional<Customer> entity = customerService.getCustomerbyId(id);

        return new ResponseEntity<Customer>(entity.get(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Customer> saveCustomer(Customer customer)
            throws RecordNotFoundException {
        Customer updated = customerService.saveCustomer(customer);
        return new ResponseEntity<Customer>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteCustomer(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        customerService.deleteCustomerById(id);
        return HttpStatus.FORBIDDEN;
    }

}
