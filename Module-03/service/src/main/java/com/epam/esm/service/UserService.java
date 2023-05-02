package com.epam.esm.service;

import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface UserService {
    List<UserLoginResponseDto> findAll(Pageable pageable);

    UserLoginResponseDto login(UserLoginRequestDto requestDto);

    void delete(Long id);
}
