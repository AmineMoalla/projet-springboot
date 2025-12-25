/*package com.iit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // désactive CSRF pour Postman
            .authorizeHttpRequests()
            .anyRequest().permitAll(); // autorise toutes les requêtes sans authentification
        return http.build();
    }
}*/
