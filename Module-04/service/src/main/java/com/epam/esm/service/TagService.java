package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface TagService {
    TagDto save(Tag tag);

    List<Tag> findAllByCertificateId(Long id);

    List<TagDto> getTags(Pageable pageable);

    void delete(Long id);

    TagDto findMostWidelyUsedTagOfUserWithHighestOrderCost();
}
