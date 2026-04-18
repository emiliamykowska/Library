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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(jwtTokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/books").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/reviews").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/loans/my").hasAnyRole("READER", "LIBRARIAN")
                                        .requestMatchers("/loans/borrow/*").hasRole("LIBRARIAN")
                                        .requestMatchers("/loans/borrow").hasAnyRole("READER", "LIBRARIAN")
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
