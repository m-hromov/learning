package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
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
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag saveIfNotExists(Tag tag) {
        return tagRepository.findByName(tag.getName())
                .orElseGet(() -> tagRepository.save(tag));
    }

    @Override
    public List<Tag> findAllByCertificateId(Long id) {
        return tagRepository.findAllByGiftCertificateId(id);
    }

    @Override
    public List<TagDto> getTags() {
        return tagRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public void delete(Long id) {
        tagRepository.delete(id);
    }
}
