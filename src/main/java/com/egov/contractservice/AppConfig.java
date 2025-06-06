package com.egov.contractservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Configuration
public class AppConfig
{
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Autowired
    EurekaDiscoveryClient discoveryClient;
    @Autowired
    private ProjectRepository projectRepository;

    @Bean
    public WebClient authValidateWebClient(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder
                .baseUrl("http://auth-service/api/v1/validate")
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient authValidateWebClientEurekaDiscovered(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("auth-service");
        log.info("instance " +instances);
        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for auth-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/validate", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient hotelServicedaysupdate(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("hotel-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for hotel-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/gethotelname", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }

    @Bean
    @Scope(value = "prototype")
    public WebClient matchProConEurekaBalanced(WebClient.Builder webClientBuilder)
    {
        List<ServiceInstance>  instances =   discoveryClient.getInstances("match-service");

        if(instances.isEmpty())
        {
            throw new RuntimeException("No instances found for match-service");
        }

        // Assuming you want to use the first instance and can be replaced by a load balancing strategy
        String hostname = instances.get(0).getHost();
        String port = String.valueOf(instances.get(0).getPort());

        return webClientBuilder
                .baseUrl(String.format("http://%s:%s/api/v1/proconlink", hostname, port))
                .filter(new LoggingWebClientFilter())
                .build();
    }




}
