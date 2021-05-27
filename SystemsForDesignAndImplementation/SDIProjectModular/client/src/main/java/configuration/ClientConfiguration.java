package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by radu.
 */
@Configuration
public class ClientConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
