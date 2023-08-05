package com.epam.esm.service.impl;

import com.epam.esm.dto.CreateOrderRequestDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Spy
    private OrderMapper mapper = Mappers.getMapper(OrderMapper.class);
    @Mock
    private UserService userService;
    @Mock
    private GiftCertificateService giftCertificateService;

    @Test
    void testFindUserOrders() {
        final Long userId = 100L;
        Order order = Order.builder().build();

        when(orderRepository.findUserOrders(userId, Pageable.builder().build())).thenReturn(List.of(order));

        List<OrderDto> orders = orderService.findUserOrders(userId, Pageable.builder().build());

        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void testCreateOrder() {
        final Long giftCertificateId = 100L;
        final Long userId = 101L;

        when(giftCertificateService.findByIdOrThrow(giftCertificateId)).thenReturn(GiftCertificate.builder().build());
        when(userService.findByIdOrThrow(userId)).thenReturn(User.builder().build());

        orderService.createOrder(
                CreateOrderRequestDto.builder()
                        .userId(userId)
                        .giftCertificateId(giftCertificateId)
                        .build()
        );

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());

        assertNotNull(captor.getValue().getGiftCertificate());
        assertNotNull(captor.getValue().getUser());
    }
}