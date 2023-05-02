package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;

import java.util.List;

public interface GiftCertificateRepository extends CrdRepository<Long, GiftCertificate> {
    List<GiftCertificate> findAllOrderedByName(String order, Pageable paging);

    List<GiftCertificate> findAllOrderedByCreationDate(String order, Pageable paging);
}
