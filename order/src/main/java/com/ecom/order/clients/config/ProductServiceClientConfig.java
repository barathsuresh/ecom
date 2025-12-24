package com.ecom.order.clients.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.ecom.order.clients.ProductServiceClient;

/**
 * Configuration class for setting up inter-service communication with Product
 * Service.
 * Creates the ProductServiceClient bean for making HTTP calls via Eureka
 * service discovery.
 */
@Configuration
public class ProductServiceClientConfig {

    /**
     * Creates the ProductServiceClient bean using HTTP Interface pattern.
     * This client will communicate with PRODUCT-SERVICE via Eureka.
     */
    @Bean
    public ProductServiceClient productServiceClient(
            @Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder
                .baseUrl("http://product-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (req, res) -> Optional.empty())
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ProductServiceClient.class);
    }
}
