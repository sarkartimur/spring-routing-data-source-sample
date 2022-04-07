package org.example.repository;

import org.example.aop.DataSourceSelector;
import org.example.aop.DataSources;
import org.example.repository.dao.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@DataSourceSelector(DataSources.CUSTOM_DATASOURCE)
public interface OrderRepository extends JpaRepository<Order, Long> {
}
