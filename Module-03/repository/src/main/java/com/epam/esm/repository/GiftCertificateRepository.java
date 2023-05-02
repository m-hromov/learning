package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends CrdRepository<Long, GiftCertificate> {
    GiftCertificate save(GiftCertificate certificate);

    void saveCertificateTag(Long certificateId, Long tagId);

    Optional<GiftCertificate> findById(Long id);

    List<GiftCertificate> findAll();

    List<GiftCertificate> findAllOrderedByName(String order);

    List<GiftCertificate> findAllOrderedByCreationDate(String order);

    void delete(Long id);
}
