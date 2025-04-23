package com.example.asyncpoc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
public class ThreadScopeConfig {

    @Bean
    public static CustomScopeConfigurer threadScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<>();
        scopes.put("thread", new SimpleThreadScope());
        configurer.setScopes(scopes);
        return configurer;
    }
}
