package com.epam.esm.mapper;

import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    UserLoginResponseDto map(User source);
    User map(UserRegisterRequestDto source);
}
