# E-Commerce Microservices Application

A full-stack e-commerce application built with Spring Boot microservices architecture.

## Architecture

This project follows a microservices architecture pattern with the following components:

### Core Services

- **Config Server** (Port: 8888) - Centralized configuration management
- **Eureka Server** (Port: 8761) - Service discovery and registration
- **API Gateway** (Port: 8080) - Single entry point for all client requests

### Business Services

- **Product Service** (Port: 8081) - Product catalog management
- **User Service** (Port: 8082) - User management and authentication
- **Order Service** (Port: 8083) - Order processing and management

## Technology Stack

- **Java 21**
- **Spring Boot 4.0.1**
- **Spring Cloud 2025.1.0**
- **PostgreSQL** - Database
- **RabbitMQ** - Message broker
- **Zipkin** - Distributed tracing
- **Netflix Eureka** - Service discovery
- **Spring Cloud Gateway** - API Gateway
- **Spring Cloud Config** - Configuration management

## Prerequisites

- Java 21
- PostgreSQL
- RabbitMQ
- Maven

## Database Setup

Create the following PostgreSQL databases:

```sql
CREATE DATABASE product;
CREATE DATABASE user;
CREATE DATABASE order;
```

## Environment Variables

Set the following environment variables before running the services:

```bash
DB_USER=your_db_username
DB_PASSWORD=your_db_password
```

## Running the Application

Start the services in the following order:

1. **Config Server**

   ```bash
   cd configserver
   mvn spring-boot:run
   ```

2. **Eureka Server**

   ```bash
   cd eureka
   mvn spring-boot:run
   ```

3. **API Gateway**

   ```bash
   cd gateway
   mvn spring-boot:run
   ```

4. **Business Services** (can be started in parallel)

   ```bash
   # Product Service
   cd product
   mvn spring-boot:run

   # User Service
   cd user
   mvn spring-boot:run

   # Order Service
   cd order
   mvn spring-boot:run
   ```

## API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### Product Service

- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### User Service

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Order Service

- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `POST /api/orders` - Create new order
- `GET /api/cart/**` - Cart operations

## Monitoring and Management

- **Eureka Dashboard**: http://localhost:8761
- **Config Server**: http://localhost:8888
- **Zipkin Dashboard**: Configure Zipkin server URL for distributed tracing

## Service Discovery

All services automatically register with Eureka server. You can view registered services at:

```
http://localhost:8761
```

## Configuration Management

Service configurations are managed centrally in the Config Server. Configuration files are located in:

```
configserver/src/main/resources/config/
```

## Project Structure

```
ecom/
├── configserver/          # Configuration server
├── eureka/               # Service discovery
├── gateway/              # API Gateway
├── product/              # Product service
├── user/                 # User service
├── order/                # Order service
├── logs/                 # Application logs
└── docker-compose.yml    # Docker compose configuration
```

## Features

- ✅ Microservices architecture
- ✅ Service discovery with Eureka
- ✅ Centralized configuration
- ✅ API Gateway for request routing
- ✅ Load balancing
- ✅ Distributed tracing with Zipkin
- ✅ PostgreSQL database integration
- ✅ RabbitMQ message broker
- ✅ RESTful APIs

