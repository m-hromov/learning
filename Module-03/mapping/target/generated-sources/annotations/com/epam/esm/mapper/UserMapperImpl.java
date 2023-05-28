package com.epam.esm.mapper;

import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-16T01:11:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserLoginResponseDto map(User source) {
        if ( source == null ) {
            return null;
        }

        UserLoginResponseDto.UserLoginResponseDtoBuilder userLoginResponseDto = UserLoginResponseDto.builder();

        if ( source.getId() != null ) {
            userLoginResponseDto.id( source.getId() );
        }
        if ( source.getUsername() != null ) {
            userLoginResponseDto.username( source.getUsername() );
        }

        return userLoginResponseDto.build();
    }

    @Override
    public User map(UserRegisterRequestDto source) {
        if ( source == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        if ( source.getUsername() != null ) {
            user.username( source.getUsername() );
        }
        if ( source.getPassword() != null ) {
            user.password( source.getPassword() );
        }

        return user.build();
    }
}
