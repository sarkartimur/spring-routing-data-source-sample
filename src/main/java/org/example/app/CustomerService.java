package org.example.app;

import lombok.RequiredArgsConstructor;
import org.example.repository.CustomerRepository;
import org.example.repository.dao.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public List<Customer> findAll() {
        return repository.findAll();
    }
}
