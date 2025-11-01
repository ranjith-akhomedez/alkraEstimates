# Alkra Estimates - Online Estimation Software

A comprehensive Spring Boot application for generating cost estimates for various products including UPVC windows, doors, modular kitchens, glass partitions, and mosquito nets.

## Features

- 🏗️ **Multiple Product Types**: Support for UPVC Windows, Doors, Modular Kitchens, Glass Partitions, and Mosquito Nets
- 📊 **Automatic Cost Calculation**: Calculates costs based on measurements and pre-configured rates
- 📄 **PDF Generation**: Generates professional PDF estimates with measurements and rough drawings
- 💾 **Data Persistence**: Stores all estimates in database for future reference
- 🎨 **Modern Web UI**: Clean and responsive web interface
- 🔌 **REST API**: Complete REST API for integration with other systems
- 📦 **WAR Deployment**: Can be exported as WAR file and deployed to Tomcat server

## Technology Stack

- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (development) / MySQL (production)
- Thymeleaf
- iText PDF
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Apache Tomcat 10+ (for WAR deployment)
- MySQL 8+ (for production, optional)

## Running Locally

1. Clone the repository:
```bash
git clone https://github.com/ranjith-akhomedez/alkraEstimates.git
cd alkraEstimates
```

2. Build the application:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
- Web UI: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- API Base URL: http://localhost:8080/api

## Building WAR File for Tomcat Deployment

1. Build the WAR file:
```bash
mvn clean package
```

2. The WAR file will be generated at: `target/alkra-estimates.war`

3. Deploy to Tomcat:
   - Copy `alkra-estimates.war` to Tomcat's `webapps` directory
   - Start/restart Tomcat
   - Access at: `http://localhost:8080/alkra-estimates/`

## API Endpoints

### Estimates

- **POST** `/api/estimates` - Create new estimate
  ```json
  {
    "productType": "UPVC_WINDOW",
    "height": 5.0,
    "width": 4.0,
    "numberOfLeafs": 2,
    "glassThickness": 6,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "customerPhone": "1234567890"
  }
  ```

- **GET** `/api/estimates/{id}` - Get estimate by ID
- **GET** `/api/estimates` - Get all estimates
- **GET** `/api/estimates/{id}/pdf` - Download estimate as PDF

### Product Configurations

- **GET** `/api/configurations` - Get all product configurations
- **GET** `/api/configurations/{productType}` - Get configuration for specific product

## Product Types

1. **UPVC_WINDOW** - UPVC Windows (₹450/sq ft)
2. **UPVC_DOOR** - UPVC Doors (₹550/sq ft)
3. **MODULAR_KITCHEN** - Modular Kitchens (₹1200/sq ft)
4. **GLASS_PARTITION** - Glass Partitions (₹350/sq ft)
5. **MOSQUITO_NET** - Mosquito Nets (₹85/sq ft)

## Configuration

### Database Configuration

For production deployment with MySQL, update `src/main/resources/application.properties`:

```properties
# Comment out H2 configuration and uncomment MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/alkra_estimates?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
```

### Updating Product Rates

Product rates are initialized in `DataInitializer.java`. To update rates, modify the values or use the API to update configurations in the database.

## Testing

Run tests with:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/alkra/estimates/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── model/           # JPA entities
│   │   ├── repository/      # Spring Data repositories
│   │   └── service/         # Business logic
│   └── resources/
│       ├── templates/       # Thymeleaf templates
│       └── application.properties
└── test/                    # Test classes
```

## License

Copyright © 2024 Alkra. All rights reserved.

## Support

For issues or questions, please open an issue on GitHub.
