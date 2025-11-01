package com.alkra.estimates.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "estimates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    @Column(nullable = false)
    private BigDecimal height;

    @Column(nullable = false)
    private BigDecimal width;

    @Column
    private Integer numberOfLeafs;

    @Column
    private Integer glassThickness;

    @Column(nullable = false)
    private BigDecimal areaInSquareFeet;

    @Column(nullable = false)
    private BigDecimal ratePerSquareFeet;

    @Column(nullable = false)
    private BigDecimal totalCost;

    @Column
    private String customerName;

    @Column
    private String customerEmail;

    @Column
    private String customerPhone;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
