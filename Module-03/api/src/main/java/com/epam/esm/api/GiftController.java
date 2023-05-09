package com.epam.esm.api;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.GiftCertificateService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping("{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(@RequestParam(required = false) Boolean ascendingName,
                                            @RequestParam(required = false) Boolean ascendingCreationDate,
                                            @RequestParam(defaultValue = "1") @Min(1) int page,
                                            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return giftCertificateService.getGiftCertificates(
                ascendingName,
                ascendingCreationDate,
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
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

    @PatchMapping("/{id}/duration")
    public GiftCertificateDto updateDuration(@PathVariable Long id, @RequestParam Long duration) {
        return giftCertificateService.updateDuration(id, duration);
    }
}
