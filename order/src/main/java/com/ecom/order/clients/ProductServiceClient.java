package com.ecom.order.clients;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.ecom.order.dto.ProductResponse;

/**
 * Declarative HTTP client interface for Product Service communication.
 * 
 * This interface uses Spring's HTTP Interface pattern
 * (@GetExchange, @PostExchange, etc.)
 * to define REST API calls without writing boilerplate HTTP client code.
 * 
 * The implementation is auto-generated at runtime by HttpServiceProxyFactory
 * configured in ProductServiceClientConfig.
 * 
 * Key Features:
 * - Integrates with @LoadBalanced RestClient for Eureka service discovery
 * - Methods map directly to Product Service REST endpoints
 * - Returns Optional for graceful handling of 404 responses
 */
@HttpExchange
public interface ProductServiceClient {

    /**
     * Fetches product details by ID from Product Service.
     * 
     * Service Discovery Flow:
     * 1. This method call triggers: GET http://PRODUCT-SERVICE/api/products/{id}
     * 2. LoadBalancer intercepts and queries Eureka for "PRODUCT-SERVICE" instances
     * 3. Selects an available instance (e.g., 192.168.1.10:8081)
     * 4. Makes actual call: GET http://192.168.1.10:8081/api/products/{id}
     * 5. Returns response as ProductResponse DTO
     * 
     * @param id Product ID to fetch
     * @return Optional<ProductResponse> - contains product if found, empty if 404
     */
    @GetExchange("/api/products/{id}")
    Optional<ProductResponse> getProductById(@PathVariable Long id);
}
