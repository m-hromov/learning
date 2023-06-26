package com.epam.esm.security.service;

import com.epam.esm.model.User;
import com.epam.esm.security.model.SecurityToken;

public interface JwtService {
    SecurityToken getSecurityToken(User user);

    User getUser(String token);

    boolean isValid(String token);

    String getJwt(String header);
}
