package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {
    GiftCertificate save(GiftCertificate certificate);

    void saveCertificateTag(Long certificateId, Long tagId);

    Optional<GiftCertificate> findById(Long id);

    List<GiftCertificate> findAll();

    void delete(Long id);
}
