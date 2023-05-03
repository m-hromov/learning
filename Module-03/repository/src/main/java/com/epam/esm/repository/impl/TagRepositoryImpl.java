package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private static final String FIND_BY_NAME = """
            SELECT t FROM Tag t
            WHERE t.name = :name
            """;
    private static final String FIND_ALL = """
            SELECT t FROM Tag t
            """;
    private static final String FIND_ALL_BY_CERTIFICATE = """
            SELECT tag FROM User u
            JOIN Order o ON o.user = u
            JOIN GiftCertificate gc ON o.giftCertificate = gc
            JOIN gc.tags tag
            WHERE gc.id = :gcId
            """;
    private static final String FIND_USER_WITH_HIGEST_ORDER_COST = """
            SELECT u FROM User u 
            JOIN Order o ON o.user = u
            JOIN GiftCertificate gc ON o.giftCertificate = gc
            JOIN gc.tags tag
            GROUP BY u.id
            HAVING SUM(o.cost) >= ALL(
                SELECT SUM (o.cost) FROM User u 
                JOIN Order o ON o.user = u
                GROUP BY u.id
            )
            """;
    private static final String FIND_MOST_WIDELY_USED_TAG_BY_USER_ID = """
            SELECT tag FROM User u
            JOIN Order o ON o.user = u
            JOIN GiftCertificate gc ON o.giftCertificate = gc
            JOIN gc.tags tag
            WHERE u.id = :userId
            GROUP BY tag.id
            HAVING COUNT(tag.id) >= ALL(
                SELECT COUNT(tag.id) FROM User u
                JOIN Order o ON o.user = u
                JOIN GiftCertificate gc ON o.giftCertificate = gc
                JOIN gc.tags tag
                WHERE u.id = :userId
                GROUP BY tag.id
            )
            """;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTagOfUserWithHighestOrderCost() {
        TypedQuery<User> typedUserQuery = entityManager.createQuery(FIND_USER_WITH_HIGEST_ORDER_COST, User.class);
        TypedQuery<Tag> typedQuery = entityManager.createQuery(FIND_MOST_WIDELY_USED_TAG_BY_USER_ID, Tag.class);
        return typedUserQuery.getResultStream()
                .findFirst()
                .map(User::getId)
                .flatMap(userId -> {
                    typedQuery.setParameter("userId", userId);
                    return typedQuery.getResultStream().findFirst();
                });
    }

    @Override
    public Optional<Tag> findByName(String name) {
        TypedQuery<Tag> typedQuery = entityManager.createQuery(FIND_BY_NAME, Tag.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getResultStream().findFirst();
    }

    @Override
    public List<Tag> findAll(Pageable paging) {
        TypedQuery<Tag> findAllQuery =
                entityManager.createQuery(FIND_ALL, Tag.class);
        findAllQuery.setFirstResult((paging.getPage() - 1) * paging.getSize());
        findAllQuery.setMaxResults(paging.getSize());
        return findAllQuery.getResultList();
    }

    @Override
    public List<Tag> findAllByGiftCertificateId(Long certificateId) {
        TypedQuery<Tag> typedQuery = entityManager.createQuery(FIND_ALL_BY_CERTIFICATE, Tag.class);
        typedQuery.setParameter("gcId", certificateId);
        return typedQuery.getResultList();
    }

    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }
}
