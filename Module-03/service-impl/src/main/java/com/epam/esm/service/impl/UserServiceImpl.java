package com.epam.esm.service.impl;

import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public List<UserLoginResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public UserLoginResponseDto register(UserRegisterRequestDto requestDto) {
        User user = mapper.map(requestDto);
        return mapper.map(userRepository.save(user));
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
