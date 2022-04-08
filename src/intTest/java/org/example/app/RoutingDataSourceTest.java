package org.example.app;

import org.example.aop.DataSources;
import org.example.repository.CustomerRepository;
import org.example.repository.OrderRepository;
import org.example.repository.dao.Customer;
import org.example.repository.dao.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource("classpath:test-application.properties")
public class RoutingDataSourceTest {
    @Autowired
    RoutingDataSource dataSource;

    @SpyBean
    RoutingDataSource.RoutingDataSourceContext context;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;


    @BeforeEach
    public void setup() throws SQLException {
        initDB();

        dataSource.setContext(context);
    }

    private void initDB() throws SQLException {
        executeDDL("create table CUSTOMER (id bigserial primary key, name varchar)");

        dataSource.getContext().set(DataSources.CUSTOM_DATASOURCE);
        executeDDL("create table ORDERS (id bigserial primary key, item varchar)");
        dataSource.getContext().set(DataSources.DEFAULT);
    }

    private void executeDDL(String query) throws SQLException {
        Statement s = dataSource.getConnection().createStatement();
        s.execute(query);
    }


    @Test
    public void dbOperations_shouldUseAppropriateDataSource() {
        Customer customer = new Customer(1L, "John");
        customerRepository.save(customer);
        assertEquals(customer, customerRepository.findById(1L).get());

        Order order = new Order(1L, "Johns order");
        orderRepository.save(order);
        assertEquals(order, orderRepository.findById(1L).get());

        verify(context, times(2)).set(eq(DataSources.CUSTOM_DATASOURCE));
    }
}
