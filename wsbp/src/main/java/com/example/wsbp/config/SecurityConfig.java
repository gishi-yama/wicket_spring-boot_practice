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

  // 認証無しでアクセスできるパスの一覧（Wicket のリソースや静的ファイルなども含める）
  private static final String[] PUBLIC_ENDPOINTS = {
      "/",
      "/Home", // ルート以外から HomePage へアクセスする場合も認証不要にする
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
            // 上記以外は必ず認証させる
            .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
            .anyRequest().authenticated()
        )
        .oauth2Login(oauth -> oauth
            // Azure AD によるログインも Sign ページをエントリポイントにする
            .loginPage("/Sign")
            .defaultSuccessUrl("/Signed")
        )
        .logout(logout -> logout
            // ログアウト完了後に Sign ページへ戻す（Wicket セッションも同時に破棄される）
            .logoutSuccessUrl("/Sign?logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
        )
        // Wicket 側で CSRF を見ていないため、今回のサンプルでは無効化しておく
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
