package edu.bi.springdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


// ---JWT---
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTTokenService jwtTokenService;

    @Autowired
    public SecurityConfig(JWTTokenService jwtTokenService){
        this.jwtTokenService = jwtTokenService;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .addFilterBefore(new JWTTokenFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/books").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers(HttpMethod.GET, "/books/search").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers(HttpMethod.GET, "/reviews").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers(HttpMethod.POST, "/reviews/*").hasRole("LIBRARIAN")
                                        .requestMatchers(HttpMethod.POST, "/reviews").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers(HttpMethod.PATCH, "/reviews/**").hasRole("LIBRARIAN")
                                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").hasRole("LIBRARIAN")
                                        .requestMatchers("/loans/my").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/loans/borrow/*").hasRole("LIBRARIAN")
                                        .requestMatchers("/loans/borrow").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/loans/return/*").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/**").hasRole("LIBRARIAN")
                )
                .exceptionHandling(e ->
                        e.accessDeniedHandler(((request, response, accessDeniedException) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value()); //code 403
                                    response.getWriter().write("""
                                    {"message": "Access denied, your role is insufficient"}""");
                                })))

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}

// ---SESSION---
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig{
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        authorizationManagerRequestMatcherRegistry ->
//                                authorizationManagerRequestMatcherRegistry
//                                        .requestMatchers("/login").permitAll()
//                                        .requestMatchers(HttpMethod.GET, "/books").hasAnyRole("READER", "LIBRARIAN")
//                                        .requestMatchers("/reviews").hasAnyRole("READER", "LIBRARIAN")
//                                        .requestMatchers("/**").hasRole("LIBRARIAN")
//                )
//                .sessionManagement(httpSecuritySessionManagementConfigurer ->
//                        httpSecuritySessionManagementConfigurer
//                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) //changed from stateless, so session can store state
//                .build();
//    }
//}
