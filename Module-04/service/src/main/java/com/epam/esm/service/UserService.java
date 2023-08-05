package com.epam.esm.service;

import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.security.model.SecurityToken;

import java.util.List;

public interface UserService {
    List<UserLoginResponseDto> findAll(Pageable pageable);

    SecurityToken signup(UserRegisterRequestDto requestDto);

    SecurityToken signin(UserLoginRequestDto requestDto);

    void signout(Object principal);

    void delete(Long id);

    User findByIdOrThrow(Long id);
}
