# Alkra Estimates - Deployment Guide

## Quick Start Guide

### 1. Build the WAR File

```bash
mvn clean package
```

This will create `target/alkra-estimates.war` (~51MB)

### 2. Deploy to Tomcat

#### Option A: Manual Deployment

1. Stop Tomcat if running
2. Copy the WAR file:
   ```bash
   cp target/alkra-estimates.war $TOMCAT_HOME/webapps/
   ```
3. Start Tomcat:
   ```bash
   $TOMCAT_HOME/bin/startup.sh
   ```
4. Access the application at: `http://localhost:8080/alkra-estimates/`

#### Option B: Tomcat Manager

1. Access Tomcat Manager: `http://localhost:8080/manager`
2. Use the "WAR file to deploy" section
3. Browse and select `alkra-estimates.war`
4. Click "Deploy"

### 3. Database Configuration

#### Development (H2 Database)
The application comes pre-configured with H2 in-memory database. No additional setup required.

#### Production (MySQL)

1. Create database:
   ```sql
   CREATE DATABASE alkra_estimates;
   CREATE USER 'alkra_user'@'localhost' IDENTIFIED BY 'your_secure_password';
   GRANT ALL PRIVILEGES ON alkra_estimates.* TO 'alkra_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. Update `application.properties`:
   ```properties
   # Comment out H2 configuration
   #spring.datasource.url=jdbc:h2:mem:alkradb
   
   # Uncomment and configure MySQL
   spring.datasource.url=jdbc:mysql://localhost:3306/alkra_estimates?useSSL=false&serverTimezone=UTC
   spring.datasource.username=alkra_user
   spring.datasource.password=your_secure_password
   spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```

3. Rebuild and redeploy:
   ```bash
   mvn clean package
   ```

## Configuration

### Product Rates

Default rates (per square feet):
- UPVC Window: ₹450
- UPVC Door: ₹550
- Modular Kitchen: ₹1200
- Glass Partition: ₹350
- Mosquito Net: ₹85

To modify rates, update `DataInitializer.java` or use the database directly.

### Application Properties

Key configuration options in `application.properties`:

```properties
# Server Port
server.port=8080

# Database
spring.datasource.url=jdbc:h2:mem:alkradb
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging
logging.level.com.alkra.estimates=INFO
```

## API Endpoints

### Estimates API

**Create Estimate**
```bash
POST /api/estimates
Content-Type: application/json

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

**Get Estimate**
```bash
GET /api/estimates/{id}
```

**Get All Estimates**
```bash
GET /api/estimates
```

**Download PDF**
```bash
GET /api/estimates/{id}/pdf
```

### Configuration API

**Get All Configurations**
```bash
GET /api/configurations
```

**Get Configuration by Product Type**
```bash
GET /api/configurations/{productType}
```

## Testing

### Run Tests
```bash
mvn test
```

### Manual Testing

1. Start application:
   ```bash
   mvn spring-boot:run
   ```

2. Access web UI: http://localhost:8080

3. Test API endpoints:
   ```bash
   # Create estimate
   curl -X POST http://localhost:8080/api/estimates \
     -H "Content-Type: application/json" \
     -d '{"productType":"UPVC_WINDOW","height":5.0,"width":4.0}'
   
   # Get all estimates
   curl http://localhost:8080/api/estimates
   
   # Download PDF
   curl http://localhost:8080/api/estimates/1/pdf -o estimate.pdf
   ```

## Troubleshooting

### Issue: Port 8080 already in use
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Database connection failed
**Solution:** Verify database credentials and ensure MySQL server is running:
```bash
systemctl status mysql
```

### Issue: WAR deployment fails
**Solution:** Check Tomcat logs:
```bash
tail -f $TOMCAT_HOME/logs/catalina.out
```

### Issue: PDF generation fails
**Solution:** Ensure iText library is included in the WAR. Check:
```bash
jar -tf target/alkra-estimates.war | grep itextpdf
```

## Performance Tuning

### JVM Options for Tomcat

Add to `$TOMCAT_HOME/bin/setenv.sh`:
```bash
export CATALINA_OPTS="$CATALINA_OPTS -Xms512m -Xmx2048m"
export CATALINA_OPTS="$CATALINA_OPTS -XX:MaxPermSize=512m"
```

### Database Connection Pool

Configure in `application.properties`:
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
```

## Security Considerations

1. **Change default passwords** in production
2. **Enable HTTPS** for production deployment
3. **Configure CORS** appropriately
4. **Add authentication** for sensitive endpoints
5. **Regular database backups**
6. **Keep dependencies updated**

## Monitoring

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

### Application Logs
```bash
tail -f $TOMCAT_HOME/logs/catalina.out
```

### Database Console (Development only)
http://localhost:8080/h2-console

## Backup and Recovery

### Backup Database (MySQL)
```bash
mysqldump -u alkra_user -p alkra_estimates > backup_$(date +%Y%m%d).sql
```

### Restore Database
```bash
mysql -u alkra_user -p alkra_estimates < backup_20231101.sql
```

## Support

For issues or questions:
- Check the logs
- Review the README.md
- Open an issue on GitHub

## License

Copyright © 2024 Alkra. All rights reserved.
