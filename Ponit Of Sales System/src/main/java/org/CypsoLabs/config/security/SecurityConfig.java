package org.CypsoLabs.config.security;

import org.CypsoLabs.service.Impl.CustomUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final CustomUserDetailServiceImpl customUserDetailServiceImpl;

    @Autowired
    public SecurityConfig(CustomUserDetailServiceImpl customUserDetailServiceImpl, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.customUserDetailServiceImpl = customUserDetailServiceImpl;
        this.jwtAuthEntryPoint=jwtAuthEntryPoint;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(sessionManagement->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/auth/**").permitAll() // Permit all requests to /api/auth/**
                                .requestMatchers("/login").permitAll() // Permit all requests to /login
                                .anyRequest().authenticated() // Require authentication for all other requests
                )
                .httpBasic(withDefaults()); // Enable HTTP Basic authentication

        http.addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("password"))  // Encode the password
//                .roles("ADMIN")
//                .build();
//
//        UserDetails cashier = User.builder()
//                .username("cashier")
//                .password(passwordEncoder().encode("password"))  // Encode the password
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, cashier);
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
}