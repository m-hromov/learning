package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private static final String FIND_ALL = """
            SELECT o FROM User u
            JOIN Order o
            WHERE u.id = :userId
            """;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public List<Order> findUserOrders(Long userId, Pageable pageable) {
        TypedQuery<Order> findAllQuery =
                entityManager.createQuery(FIND_ALL, Order.class);
        findAllQuery.setFirstResult((pageable.getPage() - 1) * pageable.getSize());
        findAllQuery.setMaxResults(pageable.getSize());
        findAllQuery.setParameter("userId", userId);
        return findAllQuery.getResultList();
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(entityManager.find(Order.class, orderId));
    }
}
