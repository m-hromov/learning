package com.epam.esm.service.impl;

import com.epam.esm.exception.BusinessException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.PatchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;
    private final PatchUtil patchUtil;

    @Override
    public GiftCertificate save(GiftCertificate certificate) {
        List<Tag> tags = certificate.getTags()
                .stream()
                .map(tagService::saveIfNotExists)
                .toList();
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        GiftCertificate savedCertificate = giftCertificateRepository.save(certificate);
        tags.forEach(tag -> giftCertificateRepository.saveCertificateTag(
                savedCertificate.getId(), tag.getId()));
        savedCertificate.setTags(tags);
        return savedCertificate;
    }

    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Certificate was not found."));
    }

    @Override
    public List<GiftCertificate> getGiftCertificates() {
        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
        certificates.forEach(certificate -> certificate.setTags(
                tagService.findAllByCertificateId(certificate.getId()))
        );
        return certificates;
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending) {
        List<GiftCertificate> certificates = getGiftCertificates();
        if (ascending) {
            certificates.sort(Comparator.comparing(GiftCertificate::getCreateDate));
        } else {
            certificates.sort(Comparator.comparing(GiftCertificate::getCreateDate).reversed());
        }
        return certificates;
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending) {
        List<GiftCertificate> certificates = getGiftCertificates();
        if (ascending) {
            certificates.sort(Comparator.comparing(GiftCertificate::getName));
        } else {
            certificates.sort(Comparator.comparing(GiftCertificate::getName).reversed());
        }
        return certificates;
    }

    @Override
    public GiftCertificate patchGiftCertificate(GiftCertificate certificate) {
        try {
            GiftCertificate certificateToBeUpdated = findById(certificate.getId());
            patchUtil.copyProperties(certificateToBeUpdated, certificate);
            giftCertificateRepository.delete(certificate.getId());
            certificateToBeUpdated.setLastUpdateDate(LocalDateTime.now());
            return giftCertificateRepository.save(certificateToBeUpdated);
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating fields.");
        }
    }

    @Override
    public void delete(Long id) {
        giftCertificateRepository.delete(id);
    }
}
