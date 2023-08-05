package com.epam.esm.security.filter;

import com.epam.esm.Authority;
import com.epam.esm.exception.AuthenticationException;
import com.epam.esm.model.User;
import com.epam.esm.security.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final String[] PERMITTED_GET_OPERATIONS;
    private final String[] PERMITTED_ALL_OPERATIONS;
    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            String jwt = jwtService.getJwt(header);
            if (jwt == null) {
                resolver.resolveException(request, response, null, new AuthenticationException("Unauthorized"));
                return;
            }
            User user = jwtService.getUser(jwt);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getId(),
                    null,
                    map(user.getAuthorities())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException exception) {
            log.warn("JWT is malformed", exception);
            resolver.resolveException(request, response, null, new AuthenticationException("JWT is malformed"));
        } catch (ExpiredJwtException exception) {
            log.warn("JWT is expired");
            resolver.resolveException(request, response, null, new AuthenticationException("JWT is expired"));
        } catch (AuthenticationException exception) {
            resolver.resolveException(request, response, null, exception);
        } catch (Exception exception) {
            log.warn("Unauthorized", exception);
            resolver.resolveException(request, response, null, new AuthenticationException("Unauthorized"));
        }
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        List<RequestMatcher> permitAll = new ArrayList<>();
        List<RequestMatcher> permitAllGet = new ArrayList<>();
        Arrays.stream(PERMITTED_ALL_OPERATIONS).forEach(s -> permitAll.add(new AntPathRequestMatcher(s)));
        Arrays.stream(PERMITTED_GET_OPERATIONS).forEach(s -> permitAllGet.add(new AntPathRequestMatcher(s)));
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(permitAll);
        OrRequestMatcher orGetRequestMatcher = new OrRequestMatcher(permitAllGet);
        return orRequestMatcher.matches(request)
               || (request.getMethod().equals(HttpMethod.GET.name()) && orGetRequestMatcher.matches(request));
    }

    private Set<GrantedAuthority> map(Collection<Authority> authorities) {
        return authorities.stream()
                .map(Authority::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
