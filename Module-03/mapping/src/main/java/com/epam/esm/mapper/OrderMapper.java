package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {
    OrderInfoDto mapToOrderInfo(Order source);
    OrderDto map(Order source);
}
