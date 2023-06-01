package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.model.paging.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    List<Order> findUserOrders(Long userId, Pageable pageable);

    Optional<Order> findById(Long orderId);
}
