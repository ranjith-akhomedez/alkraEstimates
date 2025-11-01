package com.alkra.estimates.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ProductType productType;

    @Column(nullable = false)
    private BigDecimal ratePerSquareFeet;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean active = true;
}
