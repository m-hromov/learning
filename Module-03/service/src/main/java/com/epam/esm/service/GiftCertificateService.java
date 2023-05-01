package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.List;

public interface GiftCertificateService {
    GiftCertificate save(GiftCertificate certificate);

    GiftCertificateDto findById(Long id);

    List<GiftCertificateDto> getGiftCertificates(Boolean ascendingName, Boolean ascendingCreationDate);

    List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending);

    List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending);

    GiftCertificate patchGiftCertificate(GiftCertificate certificate);

    void delete(Long id);

    GiftCertificateDto updateDuration(Long id, Long duration);
}
