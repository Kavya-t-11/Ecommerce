package com.ecommerce.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Swagger GET endpoints
                .requestMatchers("/swagger-ui/**",
                                 "/swagger-ui.html",
                                 "/v3/api-docs/**",
                                 "/api-docs/**",
                                 "/swagger-resources/**",
                                 "/swagger-resources/configuration/ui",
                                 "/swagger-resources/configuration/security",
                                 "/webjars/**").permitAll()
                // Public APIs
                .requestMatchers("/api/user/**",
                                 "/api/product/**",
                                 "/api/orders/**",
                                 "/api/cart/**",
                                 "/api/**").permitAll()
                // Everything else secured
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
