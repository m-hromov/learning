package com.epam.esm.dto;

import com.epam.esm.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto extends RepresentationModel<UserLoginResponseDto> {
    private Long id;
    private String username;
    private Set<Authority> authorities;
}
