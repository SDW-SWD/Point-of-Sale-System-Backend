package org.CypsoLabs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer id")
    private  Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Product mail;

    @Column(nullable = false)
    private  String address;

    @Column(name = "contact number",nullable = false)
    private String contact;

    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<Orders> orders;

    @OneToMany(mappedBy = "customer",fetch = FetchType.EAGER)
    private List<Cart> cart;
}