package com.martins.security;

import com.martins.filter.AuthenticationFilter;
import com.martins.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtConfig jwtConfig;
    private final AuthorizationFilter authorizationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authManager, jwtConfig);
        authenticationFilter.setFilterProcessesUrl("/auth0/token");

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/roles/**").hasAuthority("ROLE_MASTER")//Permissão de ROLE_MASTER para todos endpoint referentes a roles
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ROLE_MASTER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyAuthority("ROLE_MASTER", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                // Ordem importa:
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
