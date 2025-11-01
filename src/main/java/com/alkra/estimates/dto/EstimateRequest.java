package com.alkra.estimates.dto;

import com.alkra.estimates.model.ProductType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRequest {

    @NotNull(message = "Product type is required")
    private ProductType productType;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "0.1", message = "Height must be greater than 0")
    private BigDecimal height;

    @NotNull(message = "Width is required")
    @DecimalMin(value = "0.1", message = "Width must be greater than 0")
    private BigDecimal width;

    @Min(value = 1, message = "Number of leafs must be at least 1")
    private Integer numberOfLeafs;

    @Min(value = 1, message = "Glass thickness must be at least 1mm")
    private Integer glassThickness;

    private String customerName;

    @Email(message = "Invalid email format")
    private String customerEmail;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    private String customerPhone;
}
