package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.app.CustomerService;
import org.example.app.RoutingDataSource;
import org.example.repository.dao.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final CustomerService service;
    private final RoutingDataSource dataSource;

    @GetMapping("/")
    public List<Customer> findAll() {
        return dataSource.getConfig().getParams().keySet().stream().map(k -> {
                    dataSource.getContext().set(k);
                    return service.findAll();
                }).flatMap(List::stream).collect(Collectors.toList());
    }
}
