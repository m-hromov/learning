package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface GiftCertificateService {
    GiftCertificate save(GiftCertificate certificate);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> getGiftCertificates(Boolean ascendingName, Boolean ascendingCreationDate, Pageable paging);

    List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending, Pageable paging);

    List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending, Pageable paging);

    GiftCertificate patchGiftCertificate(GiftCertificate certificate);

    void delete(Long id);

    GiftCertificateDto updateDuration(Long id, Long duration);
}
