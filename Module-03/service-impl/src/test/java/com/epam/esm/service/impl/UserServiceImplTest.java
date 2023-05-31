package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapperImpl mapper;

    @Test
    void findAll() {
        Pageable pageable = Pageable.builder().size(10).page(10).build();
        User user = User.builder().username("name").build();
        when(userRepository.findAll(pageable)).thenReturn(List.of(user));

        List<UserLoginResponseDto> userResponse = userService.findAll(pageable);

        verify(userRepository).findAll(pageable);
        assertEquals( user.getUsername(), userResponse.get(0).getUsername());
    }

    @Test
    void register() {
        UserRegisterRequestDto user = UserRegisterRequestDto.builder().username("name").build();

        userService.register(user);

        verify(userRepository).save(mapper.map(user));
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
}