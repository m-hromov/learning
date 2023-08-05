package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OrderMapper {
    @Mapping(source = "id", target = "orderId")
    OrderInfoDto mapToOrderInfo(Order source);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "giftCertificate.id", target = "giftCertificateId")
    OrderDto map(Order source);
}
