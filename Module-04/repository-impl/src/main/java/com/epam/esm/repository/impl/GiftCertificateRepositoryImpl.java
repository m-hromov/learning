package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.GiftCertificateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String FIND_ALL = """
            SELECT gc FROM GiftCertificate gc
            """;
    private static final String FIND_ALL_WITH_TAGS = """
            SELECT gc FROM GiftCertificate gc
            JOIN gc.tags tag
            JOIN Tag t ON t.id = tag.id
            WHERE t.id IN (:tags)
            GROUP BY gc.id
            HAVING COUNT(gc.id) = :tagsCount
            """;
    private static final String FIND_ALL_ORDERED_BY_NAME = """
            SELECT gc FROM GiftCertificate gc ORDER BY gc.name %s
            """;
    private static final String FIND_ALL_ORDERED_BY_CREATION = """
            SELECT gc FROM GiftCertificate gc ORDER BY gc.create_date %s
            """;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public GiftCertificate save(GiftCertificate cert) {
        entityManager.persist(cert);
        return cert;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(Pageable paging) {
        TypedQuery<GiftCertificate> findAllQuery =
                entityManager.createQuery(FIND_ALL, GiftCertificate.class);
        findAllQuery.setFirstResult((paging.getPage() - 1) * paging.getSize());
        findAllQuery.setMaxResults(paging.getSize());
        return findAllQuery.getResultList();
    }

    @Override
    public List<GiftCertificate> findAllOrderedByName(String order, Pageable paging) {
        return findAllOrdered(order, paging, FIND_ALL_ORDERED_BY_NAME);
    }

    @Override
    public List<GiftCertificate> findAllOrderedByCreationDate(String order, Pageable paging) {
        return findAllOrdered(order, paging, FIND_ALL_ORDERED_BY_CREATION);
    }

    @Override
    public List<GiftCertificate> findAllWithTags(List<Long> tags, Pageable paging) {
        TypedQuery<GiftCertificate> findAllQuery =
                entityManager.createQuery(FIND_ALL_WITH_TAGS, GiftCertificate.class);
        findAllQuery.setFirstResult((paging.getPage() - 1) * paging.getSize());
        findAllQuery.setMaxResults(paging.getSize());
        findAllQuery.setParameter("tags", tags);
        findAllQuery.setParameter("tagsCount", tags.size());
        return findAllQuery.getResultList();
    }

    private List<GiftCertificate> findAllOrdered(String order, Pageable paging, String query) {
        TypedQuery<GiftCertificate> findAllQuery =
                entityManager.createQuery(String.format(query, order), GiftCertificate.class);
        findAllQuery.setFirstResult((paging.getPage() - 1) * paging.getSize());
        findAllQuery.setMaxResults(paging.getSize());
        return findAllQuery.getResultList();
    }

    @Override
    public void delete(Long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }
}
