package com.epam.esm.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityToken {
    private final String accessToken;
}
