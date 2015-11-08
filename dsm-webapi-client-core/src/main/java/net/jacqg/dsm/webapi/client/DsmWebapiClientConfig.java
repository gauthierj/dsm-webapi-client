package net.jacqg.dsm.webapi.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan
public class DsmWebapiClientConfig {

    @Bean
    public DsmWebapiClient unauthenticated() {
        return new DsmWebapiClientImpl();
    }

    @Bean
    @Primary
    public DsmWebapiClient dsmRestClient() {
        return new AuthenticatedDsmWebapiClient();
    }

    @Bean
    public RestTemplate dsmWebapiClientRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        restTemplate.setMessageConverters(Collections.singletonList(messageConverter));
        restTemplate.setInterceptors(Collections.singletonList(new LoggingInterceptor()));
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return restTemplate;
    }
}
