package com.alkra.estimates.repository;

import com.alkra.estimates.model.Estimate;
import com.alkra.estimates.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    
    List<Estimate> findByProductType(ProductType productType);
    
    List<Estimate> findByCustomerEmail(String customerEmail);
}
