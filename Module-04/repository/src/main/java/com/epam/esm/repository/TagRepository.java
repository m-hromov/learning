package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrdRepository<Long, Tag> {
    Optional<Tag> findMostWidelyUsedTagOfUserWithHighestOrderCost();

    Optional<Tag> findByName(String name);

    List<Tag> findAllByGiftCertificateId(Long certificateId);

    void deleteAll();
}
