package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderDto> findUserOrders(Long userId, Pageable pageable);
    OrderDto createOrder(Long userId, Long certId);

    OrderInfoDto findOrderInfo(Long orderId);
}
