package se.home.graph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Client {
    @Bean
    WebClient webClient(){
        return WebClient.create();
    }
}
