package com.alkra.estimates.controller;

import com.alkra.estimates.model.ProductConfiguration;
import com.alkra.estimates.model.ProductType;
import com.alkra.estimates.repository.ProductConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductConfigurationController {

    private final ProductConfigurationRepository productConfigurationRepository;

    @GetMapping
    public ResponseEntity<List<ProductConfiguration>> getAllConfigurations() {
        List<ProductConfiguration> configurations = productConfigurationRepository.findAll();
        return ResponseEntity.ok(configurations);
    }

    @GetMapping("/{productType}")
    public ResponseEntity<ProductConfiguration> getConfiguration(@PathVariable ProductType productType) {
        return productConfigurationRepository.findByProductTypeAndActiveTrue(productType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
