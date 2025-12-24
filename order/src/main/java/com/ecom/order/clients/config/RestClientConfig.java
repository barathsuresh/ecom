package com.ecom.order.clients.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    /**
     * Primary (non-load-balanced) RestClient.Builder.
     * Used by Eureka's internal HTTP client to avoid circular dependency.
     */
    @Bean
    @Primary
    public RestClient.Builder defaultRestClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Load-balanced RestClient.Builder for service discovery.
     * Explicitly qualified to avoid conflicts with the primary bean.
     */
    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedRestClientBuilder")
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }
}
