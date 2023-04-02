package com.epam.esm.api;

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
public class GiftController {//TODO: Implement a generic BaseController with CRUD operations?
    public static final String GIFT_CERTIFICATES_BASE = "/gifts";
    public static final String GET_BY_ID = "{id}";
    private final GiftCertificateService giftCertificateService;

    @GetMapping(GET_BY_ID)
    public GiftCertificate findById(@PathVariable("id") Long id) {
        return giftCertificateService.findById(id);
    }

    @GetMapping
    public List<GiftCertificate> findAll(@RequestParam(required = false) Boolean ascendingName,
                                         @RequestParam(required = false) Boolean ascendingCreationDate) {
        //I think it would be better to move logic into service
        if (Objects.nonNull(ascendingName)) {
            return giftCertificateService.getGiftCertificatesSortedByName(ascendingName);
        } else if (Objects.nonNull(ascendingCreationDate)) {
            return giftCertificateService.getGiftCertificatesSortedByCreationDate(ascendingCreationDate);
        }
        return giftCertificateService.getGiftCertificates();
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
