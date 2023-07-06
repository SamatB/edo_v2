package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * KeyCloak + security конфигурация.
 * с парсером ролей и логаут реализацией.
 */

@Configuration
@EnableWebSecurity
public class KeycloakSecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    private final KeycloakLogoutHandler keycloakLogoutHandler;

    public KeycloakSecurityConfig(JwtAuthConverter jwtAuthConverter, KeycloakLogoutHandler keycloakLogoutHandler) {
        this.jwtAuthConverter = jwtAuthConverter;
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringRequestMatchers("/eureka/**");
        http
                .authorizeHttpRequests(c -> c.requestMatchers("/error").permitAll()
                        .requestMatchers("/eureka/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login()
                .and()
                .logout()
                .addLogoutHandler(keycloakLogoutHandler)
                .logoutSuccessUrl("/");
        http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthConverter);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
}