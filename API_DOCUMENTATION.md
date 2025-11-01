# Alkra Estimates API Documentation

## Base URL

```
http://localhost:8080/api
```

For WAR deployment on Tomcat:
```
http://localhost:8080/alkra-estimates/api
```

## Authentication

Currently, the API does not require authentication. For production deployment, consider implementing:
- JWT tokens
- OAuth2
- API keys

## Response Format

All API responses are in JSON format.

### Success Response
```json
{
  "id": 1,
  "productType": "UPVC_WINDOW",
  "totalCost": 9000.0,
  ...
}
```

### Error Response
```json
{
  "timestamp": "2023-11-01T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/estimates"
}
```

## Endpoints

### 1. Create Estimate

Create a new cost estimate for a product.

**Endpoint:** `POST /api/estimates`

**Request Headers:**
```
Content-Type: application/json
```

**Request Body:**
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

**Field Descriptions:**

| Field | Type | Required | Description | Validation |
|-------|------|----------|-------------|------------|
| productType | String (Enum) | Yes | Product type | Must be one of: UPVC_WINDOW, UPVC_DOOR, MODULAR_KITCHEN, GLASS_PARTITION, MOSQUITO_NET |
| height | Number | Yes | Height in feet | Must be > 0.1 |
| width | Number | Yes | Width in feet | Must be > 0.1 |
| numberOfLeafs | Integer | No | Number of leafs/panels | Must be >= 1 |
| glassThickness | Integer | No | Glass thickness in mm | Must be >= 1 |
| customerName | String | No | Customer name | - |
| customerEmail | String | No | Customer email | Must be valid email format |
| customerPhone | String | No | Customer phone | Must be 10-15 digits |

**Success Response (201 Created):**
```json
{
  "id": 1,
  "productType": "UPVC_WINDOW",
  "productTypeName": "UPVC Window",
  "height": 5.0,
  "width": 4.0,
  "numberOfLeafs": 2,
  "glassThickness": 6,
  "areaInSquareFeet": 20.0,
  "ratePerSquareFeet": 450.0,
  "totalCost": 9000.0,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "customerPhone": "1234567890",
  "createdAt": "2023-11-01T14:30:00"
}
```

**Example cURL:**
```bash
curl -X POST http://localhost:8080/api/estimates \
  -H "Content-Type: application/json" \
  -d '{
    "productType": "UPVC_WINDOW",
    "height": 5.0,
    "width": 4.0,
    "numberOfLeafs": 2,
    "glassThickness": 6,
    "customerName": "John Doe",
    "customerEmail": "john@example.com",
    "customerPhone": "1234567890"
  }'
```

---

### 2. Get Estimate by ID

Retrieve a specific estimate by its ID.

**Endpoint:** `GET /api/estimates/{id}`

**Path Parameters:**
- `id` (required): The estimate ID

**Success Response (200 OK):**
```json
{
  "id": 1,
  "productType": "UPVC_WINDOW",
  "productTypeName": "UPVC Window",
  "height": 5.0,
  "width": 4.0,
  "numberOfLeafs": 2,
  "glassThickness": 6,
  "areaInSquareFeet": 20.0,
  "ratePerSquareFeet": 450.0,
  "totalCost": 9000.0,
  "customerName": "John Doe",
  "customerEmail": "john@example.com",
  "customerPhone": "1234567890",
  "createdAt": "2023-11-01T14:30:00"
}
```

**Error Response (404 Not Found):**
```json
{
  "message": "Estimate not found with id: 999"
}
```

**Example cURL:**
```bash
curl http://localhost:8080/api/estimates/1
```

---

### 3. Get All Estimates

Retrieve all estimates.

**Endpoint:** `GET /api/estimates`

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "productType": "UPVC_WINDOW",
    "productTypeName": "UPVC Window",
    "height": 5.0,
    "width": 4.0,
    "areaInSquareFeet": 20.0,
    "ratePerSquareFeet": 450.0,
    "totalCost": 9000.0,
    "customerName": "John Doe",
    "createdAt": "2023-11-01T14:30:00"
  },
  {
    "id": 2,
    "productType": "MODULAR_KITCHEN",
    "productTypeName": "Modular Kitchen",
    "height": 10.0,
    "width": 8.0,
    "areaInSquareFeet": 80.0,
    "ratePerSquareFeet": 1200.0,
    "totalCost": 96000.0,
    "customerName": "Jane Smith",
    "createdAt": "2023-11-01T14:35:00"
  }
]
```

**Example cURL:**
```bash
curl http://localhost:8080/api/estimates
```

---

### 4. Download Estimate PDF

Download an estimate as a PDF document.

**Endpoint:** `GET /api/estimates/{id}/pdf`

**Path Parameters:**
- `id` (required): The estimate ID

**Success Response (200 OK):**
- Content-Type: application/pdf
- Content-Disposition: attachment; filename="estimate-{id}.pdf"

**Response:** Binary PDF file

**Example cURL:**
```bash
curl http://localhost:8080/api/estimates/1/pdf -o estimate-1.pdf
```

**Example in Browser:**
```
http://localhost:8080/api/estimates/1/pdf
```

---

### 5. Get All Product Configurations

Retrieve all product configurations with rates.

**Endpoint:** `GET /api/configurations`

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "productType": "UPVC_WINDOW",
    "ratePerSquareFeet": 450.0,
    "description": "High quality UPVC windows with double glazing",
    "active": true
  },
  {
    "id": 2,
    "productType": "UPVC_DOOR",
    "ratePerSquareFeet": 550.0,
    "description": "Premium UPVC doors with security features",
    "active": true
  },
  {
    "id": 3,
    "productType": "MODULAR_KITCHEN",
    "ratePerSquareFeet": 1200.0,
    "description": "Modern modular kitchen solutions",
    "active": true
  },
  {
    "id": 4,
    "productType": "GLASS_PARTITION",
    "ratePerSquareFeet": 350.0,
    "description": "Elegant glass partitions for offices and homes",
    "active": true
  },
  {
    "id": 5,
    "productType": "MOSQUITO_NET",
    "ratePerSquareFeet": 85.0,
    "description": "Durable mosquito nets for windows and doors",
    "active": true
  }
]
```

