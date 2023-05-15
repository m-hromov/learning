package com.epam.esm.service.impl;

import com.epam.esm.dto.CreateOrderRequestDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final GiftCertificateService giftCertificateService;
    private final UserService userService;
    private final OrderMapper mapper;

    @Override
    public List<OrderDto> findUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findUserOrders(userId, pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public OrderDto createOrder(CreateOrderRequestDto requestDto) {
        GiftCertificate giftCertificate = giftCertificateService.findByIdOrThrow(requestDto.getGiftCertificateId());
        User user = userService.findByIdOrThrow(requestDto.getUserId());
        Order order = Order.builder()
                .cost(giftCertificate.getPrice())
                .giftCertificate(giftCertificate)
                .user(user)
                .build();
        return mapper.map(orderRepository.save(order));
    }

    @Override
    public OrderInfoDto findOrderInfo(Long orderId) {
        return mapper.mapToOrderInfo(findByIdOrThrow(orderId));
    }

    private Order findByIdOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Order with id '%s' was not found", orderId)));
    }
}
