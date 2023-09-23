package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificateDto save(GiftCertificate certificate, MultipartFile file);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> getGiftCertificates(Boolean ascendingName, Boolean ascendingCreationDate, Pageable paging, String search);

    List<GiftCertificateDto> getGiftCertificatesWithTags(List<Long> tags, Pageable paging);

    List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending, Pageable paging);

    List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending, Pageable paging);

    GiftCertificateDto patchGiftCertificate(GiftCertificate certificate);

    void delete(Long id);

    GiftCertificateDto updateDuration(Long id, Long duration);

    GiftCertificate findByIdOrThrow(Long id);
}
