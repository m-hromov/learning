package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.TagMapperImpl;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Spy
    private TagMapperImpl mapper;

    @Test
    void save() {
        Tag tag = Tag.builder().id(100L).build();

        tagService.save(tag);

        verify(tagRepository).save(tag);
    }

    @Test
    void findAllByCertificateId() {
        Long id = 100L;

        tagService.findAllByCertificateId(id);

        verify(tagRepository).findAllByGiftCertificateId(id);
    }

    @Test
    void getTags() {
        Pageable pageable = Pageable.builder().size(10).page(10).build();
        Tag tag = Tag.builder().name("name").build();
        when(tagRepository.findAll(pageable)).thenReturn(List.of(tag));

        List<TagDto> tagDto = tagService.getTags(pageable);

        verify(tagRepository).findAll(pageable);
        assertEquals( tag.getName(), tagDto.get(0).getName());
    }

    @Test
    void delete() {
        Long id = 100L;

        tagService.delete(id);

        verify(tagRepository).delete(id);
    }

    @Test
    void findMostWidelyUsedTagOfUserWithHighestOrderCost() {
        Tag tag = Tag.builder().name("name").build();
        when(tagRepository.findMostWidelyUsedTagOfUserWithHighestOrderCost()).thenReturn(Optional.of(tag));

        TagDto tagDto = tagService.findMostWidelyUsedTagOfUserWithHighestOrderCost();

        verify(tagRepository).findMostWidelyUsedTagOfUserWithHighestOrderCost();
        assertNotNull(tagDto);
    }

    @Test
    void findMostWidelyUsedTagOfUserWithHighestOrderCostWhenNotFound() {
        when(tagRepository.findMostWidelyUsedTagOfUserWithHighestOrderCost()).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.findMostWidelyUsedTagOfUserWithHighestOrderCost());
    }
}