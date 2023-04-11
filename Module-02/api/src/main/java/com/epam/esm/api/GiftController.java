package com.epam.esm.api;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.epam.esm.api.GiftController.GIFT_CERTIFICATES_BASE;

@RestController
@RequestMapping(GIFT_CERTIFICATES_BASE)
@RequiredArgsConstructor
public class GiftController {
    public static final String GIFT_CERTIFICATES_BASE = "/gifts";
    public static final String GET_BY_ID = "{id}";
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper mapper;

    @GetMapping(GET_BY_ID)
    public GiftCertificateDto findById(@PathVariable("id") Long id) {
        return mapper.map(giftCertificateService.findById(id));
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(@RequestParam(required = false) Boolean ascendingName,
                                         @RequestParam(required = false) Boolean ascendingCreationDate) {
        return giftCertificateService.getGiftCertificates(ascendingName, ascendingCreationDate)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @PostMapping
    public void create(@RequestBody GiftCertificate certificate) {
        giftCertificateService.save(certificate);
    }

    @PatchMapping
    public void patch(@RequestBody GiftCertificate certificate) {
        giftCertificateService.patchGiftCertificate(certificate);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        giftCertificateService.delete(id);
    }
}
