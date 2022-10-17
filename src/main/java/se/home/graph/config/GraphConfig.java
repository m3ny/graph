package se.home.graph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.UnifiedJedis;

@Configuration
public class GraphConfig {

    @Bean
    UnifiedJedis unifiedJedis(){
        return new UnifiedJedis(new HostAndPort("localhost", 6379));
    }

}
