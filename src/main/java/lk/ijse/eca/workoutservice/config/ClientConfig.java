package lk.ijse.eca.workoutservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final HeaderPropagationInterceptor headerPropagationInterceptor;

    @Bean
    @Primary
    public RestClient.Builder defaultRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(@LoadBalanced RestClient.Builder loadBalancedRestClientBuilder) {
        return loadBalancedRestClientBuilder
                .baseUrl("http://MEMBER-SERVICE")
                .requestInterceptor(headerPropagationInterceptor)
                .build();
    }
}

