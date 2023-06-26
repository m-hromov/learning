package com.epam.esm.security.service.impl;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.model.SecurityToken;
import com.epam.esm.security.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    @Value("${security.secret}")
    private String secret;
    private static final int BEARER_LENGTH = 7;

    @Override
    public SecurityToken getSecurityToken(User user) {
        String access = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("scp", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(Instant.now()
                                .plus(60, ChronoUnit.MINUTES)) //TODO: could be moved to properties
                )
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return SecurityToken.builder()
                .accessToken(access)
                .build();
    }

    @Override
    public User getUser(String token) {
        Long userId = Long.valueOf((Integer) Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("userId"));
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new NotFoundException("User was not found.");
                });
    }

    @Override
    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getJwt(String header) {
        try {
            return header.substring(BEARER_LENGTH);
        } catch (Exception e) {
            return null;
        }
    }
}
