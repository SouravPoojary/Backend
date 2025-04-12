package com.example.Backend.config;


import com.example.Backend.filter.JwtAuthenticationFilter;
import com.example.Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**" ).permitAll()
//                        "/register/customer", "scenter/register"
                                .requestMatchers("/service/getAll", "/service/delete/id").hasAnyAuthority("ADMIN", "SERVICE_CENTER")
                                .requestMatchers("/appointment/getAll", "/appointment/delete/**").hasAnyAuthority("ADMIN", "CUSTOMER", "SERVICE_CENTER")
                                .requestMatchers( "/admin/customers/","admin/service-centers","/admin/customers/id","admin/service-centers/id").hasAnyAuthority("ADMIN")
                                .requestMatchers("/appointment/create").hasAnyAuthority("CUSTOMER")
                                .requestMatchers("/service/create","/appointment/id/status","/appointment/addJob/id").hasAnyAuthority("SERVICE_CENTER")
//                        .requestMatchers(
//                                "/service/getAll", "/service/delete/**",
//                                "/appointment/getAll", "/appointment/delete/**",
//                                "/customer/getAll", "/customer/delete/**",
//                                "/scenter/getAll", "/scenter/delete/**"
//                        ).hasAuthority("ADMIN")
//                                .requestMatchers(
//                    "/appointment/create",
//                    "/appointment/getAll",
//                    "/appointment/delete/**"
//                ).hasAuthority("CUSTOMER")
//
//                        .requestMatchers(
//                                "/service/create", "/service/getAll", "/service/delete/**",
//                                "/appointment/getAll", "/appointment/delete/**"
//                        ).hasAuthority("SERVICE_CENTER")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                .userDetailsService(userService)
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable); // or customize CSRF

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // frontend origin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization")); // if you're using JWT in headers
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

