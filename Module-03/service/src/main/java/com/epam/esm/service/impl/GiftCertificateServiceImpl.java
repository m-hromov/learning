package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BusinessException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
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
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;
    private final PatchUtil patchUtil;
    private final GiftCertificateMapper mapper;

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
    public GiftCertificateDto findById(Long id) {
        return mapper.map(findByIdOrThrow(id));
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificates(Boolean ascendingName, Boolean ascendingCreationDate) {
        List<GiftCertificate> certificates;
        if (Objects.nonNull(ascendingName)) {
            certificates = getGiftCertificatesSortedByName(ascendingName);
        } else if (Objects.nonNull(ascendingCreationDate)) {
            certificates = getGiftCertificatesSortedByCreationDate(ascendingCreationDate);
        } else {
            certificates = giftCertificateRepository.findAll();
        }
        certificates.forEach(certificate -> certificate.setTags(
                tagService.findAllByCertificateId(certificate.getId()))
        );
        return certificates.stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending) {
        return giftCertificateRepository.findAllOrderedByCreationDate(ascending ? "ASC" : "DESC");
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending) {
        return giftCertificateRepository.findAllOrderedByName(ascending ? "ASC" : "DESC");
    }

    @Override
    public GiftCertificate patchGiftCertificate(GiftCertificate certificate) {
        try {
            GiftCertificate certificateToBeUpdated = findByIdOrThrow(certificate.getId());
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

    @Override
    public GiftCertificateDto updateDuration(Long id, Long duration) {
        GiftCertificate giftCertificate = patchGiftCertificate(
                GiftCertificate.builder()
                        .id(id)
                        .duration(duration)
                        .build()
        );
        return mapper.map(giftCertificate);
    }

    private GiftCertificate findByIdOrThrow(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Certificate was not found."));
    }
}
