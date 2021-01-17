package com.grokonez.jwtauthentication.security.services;

import com.grokonez.jwtauthentication.Exception.RecordNotFoundException;
import com.grokonez.jwtauthentication.model.Customer;
import com.grokonez.jwtauthentication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {


    @Autowired
    CustomerRepository repository;

    public List<Customer> getAllCustomers()
    {
        List<Customer> customerList = repository.findAll();

        if(customerList.size() > 0) {
            return customerList;
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Customer> getCustomerbyId(Long id) throws RecordNotFoundException
    {
        Optional<Customer> customer = repository.findById(id);

        if(null != customer) {
            return customer;
        } else {
            throw new RecordNotFoundException("No Account exist for given id");
        }
    }

    @Transactional
    public void deleteCustomerById(Long id) throws RecordNotFoundException
    {
        Optional<Customer> customer;
        customer = repository.findById(id);

        if(null != customer)
        {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }


    public Customer saveCustomer(Customer entity) throws RecordNotFoundException
    {
        return repository.save(entity);
    }

}


