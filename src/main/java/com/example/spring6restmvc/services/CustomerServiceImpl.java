package com.example.spring6restmvc.services;

import com.example.spring6restmvc.models.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Customer 1")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Customer 2")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Customer 3")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap = new HashMap<>();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
        }

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        customerMap.remove(customerId);

        return true;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomerName(customer.getCustomerName());

        return Optional.of(existing);
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .updateDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .customerName(customer.getCustomerName())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.of(customerMap.get(uuid));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }
}