package com.example.spring6restmvc.controllers;

import com.example.spring6restmvc.entities.Customer;
import com.example.spring6restmvc.mappers.CustomerMapper;
import com.example.spring6restmvc.models.CustomerDTO;
import com.example.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testPatchCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testPatchCustomerById() {
        testSaveNewCustomer();

        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        final String customerName = "UPDATED";

        customerDTO.setId(null);
        customerDTO.setVersion(null);
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer patchedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(patchedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerById() {
        testSaveNewCustomer();

        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Test
    void testUpdateCustomerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExistingCustomer() {
        testSaveNewCustomer();

        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        final String customerName = "UPDATED";

        customerDTO.setId(null);
        customerDTO.setVersion(null);
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();

        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);
    }

    @Test
    void testSaveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(location[4]);

        Customer customer = customerRepository.findById(savedUUID).get();

        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testGetCustomerById() {
        testSaveNewCustomer();

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO dto = customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }

    @Rollback
    @Transactional
    @Test
    void testListCustomers() {
        testSaveNewCustomer();

        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(1);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();

        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}