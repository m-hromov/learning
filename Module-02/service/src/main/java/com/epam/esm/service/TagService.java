package com.epam.esm.service;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);

    Tag saveIfNotExists(Tag tag);

    List<Tag> findAllByCertificateId(Long id);

    List<Tag> getTags();

    void delete(Long id);
}
