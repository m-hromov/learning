package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper mapper;

    @Override
    public TagDto save(Tag tag) {
        return mapper.map(tagRepository.save(tag));
    }

    @Override
    public List<Tag> findAllByCertificateId(Long id) {
        return tagRepository.findAllByGiftCertificateId(id);
    }

    @Override
    public List<TagDto> getTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(id);
    }

    @Override
    public TagDto findMostWidelyUsedTagOfUserWithHighestOrderCost() {
        return tagRepository.findMostWidelyUsedTagOfUserWithHighestOrderCost()
                .map(mapper::map)
                .orElseThrow(() -> new NotFoundException("Tag was not found"));
    }
}
