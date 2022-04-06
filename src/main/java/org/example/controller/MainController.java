package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.app.MainService;
import org.example.repository.dao.Customer;
import org.example.repository.dao.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService service;

    @GetMapping("/customers")
    public List<Customer> customers() {
        return service.findAllСustomers();
    }

    @GetMapping("/orders")
    public List<Order> orders() {
        return service.findAllOrders();
    }
}
