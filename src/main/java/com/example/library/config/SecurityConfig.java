package com.example.library.config;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/index.html", "/login.html", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll() // Allow H2 console access
                .requestMatchers("/api/books").permitAll() // GET all books - public
                .requestMatchers("/api/books/search").permitAll() // Search - public
                .requestMatchers("/api/books/status/**").permitAll() // Status filter - public
                .requestMatchers("/api/user").permitAll() // User info - public
                .requestMatchers("/api/test-auth").permitAll() // Test auth - public
                .requestMatchers("/api/books/*/borrow").hasAnyRole("USER", "ADMIN") // Borrow - authenticated
                .requestMatchers("/api/books/*/return").hasAnyRole("USER", "ADMIN") // Return - authenticated
                .requestMatchers("POST", "/api/books").hasRole("ADMIN") // Create book - admin only
                .requestMatchers("PUT", "/api/books/*").hasRole("ADMIN") // Update book - admin only
                .requestMatchers("DELETE", "/api/books/*").hasRole("ADMIN") // Delete book - admin only
                .requestMatchers("GET", "/api/books/*").permitAll() // Get specific book - public
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true) // Force redirect to home page
                .failureUrl("/login.html?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .expiredUrl("/login.html?expired=true")
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Allow H2 console frames

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 