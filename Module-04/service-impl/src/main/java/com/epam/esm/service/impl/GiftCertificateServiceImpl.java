package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.BusinessException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.PatchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final PatchUtil patchUtil;
    private final GiftCertificateMapper mapper;

    @Override
    public GiftCertificateDto save(GiftCertificate certificate, MultipartFile file) {
        certificate.setPhotoPath(saveFIle(file));
        return mapper.map(giftCertificateRepository.save(certificate));
    }

    private String saveFIle(MultipartFile file) {
        try {
            Path dir = Paths.get("image-storage");
            Files.createDirectories(dir);
            Files.copy(file.getInputStream(), dir.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "http://localhost:8080/image-storage/" + file.getOriginalFilename();
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return mapper.map(findByIdOrThrow(id));
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificates(Boolean ascendingName,
                                                        Boolean ascendingCreationDate,
                                                        Pageable paging, String search) {
        List<GiftCertificate> certificates;
//        if (Objects.nonNull(ascendingName)) {
//            certificates = getGiftCertificatesSortedByName(ascendingName, paging);
//        } else if (Objects.nonNull(ascendingCreationDate)) {
//            certificates = getGiftCertificatesSortedByCreationDate(ascendingCreationDate, paging);
//        } else {
//        }
        certificates = giftCertificateRepository.findAll(paging, search);
        return certificates.stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificatesWithTags(List<Long> tags, Pageable paging) {
        return giftCertificateRepository.findAllWithTags(tags, paging)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByCreationDate(boolean ascending, Pageable paging) {
        return giftCertificateRepository.findAllOrderedByCreationDate(ascending ? "ASC" : "DESC", paging);
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesSortedByName(boolean ascending, Pageable paging) {
        return giftCertificateRepository.findAllOrderedByName(ascending ? "ASC" : "DESC", paging);
    }

    @Override
    public GiftCertificateDto patchGiftCertificate(GiftCertificate certificate) {
        try {
            GiftCertificate certificateToBeUpdated = findByIdOrThrow(certificate.getId());
            patchUtil.copyProperties(certificateToBeUpdated, certificate);
            giftCertificateRepository.delete(certificate.getId());
            return mapper.map(giftCertificateRepository.save(certificateToBeUpdated));
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
        GiftCertificateDto giftCertificate = patchGiftCertificate(
                GiftCertificate.builder()
                        .id(id)
                        .duration(duration)
                        .build()
        );
        return giftCertificate;
    }

    @Override
    public GiftCertificate findByIdOrThrow(Long id) {
        return giftCertificateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Certificate was not found."));
    }
}
