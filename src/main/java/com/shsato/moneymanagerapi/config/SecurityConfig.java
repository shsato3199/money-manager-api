package com.shsato.moneymanagerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    // Spring Securityの基本設定。
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CORS設定を有効にする。
                .cors(Customizer.withDefaults())
                // NuxtなどのSPAからPOST・PUT・DELETEするためのCSRF設定。
                // CSRFトークンをCookieへ保存し、
                // フロントからX-XSRF-TOKENヘッダーで送信できるようにする。
                .csrf(csrf -> csrf.spa())
                // URLごとのアクセス権限を設定する。
                .authorizeHttpRequests(auth -> auth
                        // 開発中はフロントからAPIを直接呼べるように、/api/** の認証を不要にする。
                        // Googleログイン実装後は認証必須に変更する。
                        .requestMatchers("/api/**").permitAll()
                        // /api/** 以外へのアクセスはログインを必要とする。
                        .anyRequest().authenticated()
                )
                // 現在は動作確認用のフォームログインを使用する。
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    // NuxtからSpring Bootへの通信を許可するCORS設定。
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        // Nuxtの開発環境からのアクセスを許可する。
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        // APIで使用するHTTPメソッドを許可する。
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // リクエストヘッダーを許可する。
        configuration.setAllowedHeaders(List.of("*"));
        // Session Cookieを送受信できるようにする。
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // すべてのパスに上記CORS設定を適用する。
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}