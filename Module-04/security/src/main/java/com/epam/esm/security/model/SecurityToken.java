package com.epam.esm.security.model;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
public class SecurityToken {
    private final String accessToken;
}
