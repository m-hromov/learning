package com.epam.esm.repository;

import com.epam.esm.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrdRepository<Long, Tag> {
    Tag save(Tag tag);

    boolean existsByName(String name);

    Optional<Tag> findById(Long id);

    Optional<Tag> findByName(String name);

    List<Tag> findAll();

    List<Tag> findAllByGiftCertificateId(Long certificateId);

    void delete(Long id);
}
