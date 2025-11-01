package com.alkra.estimates.config;

import com.alkra.estimates.model.ProductConfiguration;
import com.alkra.estimates.model.ProductType;
import com.alkra.estimates.repository.ProductConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductConfigurationRepository productConfigurationRepository;

    @Override
    public void run(String... args) {
        if (productConfigurationRepository.count() == 0) {
            log.info("Initializing product configurations...");

            // UPVC Window
            ProductConfiguration upvcWindow = new ProductConfiguration();
            upvcWindow.setProductType(ProductType.UPVC_WINDOW);
            upvcWindow.setRatePerSquareFeet(new BigDecimal("450.00"));
            upvcWindow.setDescription("High quality UPVC windows with double glazing");
            upvcWindow.setActive(true);
            productConfigurationRepository.save(upvcWindow);

            // UPVC Door
            ProductConfiguration upvcDoor = new ProductConfiguration();
            upvcDoor.setProductType(ProductType.UPVC_DOOR);
            upvcDoor.setRatePerSquareFeet(new BigDecimal("550.00"));
            upvcDoor.setDescription("Premium UPVC doors with security features");
            upvcDoor.setActive(true);
            productConfigurationRepository.save(upvcDoor);

            // Modular Kitchen
            ProductConfiguration modularKitchen = new ProductConfiguration();
            modularKitchen.setProductType(ProductType.MODULAR_KITCHEN);
            modularKitchen.setRatePerSquareFeet(new BigDecimal("1200.00"));
            modularKitchen.setDescription("Modern modular kitchen solutions");
            modularKitchen.setActive(true);
            productConfigurationRepository.save(modularKitchen);

            // Glass Partition
            ProductConfiguration glassPartition = new ProductConfiguration();
            glassPartition.setProductType(ProductType.GLASS_PARTITION);
            glassPartition.setRatePerSquareFeet(new BigDecimal("350.00"));
            glassPartition.setDescription("Elegant glass partitions for offices and homes");
            glassPartition.setActive(true);
            productConfigurationRepository.save(glassPartition);

            // Mosquito Net
            ProductConfiguration mosquitoNet = new ProductConfiguration();
            mosquitoNet.setProductType(ProductType.MOSQUITO_NET);
            mosquitoNet.setRatePerSquareFeet(new BigDecimal("85.00"));
            mosquitoNet.setDescription("Durable mosquito nets for windows and doors");
            mosquitoNet.setActive(true);
            productConfigurationRepository.save(mosquitoNet);

            log.info("Product configurations initialized successfully");
        } else {
            log.info("Product configurations already exist, skipping initialization");
        }
    }
}
