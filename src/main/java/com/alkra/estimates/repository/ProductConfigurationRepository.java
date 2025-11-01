package com.alkra.estimates.repository;

import com.alkra.estimates.model.ProductConfiguration;
import com.alkra.estimates.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductConfigurationRepository extends JpaRepository<ProductConfiguration, Long> {
    
    Optional<ProductConfiguration> findByProductTypeAndActiveTrue(ProductType productType);
}
