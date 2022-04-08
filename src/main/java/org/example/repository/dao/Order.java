package org.example.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id
    private Long id;
    @Basic
    private String item;
}
