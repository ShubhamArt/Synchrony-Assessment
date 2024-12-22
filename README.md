# SynchronyAssessment

A Spring Boot application that demonstrates CRUD operations using MySQL, caching with Redis, and performance optimization through multithreading.

## Features
- CRUD operations on MySQL.
- Caching with Redis.
- Multithreaded processing using ExecutorService.

## Setup
- Prerequisites: Java, MySQL, Redis.
 - Secure connection to MySQL with SSL/TLS.
- Caching with Redis for optimized performance.
- Multithreading with ExecutorService for parallel processing.
- Graceful error handling and resilience mechanisms.
- Performance metrics for thread execution and cache hit/miss rates.
- Run `mvn spring-boot:run` to start the application.

  - ## Setup Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd SynchronyAssessment
   ```

2. Configure MySQL:
   - Create a database:
     ```sql
     CREATE DATABASE Synchronyproject;
     ```
   - Add a table:
     ```sql
     CREATE TABLE user (
         id BIGINT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(255) NOT NULL,
         email VARCHAR(255) UNIQUE NOT NULL
     );
     ```

3. Configure application properties:
   Update `src/main/resources/application.properties` with  MySQL and Redis credentials.

4. Run the application:
  
   mvn spring-boot:run
  

5. Access the APIs:
   - Get all users: `GET http://localhost:8080/users`
   - Add a user: `POST http://localhost:8080/users` with JSON body:
     ```json
     {
       "name": "Alice",
       "email": "alice@example.com"
     }
Parallel threading reduces execution time significantly by processing tasks concurrently

## API Endpoints
- GET/users: Retrieve all users.(http://localhost:8080/users)
- POST/users : Add a new user.(http://localhost:8080/users)

## Testing
- Run `mvn test` to execute unit and integration tests.

## Code Architecture Details

### Layers
1. **Controller Layer**: Handles HTTP requests and responses.
2. **Service Layer**: Implements business logic and multithreading.
3. **Repository Layer**: Interacts with MySQL using Spring Data JPA.
4. **Caching Layer**: Uses Redis to cache data for performance optimization.
5. **Utility Layer**: Includes utilities like performance logging.

### Key Features
- **Multithreading**: ExecutorService is used to handle concurrent tasks.
- **Caching**: Redis is integrated for caching frequently used data.
- **Performance Logging**: Measures API execution time.
- **Error Handling**: Gracefully handles Redis and database errors.

## Test Cases

1) Unit Tests = UserServiceTest : Test CRUD operations & Test Redis cache updates
2) Integration Tests = UserControllerTest : Test REST APIs
3) Performance Tests = PerformanceTest : Compare sequential vs parallel execution

## Performance Reports

| Execution Type    | Time (ms) |
|-------------------|-----------|
| Sequential Calls  | 303       |
| Parallel Calls    | 320       |
