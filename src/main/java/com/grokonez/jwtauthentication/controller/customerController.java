package com.grokonez.jwtauthentication.controller;

import com.grokonez.jwtauthentication.Exception.RecordNotFoundException;
import com.grokonez.jwtauthentication.message.request.customerForm;
import com.grokonez.jwtauthentication.model.Customer;
import com.grokonez.jwtauthentication.security.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")

public class customerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> list = customerService.getAllCustomers();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        Optional<Customer> entity = customerService.getCustomerbyId(id);

        return new ResponseEntity<Customer>(entity.get(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody customerForm customer)
            throws RecordNotFoundException {
        Customer customer1= new Customer(customer.getFirstName(),customer.getLastName(),customer.getEmail(),customer.getAccountType());

        Customer updated = customerService.saveCustomer(customer1);
        return new ResponseEntity<Customer>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<String>  deleteCustomer(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        customerService.deleteCustomerById(id);
        return  ResponseEntity.ok().body("Customer Deleted successfully!");
    }

}
