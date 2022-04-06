package org.example.repository.dao;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private Long id;
    @Basic
    private String item;
}
