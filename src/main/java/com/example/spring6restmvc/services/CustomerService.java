package com.example.spring6restmvc.services;

import com.example.spring6restmvc.models.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();

    Customer getCustomer(UUID id);
}
