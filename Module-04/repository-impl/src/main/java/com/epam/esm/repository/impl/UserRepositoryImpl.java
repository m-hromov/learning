package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {
    private static final String FIND_ALL = """
            SELECT u FROM User u
            """;
    private static final String FIND_BY_USERNAME = """
            SELECT u FROM User u
            WHERE u.username = :username
            """;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(Pageable paging) {
        TypedQuery<User> findAllQuery =
                entityManager.createQuery(FIND_ALL, User.class);
        findAllQuery.setFirstResult((paging.getPage() - 1) * paging.getSize());
        findAllQuery.setMaxResults(paging.getSize());
        return findAllQuery.getResultList();
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        TypedQuery<User> findByUsername = entityManager.createQuery(FIND_BY_USERNAME, User.class);
        findByUsername.setParameter("username", username);
        return findByUsername.getResultStream().findFirst();
    }
}
