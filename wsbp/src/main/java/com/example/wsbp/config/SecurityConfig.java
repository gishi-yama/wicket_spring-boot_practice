package com.example.wsbp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] PUBLIC_ENDPOINTS = {
      "/",
      "/Sign",
      "/webjars/**",
      "/css/**",
      "/js/**",
      "/images/**",
      "/wicket/resource/**",
      "/login/oauth2/**",
      "/error"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth -> oauth
            .loginPage("/Sign")
            .defaultSuccessUrl("/Signed")
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/Sign?logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
        )
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
