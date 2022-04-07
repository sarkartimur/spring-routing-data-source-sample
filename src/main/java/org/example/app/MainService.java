package org.example.app;

import lombok.RequiredArgsConstructor;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.repository.dao.Customer;
import org.example.repository.dao.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public List<Customer> findAllСustomers() {
        return customerRepository.findAll();
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
