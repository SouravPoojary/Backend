package com.example.demo.config;

import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 @Autowired
 private UserService userService;
 @Autowired
    private JwtAuthenticationFilter authenticationFilter;

 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
     return httpSecurity
             .csrf(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(
                     req->req.requestMatchers("login/**","register/**")
                             .permitAll().
                             requestMatchers("history/getById","appointment/getAll").hasAnyAuthority("ADMIN","TECHNICIAN","USER")
                             .requestMatchers("technician_details/getAll").hasAnyAuthority("ADMIN","USER")
                             .requestMatchers("feedback/getAll","service_type/getAll","vehicle_details/getAll","vehicle_owner/getAll","technician_details/getById").hasAnyAuthority("ADMIN","TECHNICIAN")
                             .requestMatchers("appointment/**","technician_details/**","technician_status/delete","technician_status/getAll").hasAuthority("ADMIN")
                             .requestMatchers("feedback/**","service_type/**","vehicle_details/**","vehicle_owner/**").hasAuthority("USER")
                             .requestMatchers("history/**","technician_status/**").hasAuthority("TECHNICIAN")
                             .anyRequest()
                             .authenticated()
             ).userDetailsService(userService)
             .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
             .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
             .build();
 }

 @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
     return configuration.getAuthenticationManager();
 }

 @Bean
    public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
 }
}
