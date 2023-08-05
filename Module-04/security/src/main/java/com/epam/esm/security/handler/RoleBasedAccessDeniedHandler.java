package com.epam.esm.security.handler;

import com.epam.esm.exception.AuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RoleBasedAccessDeniedHandler implements AccessDeniedHandler {
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Override
    public void handle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        resolver.resolveException(request, response, null, new AuthenticationException("Access denied."));
    }
}
