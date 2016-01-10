package com.github.gauthierj.dsm.webapi.client;

import com.github.gauthierj.dsm.webapi.client.authentication.AuthenticationProvider;
import com.github.gauthierj.dsm.webapi.client.authentication.PropertiesAuthenticationProvider;
import com.github.gauthierj.dsm.webapi.client.timezone.PropertiesTimeZoneProvider;
import com.github.gauthierj.dsm.webapi.client.timezone.TimeZoneProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Import(DsmWebapiClientConfig.class)
public class TestConfiguration {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new PropertiesAuthenticationProvider();
    }

    @Bean
    public DsmUrlProvider urlProvider() {
        return new PropertiesDsmUrlProvider();
    }

    @Bean
    public TimeZoneProvider timeZoneProvider() {
        return new PropertiesTimeZoneProvider();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