**Example cURL:**
```bash
curl http://localhost:8080/api/configurations
```

---

### 6. Get Product Configuration by Type

Retrieve configuration for a specific product type.

**Endpoint:** `GET /api/configurations/{productType}`

**Path Parameters:**
- `productType` (required): Product type (UPVC_WINDOW, UPVC_DOOR, MODULAR_KITCHEN, GLASS_PARTITION, MOSQUITO_NET)

**Success Response (200 OK):**
```json
{
  "id": 1,
  "productType": "UPVC_WINDOW",
  "ratePerSquareFeet": 450.0,
  "description": "High quality UPVC windows with double glazing",
  "active": true
}
```

**Error Response (404 Not Found):**
```json
{
  "message": "Configuration not found"
}
```

**Example cURL:**
```bash
curl http://localhost:8080/api/configurations/UPVC_WINDOW
```

---

## Product Types

| Product Type | Display Name | Default Rate (₹/sq ft) |
|--------------|--------------|------------------------|
| UPVC_WINDOW | UPVC Window | 450 |
| UPVC_DOOR | UPVC Door | 550 |
| MODULAR_KITCHEN | Modular Kitchen | 1200 |
| GLASS_PARTITION | Glass Partition | 350 |
| MOSQUITO_NET | Mosquito Net | 85 |

---

## Error Codes

| Status Code | Description |
|-------------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid input data |
| 404 | Not Found - Resource not found |
| 500 | Internal Server Error - Server error |

---

## CORS

The API has CORS enabled for all origins (`@CrossOrigin(origins = "*")`).

For production, configure specific origins in the controllers or application properties.

---

## Rate Limiting

Currently, there is no rate limiting. For production deployment, consider implementing:
- Request throttling
- API quotas per user/IP
- Rate limiting middleware

---

## Testing

### Postman Collection

Import the following examples into Postman for easy testing:

1. **Create Estimate - UPVC Window**
   - Method: POST
   - URL: `{{baseUrl}}/api/estimates`
   - Body: See example in "Create Estimate" section

2. **Get All Estimates**
   - Method: GET
   - URL: `{{baseUrl}}/api/estimates`

3. **Get Estimate by ID**
   - Method: GET
   - URL: `{{baseUrl}}/api/estimates/1`

4. **Download PDF**
   - Method: GET
   - URL: `{{baseUrl}}/api/estimates/1/pdf`
   - Save response as file

5. **Get All Configurations**
   - Method: GET
   - URL: `{{baseUrl}}/api/configurations`

### Variables
```
baseUrl = http://localhost:8080
```

---

## Integration Examples

### JavaScript (Fetch API)

```javascript
// Create estimate
async function createEstimate() {
  const response = await fetch('http://localhost:8080/api/estimates', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      productType: 'UPVC_WINDOW',
      height: 5.0,
      width: 4.0,
      numberOfLeafs: 2,
      glassThickness: 6,
      customerName: 'John Doe'
    })
  });
  
  const estimate = await response.json();
  console.log(estimate);
}

// Get all estimates
async function getAllEstimates() {
  const response = await fetch('http://localhost:8080/api/estimates');
  const estimates = await response.json();
  console.log(estimates);
}
```

### Python (requests)

```python
import requests

# Create estimate
response = requests.post(
    'http://localhost:8080/api/estimates',
    json={
        'productType': 'UPVC_WINDOW',
        'height': 5.0,
        'width': 4.0,
        'numberOfLeafs': 2,
        'glassThickness': 6,
        'customerName': 'John Doe'
    }
)
estimate = response.json()
print(estimate)

# Download PDF
response = requests.get('http://localhost:8080/api/estimates/1/pdf')
with open('estimate.pdf', 'wb') as f:
    f.write(response.content)
```

---

## Support

For issues or questions about the API:
- Check the main README.md
- Review the DEPLOYMENT.md guide
- Open an issue on GitHub

---

## Changelog

### Version 1.0.0 (2024-11-01)
- Initial release
- Basic CRUD operations for estimates
- PDF generation
- Product configuration management
