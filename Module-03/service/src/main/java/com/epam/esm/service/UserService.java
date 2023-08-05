package com.epam.esm.service;

import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface UserService {
    List<UserLoginResponseDto> findAll(Pageable pageable);

    UserLoginResponseDto register(UserRegisterRequestDto requestDto);

    void delete(Long id);

    User findByIdOrThrow(Long id);
}
