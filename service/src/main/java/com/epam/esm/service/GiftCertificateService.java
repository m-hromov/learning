package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface GiftCertificateService {
    GiftCertificate save(GiftCertificate certificate);

    GiftCertificate findById(Long id);

    List<GiftCertificate> getGiftCertificates();

    List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending);

    List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending);

    GiftCertificate patchGiftCertificate(GiftCertificate certificate);

    void delete(Long id);
}
