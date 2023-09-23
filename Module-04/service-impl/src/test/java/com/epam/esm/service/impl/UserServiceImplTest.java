package com.epam.esm.service.impl;

import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.security.model.SecurityToken;
import com.epam.esm.security.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Spy
    private UserMapperImpl mapper;

    @Test
    void findAll() {
        Pageable pageable = Pageable.builder().size(10).page(10).build();
        User user = User.builder().username("name").build();
        when(userRepository.findAll(pageable, )).thenReturn(List.of(user));

        List<UserLoginResponseDto> userResponse = userService.findAll(pageable);

        verify(userRepository).findAll(pageable, );
        assertEquals( user.getUsername(), userResponse.get(0).getUsername());
    }

    @Test
    void register() {
        UserRegisterRequestDto user = UserRegisterRequestDto.builder().username("name").build();
        SecurityToken token = SecurityToken.builder().accessToken("jwt").build();

        when(userRepository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(jwtService.getSecurityToken(any()))
                .thenReturn(token);

        userService.signup(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).save(captor.capture());
        assertEquals(token.getAccessToken(), captor.getValue().getJwt());
    }

    @Test
    void delete() {
        Long id = 100L;

        userService.delete(id);

        verify(userRepository).delete(id);
    }

    @Test
    void findByIdOrThrow() {
        Long id = 100L;
        when(userRepository.findById(id)).thenReturn(Optional.of(User.builder().build()));

        User user = userService.findByIdOrThrow(id);

        verify(userRepository).findById(id);
        assertNotNull(user);
    }

    @Test
    void findByIdOrThrowWhenNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findByIdOrThrow(100L));
    }

    @Test
    void testSignout() {
        Long userId = 123L;
        User user = User.builder().jwt("someJwt").id(userId).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.signout(userId);

        verify(userRepository).save(user);
        assertNull(user.getJwt());
    }
}