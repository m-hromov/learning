package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BusinessException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.PatchUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @InjectMocks
    private GiftCertificateServiceImpl certificateService;
    @Mock
    private GiftCertificateRepository certificateRepository;
    @Spy
    private GiftCertificateMapper mapper = Mappers.getMapper(GiftCertificateMapper.class);
    @Spy
    private PatchUtil patchUtil;

    @Test
    void testSave() {
        List<Tag> tags = buildTags2();
        GiftCertificate giftCertificate = buildGiftCertificate(tags);
        giftCertificate.setCreateDate(null);
        giftCertificate.setLastUpdateDate(null);

        when(certificateRepository.save(giftCertificate)).thenAnswer(AdditionalAnswers.returnsFirstArg());

        GiftCertificate result = certificateService.save(giftCertificate);

        assertNotNull(result);
    }

    @Test
    void testFindById() {
        GiftCertificate giftCertificate = buildGiftCertificate(buildTags1());

        when(certificateRepository.findById(giftCertificate.getId()))
                .thenReturn(Optional.of(giftCertificate));

        GiftCertificateDto result = certificateService.findById(giftCertificate.getId());

        assertNotNull(giftCertificate);
    }

    @Test
    void testFindByIdThrows() {
        when(certificateRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> certificateService.findById(1L));
    }

    @Test
    void testGetGiftCertificates() {
        GiftCertificate giftCertificate = buildGiftCertificate(Collections.emptyList());
        List<Tag> tags = buildTags2();

        when(certificateRepository.findAll(Pageable.builder().build())).thenReturn(List.of(giftCertificate));

        List<GiftCertificateDto> certificates = certificateService.getGiftCertificates(
                null,
                null,
                Pageable.builder().build()
        );

        assertFalse(certificates.isEmpty());
    }

    @Test
    void testGetGiftCertificatesByCreationDateAscending() {
        certificateService.getGiftCertificatesSortedByCreationDate(true, Pageable.builder().build());

        verify(certificateRepository).findAllOrderedByCreationDate(eq("ASC"), any());
    }

    @Test
    void testGetGiftCertificatesByCreationDateDescending() {
        certificateService.getGiftCertificatesSortedByCreationDate(false, Pageable.builder().build());

        verify(certificateRepository).findAllOrderedByCreationDate(eq("DESC"), any());
    }

    @Test
    void testGetGiftCertificatesByNameAscending() {
        certificateService.getGiftCertificatesSortedByName(true, Pageable.builder().build());

        verify(certificateRepository).findAllOrderedByName(eq("ASC"), any());
    }

    @Test
    void testGetGiftCertificatesByNameDescending() {
        certificateService.getGiftCertificatesSortedByName(false, Pageable.builder().build());

        verify(certificateRepository).findAllOrderedByName(eq("DESC"), any());
    }

    @Test
    void testPatchGiftCertificate() throws InvocationTargetException, IllegalAccessException {
        GiftCertificate giftCertificate = buildGiftCertificate(buildTags1());
        String expectedDescription = giftCertificate.getDescription();
        LocalDateTime updatedDate = giftCertificate.getLastUpdateDate();
        GiftCertificate updatedGiftCertificate = buildGiftCertificate(buildTags1());
        updatedGiftCertificate.setName("updatedName123123");
        updatedGiftCertificate.setDescription(null);

        when(certificateRepository.findById(updatedGiftCertificate.getId()))
                .thenReturn(Optional.of(giftCertificate));
        when(certificateRepository.save(giftCertificate))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        GiftCertificate result = certificateService.patchGiftCertificate(updatedGiftCertificate);

        verify(patchUtil).copyProperties(giftCertificate, updatedGiftCertificate);
        verify(certificateRepository).delete(giftCertificate.getId());
        assertEquals(updatedGiftCertificate.getName(), result.getName());
        assertNotNull(result.getDescription());
        assertEquals(expectedDescription, result.getDescription());
    }

    @Test
    void testPatchGiftCertificateThrows() {
        GiftCertificate giftCertificate = GiftCertificate.builder().build();

        when(certificateRepository.findById(any()))
                .thenReturn(Optional.of(buildGiftCertificate(Collections.emptyList())));
        when(certificateRepository.save(any()))
                .thenThrow(RuntimeException.class);

        assertThrows(BusinessException.class,
                () -> certificateService.patchGiftCertificate(giftCertificate));
    }

    @Test
    void testDelete() {
        Long id = 100L;

        certificateService.delete(id);

        verify(certificateRepository).delete(id);
    }

    private List<Tag> buildTags1() {
        return List.of(Tag.builder().name("name").build());
    }

    private List<Tag> buildTags2() {
        return List.of(Tag.builder().id(10000L).name("name2").build(),
                Tag.builder().id(20000L).name("name3").build());
    }

    private GiftCertificate buildGiftCertificate(List<Tag> tags) {
        return GiftCertificate.builder()
                .id(100L)
                .name("Cert")
                .description("Cert descr")
                .duration(1000L)
                .tags(tags)
                .lastUpdateDate(LocalDateTime.now())
                .build();
    }
}