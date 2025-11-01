package com.alkra.estimates.dto;

import com.alkra.estimates.model.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstimateResponse {

    private Long id;
    private ProductType productType;
    private String productTypeName;
    private BigDecimal height;
    private BigDecimal width;
    private Integer numberOfLeafs;
    private Integer glassThickness;
    private BigDecimal areaInSquareFeet;
    private BigDecimal ratePerSquareFeet;
    private BigDecimal totalCost;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private LocalDateTime createdAt;
}
