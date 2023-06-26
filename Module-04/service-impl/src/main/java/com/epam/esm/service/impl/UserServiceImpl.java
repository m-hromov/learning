package com.epam.esm.service.impl;

import com.epam.esm.Authority;
import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.exception.AuthenticationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.model.SecurityToken;
import com.epam.esm.security.service.JwtService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserLoginResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public SecurityToken signup(UserRegisterRequestDto requestDto) {
        User user = mapper.map(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setAuthorities(Set.of(Authority.USER));
        user = userRepository.save(user);
        return jwtService.getSecurityToken(user);
    }

    @Override
    public SecurityToken signin(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new AuthenticationException("Username or password is not valid"));
        boolean passMatches = passwordEncoder.matches(requestDto.getPassword(), user.getPassword());
        if (!passMatches) {
            throw new AuthenticationException("Username or password is not valid");
        }
        return jwtService.getSecurityToken(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User was not found."));
    }
}
