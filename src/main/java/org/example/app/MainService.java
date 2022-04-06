package org.example.app;

import lombok.RequiredArgsConstructor;
import org.example.aop.DataSourceSelector;
import org.example.aop.DataSources;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.repository.dao.Customer;
import org.example.repository.dao.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@DataSourceSelector(DataSources.DATA_SOURCE_1)
public class MainService {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public List<Customer> findAllСustomers() {
        return customerRepository.findAll();
    }

    @DataSourceSelector(DataSources.DATA_SOURCE_2)
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}
