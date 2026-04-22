package com.backend.oilmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain springSecurityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider provider) throws Exception {

        http.authenticationProvider(provider);

        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login","/api/signup", "/error").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form ->
                        form.defaultSuccessUrl("/home", true).permitAll())
                .logout(logout ->
                        logout.logoutSuccessUrl("/login?logout"))
                .build();
    }
}
