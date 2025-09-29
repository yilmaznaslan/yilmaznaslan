# Mobile.de Marketplace Backend

A comprehensive Spring Boot 3.5+ backend application that mimics the mobile.de vehicle marketplace business model. This project demonstrates modern Java development practices and enterprise-level architecture suitable for technical interviews.

## 🚀 Features

### Core Functionality

- **Vehicle Management**: Complete CRUD operations for vehicle listings
- **Advanced Search**: Multi-criteria search with pagination and sorting
- **Dealer Management**: User management for private sellers and dealers
- **Location-based Search**: Geographic filtering capabilities
- **Full-text Search**: MongoDB text search with scoring
- **RESTful APIs**: Comprehensive REST endpoints with OpenAPI documentation

### Technical Stack

- **Java 21** with Spring Boot 3.5+
- **Spring Data MongoDB** for NoSQL data persistence
- **Spring Data JPA** with H2 for relational data
- **Spring Security** for authentication and authorization
- **Spring Data Elasticsearch** for advanced search capabilities
- **Redis** for caching
- **Apache Kafka** for event-driven architecture
- **MapStruct** for object mapping
- **OpenAPI 3** for API documentation
- **Testcontainers** for integration testing

## 🏗️ Architecture

### Database Design

- **MongoDB**: Primary database for vehicle documents with text indexing
- **H2**: Relational database for user management and metadata
- **Elasticsearch**: Search engine for complex queries and analytics
- **Redis**: Caching layer for performance optimization

### API Endpoints

#### Vehicle Management

- `POST /api/vehicles/search` - Advanced vehicle search with filters
- `GET /api/vehicles` - Get all vehicles with pagination
- `GET /api/vehicles/{id}` - Get vehicle by ID
- `POST /api/vehicles` - Create new vehicle listing
- `PUT /api/vehicles/{id}` - Update vehicle listing
- `DELETE /api/vehicles/{id}` - Delete vehicle listing

#### Search & Filtering

- `GET /api/vehicles/brand/{brand}` - Filter by brand
- `GET /api/vehicles/price-range` - Filter by price range
- `GET /api/vehicles/year-range` - Filter by year range
- `GET /api/vehicles/mileage-range` - Filter by mileage range
- `GET /api/vehicles/location` - Filter by location
- `GET /api/vehicles/search-text` - Full-text search

#### Statistics

- `GET /api/vehicles/stats` - Vehicle marketplace statistics

## 🚗 Vehicle Model

### Core Attributes

- **Basic Info**: Brand, Model, Year, Price, Mileage
- **Technical Specs**: Engine Power, Displacement, Fuel Type, Transmission
- **Physical Attributes**: Body Type, Doors, Seats, Previous Owners
- **Condition**: Vehicle Condition, Status, Registration Dates
- **Location**: City, State, Country, Postal Code, Address
- **Media**: Image URLs, Features List, Description

### Enums

- **FuelType**: GASOLINE, DIESEL, ELECTRIC, HYBRID, LPG, CNG
- **TransmissionType**: MANUAL, AUTOMATIC, SEMI_AUTOMATIC, CVT
- **BodyType**: SEDAN, HATCHBACK, SUV, COUPE, CONVERTIBLE, WAGON, PICKUP, VAN, MINIVAN
- **VehicleCondition**: NEW, USED, DAMAGED, FOR_PARTS
- **VehicleStatus**: ACTIVE, SOLD, RESERVED, INACTIVE, PENDING_APPROVAL

## 🔍 Search Capabilities

### Advanced Search Request

```json
{
  "query": "BMW 3 Series",
  "brand": "BMW",
  "minYear": 2020,
  "maxYear": 2024,
  "minPrice": 25000,
  "maxPrice": 50000,
  "minMileage": 0,
  "maxMileage": 50000,
  "fuelTypes": ["GASOLINE", "DIESEL"],
  "transmissions": ["AUTOMATIC"],
  "bodyTypes": ["SEDAN", "COUPE"],
  "city": "Berlin",
  "state": "Berlin",
  "country": "Germany",
  "page": 0,
  "size": 20,
  "sortField": "PRICE",
  "sortDirection": "ASC"
}
```

### Search Response

```json
{
  "vehicles": [...],
  "totalElements": 150,
  "totalPages": 8,
  "currentPage": 0,
  "size": 20,
  "first": true,
  "last": false,
  "numberOfElements": 20
}
```

## 🛠️ Setup & Installation

### Prerequisites

