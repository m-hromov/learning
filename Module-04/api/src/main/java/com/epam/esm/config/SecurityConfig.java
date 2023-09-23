package com.epam.esm.config;

import com.epam.esm.Authority;
import com.epam.esm.security.filter.JwtAuthFilter;
import com.epam.esm.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    protected static final String[] PERMITTED_GET_OPERATIONS = {"/gifts/**", "/tags/**", "/image-storage/**", "/favicon.ico"};
    protected static final String[] PERMITTED_ALL_OPERATIONS = {"/users/signin", "/users/signup", "/swagger-ui/**", "/v3/**", "/error"};
    private final AuthenticationEntryPoint authEntryPoint;
    @Value("${security.enabled: true}")
    private boolean securityEnabled;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/image-storage/**")
                        .addResourceLocations("file:image-storage/");
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtService jwtService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            AccessDeniedHandler accessDeniedHandler
    ) throws Exception {
        if (securityEnabled) {
            return http.formLogin().disable()
                    .csrf()
                    .disable()
                    .cors()
                    .and()
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
                    .addFilterBefore(new JwtAuthFilter(
                            PERMITTED_GET_OPERATIONS,
                            PERMITTED_ALL_OPERATIONS,
                            jwtService,
                            resolver
                    ), UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .build();
        } else {
            return http.formLogin().disable()
                    .csrf()
                    .disable()
                    .cors()
                    .and()
                    .authorizeHttpRequests(auth ->
                            auth.anyRequest().permitAll()

                    )
                    .build();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
