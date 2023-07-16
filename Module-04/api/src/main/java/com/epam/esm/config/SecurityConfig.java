package com.epam.esm.config;

import com.epam.esm.Authority;
import com.epam.esm.security.filter.JwtAuthFilter;
import com.epam.esm.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    protected static final String[] PERMITTED_GET_OPERATIONS = {"/gifts/**", "/tags/**"};
    protected static final String[] PERMITTED_ALL_OPERATIONS = {"/users/signin", "/users/signup", "/swagger-ui/**", "/v3/**", "/error"};
    private final AuthenticationEntryPoint authEntryPoint;

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtService jwtService,
                                       @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        return new JwtAuthFilter(
                PERMITTED_GET_OPERATIONS,
                PERMITTED_ALL_OPERATIONS,
                jwtService,
                resolver
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        return http.formLogin().disable()
                .csrf()
                .disable()
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.GET, PERMITTED_GET_OPERATIONS).permitAll()
                                .requestMatchers(PERMITTED_ALL_OPERATIONS).permitAll()
                                .requestMatchers("/users/signout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority(
                                        Authority.ADMIN.name(), Authority.USER.name()
                                )
                                .anyRequest().hasAuthority(Authority.ADMIN.name())

                )
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