- Java 21+
- Maven 3.8+
- MongoDB 6.0+
- Elasticsearch 8.0+
- Redis 6.0+
- Apache Kafka 3.0+

### Running the Application

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd spring-demo
   ```

2. **Start required services**

   ```bash
   # MongoDB
   docker run -d -p 27017:27017 --name mongodb mongo:6.0

   # Elasticsearch
   docker run -d -p 9200:9200 --name elasticsearch elasticsearch:8.0.0

   # Redis
   docker run -d -p 6379:6379 --name redis redis:6.0

   # Kafka (requires Zookeeper)
   docker run -d -p 2181:2181 --name zookeeper confluentinc/cp-zookeeper:latest
   docker run -d -p 9092:9092 --name kafka confluentinc/cp-kafka:latest
   ```

3. **Build and run the application**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console`

## 📊 API Documentation

### OpenAPI/Swagger

The application includes comprehensive API documentation available at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

### Example API Calls

#### Create a Vehicle

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "BMW",
    "model": "3 Series",
    "year": 2022,
    "price": 35000,
    "mileage": 15000,
    "fuelType": "GASOLINE",
    "transmission": "AUTOMATIC",
    "bodyType": "SEDAN",
    "enginePower": 184,
    "engineDisplacement": 2000,
    "numberOfDoors": 4,
    "numberOfSeats": 5,
    "numberOfPreviousOwners": 1,
    "firstRegistrationDate": "2022-01-15",
    "lastServiceDate": "2023-12-01",
    "nextInspectionDate": "2024-01-15",
    "condition": "USED",
    "imageUrls": ["https://example.com/image1.jpg"],
    "features": ["Navigation", "Bluetooth", "Cruise Control"],
    "description": "Well-maintained BMW 3 Series with low mileage",
    "location": {
      "city": "Berlin",
      "state": "Berlin",
      "country": "Germany",
      "postalCode": "10115",
      "address": "Unter den Linden 1"
    },
    "dealerId": 1
  }'
```

#### Search Vehicles

```bash
curl -X POST http://localhost:8080/api/vehicles/search \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "BMW",
    "minYear": 2020,
    "maxYear": 2024,
    "minPrice": 25000,
    "maxPrice": 50000,
    "page": 0,
    "size": 10
  }'
```

## 🧪 Testing

### Unit Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Test Coverage

The project includes comprehensive test coverage for:

- Service layer business logic
- Repository layer data access
- Controller layer API endpoints
- Integration tests with Testcontainers

## 🚀 Performance Considerations

### Caching Strategy

- Redis caching for frequently accessed data
- Query result caching for search operations
- Dealer information caching

### Database Optimization

- MongoDB text indexes for search fields
- Compound indexes for common query patterns
- Pagination for large result sets

### Search Performance

- Elasticsearch for complex search queries
- MongoDB aggregation pipelines for analytics
- Cached search results

## 🔒 Security Features

- JWT-based authentication
- Role-based access control (USER, DEALER, ADMIN)
- Input validation and sanitization
- CORS configuration
- Rate limiting (configurable)

## 📈 Monitoring & Observability

- Health check endpoints
- Metrics collection
- Structured logging
- Application performance monitoring

## 🏢 Business Logic

### Vehicle Lifecycle

1. **Creation**: Vehicle created with PENDING_APPROVAL status
2. **Approval**: Admin approval changes status to ACTIVE
3. **Reservation**: Customer can reserve vehicle (RESERVED status)
4. **Sale**: Vehicle marked as SOLD
5. **Inactive**: Dealer can deactivate listing

### Dealer Management

- Private sellers and verified dealers
- Dealer verification process
- License number validation
- Company information management

## 🎯 Interview Preparation

This project demonstrates:

- **Modern Java Features**: Java 21, Records, Pattern Matching
- **Spring Ecosystem**: Boot, Data, Security, Kafka
- **Microservices Patterns**: Event-driven architecture, CQRS
- **Database Design**: Multi-database strategy, indexing
- **API Design**: RESTful principles, OpenAPI documentation
- **Testing**: Unit, integration, and contract testing
- **Performance**: Caching, pagination, search optimization
- **Security**: Authentication, authorization, validation

## 📝 License

This project is created for educational and interview preparation purposes.

## 🤝 Contributing

This is a demonstration project. For interview purposes, focus on:

- Code quality and best practices
- Architecture decisions and trade-offs
- Performance optimization strategies
- Security considerations
- Testing approaches
