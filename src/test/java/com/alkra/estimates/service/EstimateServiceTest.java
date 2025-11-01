package com.alkra.estimates.service;

import com.alkra.estimates.dto.EstimateRequest;
import com.alkra.estimates.dto.EstimateResponse;
import com.alkra.estimates.model.ProductConfiguration;
import com.alkra.estimates.model.ProductType;
import com.alkra.estimates.repository.EstimateRepository;
import com.alkra.estimates.repository.ProductConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EstimateServiceTest {

    @Autowired
    private EstimateService estimateService;

    @Autowired
    private ProductConfigurationRepository productConfigurationRepository;

    @Autowired
    private EstimateRepository estimateRepository;

    @BeforeEach
    void setUp() {
        estimateRepository.deleteAll();
        productConfigurationRepository.deleteAll();

        // Create test configuration
        ProductConfiguration config = new ProductConfiguration();
        config.setProductType(ProductType.UPVC_WINDOW);
        config.setRatePerSquareFeet(new BigDecimal("450.00"));
        config.setDescription("Test configuration");
        config.setActive(true);
        productConfigurationRepository.save(config);
    }

    @Test
    void testCreateEstimate() {
        EstimateRequest request = new EstimateRequest();
        request.setProductType(ProductType.UPVC_WINDOW);
        request.setHeight(new BigDecimal("5.0"));
        request.setWidth(new BigDecimal("4.0"));
        request.setNumberOfLeafs(2);
        request.setGlassThickness(6);
        request.setCustomerName("John Doe");

        EstimateResponse response = estimateService.createEstimate(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(ProductType.UPVC_WINDOW, response.getProductType());
        assertEquals(new BigDecimal("5.0"), response.getHeight());
        assertEquals(new BigDecimal("4.0"), response.getWidth());
        assertEquals(new BigDecimal("20.00"), response.getAreaInSquareFeet());
        assertEquals(new BigDecimal("450.00"), response.getRatePerSquareFeet());
        assertEquals(new BigDecimal("9000.00"), response.getTotalCost());
        assertEquals("John Doe", response.getCustomerName());
    }

    @Test
    void testGetEstimate() {
        EstimateRequest request = new EstimateRequest();
        request.setProductType(ProductType.UPVC_WINDOW);
        request.setHeight(new BigDecimal("6.0"));
        request.setWidth(new BigDecimal("3.0"));

        EstimateResponse created = estimateService.createEstimate(request);
        EstimateResponse retrieved = estimateService.getEstimate(created.getId());

        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getTotalCost(), retrieved.getTotalCost());
    }

    @Test
    void testGetAllEstimates() {
        EstimateRequest request1 = new EstimateRequest();
        request1.setProductType(ProductType.UPVC_WINDOW);
        request1.setHeight(new BigDecimal("5.0"));
        request1.setWidth(new BigDecimal("4.0"));

        EstimateRequest request2 = new EstimateRequest();
        request2.setProductType(ProductType.UPVC_WINDOW);
        request2.setHeight(new BigDecimal("3.0"));
        request2.setWidth(new BigDecimal("2.0"));

        estimateService.createEstimate(request1);
        estimateService.createEstimate(request2);

        assertEquals(2, estimateService.getAllEstimates().size());
    }
}
