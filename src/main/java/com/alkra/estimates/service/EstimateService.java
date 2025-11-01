package com.alkra.estimates.service;

import com.alkra.estimates.dto.EstimateRequest;
import com.alkra.estimates.dto.EstimateResponse;
import com.alkra.estimates.exception.ResourceNotFoundException;
import com.alkra.estimates.model.Estimate;
import com.alkra.estimates.model.ProductConfiguration;
import com.alkra.estimates.repository.EstimateRepository;
import com.alkra.estimates.repository.ProductConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final EstimateRepository estimateRepository;
    private final ProductConfigurationRepository productConfigurationRepository;

    @Transactional
    public EstimateResponse createEstimate(EstimateRequest request) {
        // Get product configuration
        ProductConfiguration config = productConfigurationRepository
                .findByProductTypeAndActiveTrue(request.getProductType())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product configuration not found for: " + request.getProductType()));

        // Calculate area in square feet
        BigDecimal areaInSquareFeet = calculateArea(request.getHeight(), request.getWidth());

        // Calculate total cost
        BigDecimal totalCost = areaInSquareFeet
                .multiply(config.getRatePerSquareFeet())
                .setScale(2, RoundingMode.HALF_UP);

        // Create and save estimate
        Estimate estimate = new Estimate();
        estimate.setProductType(request.getProductType());
        estimate.setHeight(request.getHeight());
        estimate.setWidth(request.getWidth());
        estimate.setNumberOfLeafs(request.getNumberOfLeafs());
        estimate.setGlassThickness(request.getGlassThickness());
        estimate.setAreaInSquareFeet(areaInSquareFeet);
        estimate.setRatePerSquareFeet(config.getRatePerSquareFeet());
        estimate.setTotalCost(totalCost);
        estimate.setCustomerName(request.getCustomerName());
        estimate.setCustomerEmail(request.getCustomerEmail());
        estimate.setCustomerPhone(request.getCustomerPhone());

        estimate = estimateRepository.save(estimate);

        return mapToResponse(estimate);
    }

    public EstimateResponse getEstimate(Long id) {
        Estimate estimate = estimateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estimate not found with id: " + id));
        return mapToResponse(estimate);
    }

    public List<EstimateResponse> getAllEstimates() {
        return estimateRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BigDecimal calculateArea(BigDecimal height, BigDecimal width) {
        // Convert to square feet (assuming input is in feet)
        return height.multiply(width).setScale(2, RoundingMode.HALF_UP);
    }

    private EstimateResponse mapToResponse(Estimate estimate) {
        return EstimateResponse.builder()
                .id(estimate.getId())
                .productType(estimate.getProductType())
                .productTypeName(estimate.getProductType().getDisplayName())
                .height(estimate.getHeight())
                .width(estimate.getWidth())
                .numberOfLeafs(estimate.getNumberOfLeafs())
                .glassThickness(estimate.getGlassThickness())
                .areaInSquareFeet(estimate.getAreaInSquareFeet())
                .ratePerSquareFeet(estimate.getRatePerSquareFeet())
                .totalCost(estimate.getTotalCost())
                .customerName(estimate.getCustomerName())
                .customerEmail(estimate.getCustomerEmail())
                .customerPhone(estimate.getCustomerPhone())
                .createdAt(estimate.getCreatedAt())
                .build();
    }
}
