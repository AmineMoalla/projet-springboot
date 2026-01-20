package com.iit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.iit.services.ApplicationUserService;

@Configuration
public class SecurityConfig {

    private final ApplicationUserService userService;

    public SecurityConfig(ApplicationUserService userDetailsService) {
        this.userService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.csrf().disable()
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers("/api/auth/**").permitAll()
    //             .requestMatchers("/admin/**").permitAll()
    //             .requestMatchers("/admin/demande/**").permitAll()
    //             .requestMatchers("/admin/formateur/**").hasRole("ADMIN")
    //             //.requestMatchers("/admin/**").authenticated()
    //             .anyRequest().authenticated()
    //         );
    //     return http.build();
    // }


      @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll()
        );

    return http.build();
}


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .authenticationProvider(authenticationProvider())
                   .build();
    }

}