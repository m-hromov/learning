package com.epam.esm.api;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.GiftCertificateService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping("{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return giftCertificateService.findById(id).add(
                linkTo(
                        methodOn(GiftController.class).findById(id)
                ).withSelfRel()
        );
    }

    @GetMapping
    public CollectionModel<GiftCertificateDto> findAll(@RequestParam(required = false) Boolean ascendingName,
                                                       @RequestParam(required = false) Boolean ascendingCreationDate,
                                                       @RequestParam(defaultValue = "1") @Min(1) int page,
                                                       @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<GiftCertificateDto> list = giftCertificateService.getGiftCertificates(
                ascendingName,
                ascendingCreationDate,
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );

        list.forEach(gift -> {
            gift.add(
                    linkTo(
                            methodOn(GiftController.class).findById(gift.getId())
                    ).withSelfRel()
            );
            List<Long> tagIds = gift.getTags().stream()
                    .map(TagDto::getId)
                    .toList();
            gift.add(
                    linkTo(
                            methodOn(GiftController.class).findAllWithTags(tagIds, 1, 10)
                    ).withRel("findAllWithTags")
            );
        });
        Link self = linkTo(
                methodOn(GiftController.class).findAll(ascendingName, ascendingCreationDate, page, size)
        ).withSelfRel();
        return CollectionModel.of(list, self);
    }

    @GetMapping("/by-tags")
    public CollectionModel<GiftCertificateDto> findAllWithTags(@RequestParam @NotEmpty List<@NotBlank Long> tags,
                                                               @RequestParam(defaultValue = "1") @Min(1) int page,
                                                               @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<GiftCertificateDto> list = giftCertificateService.getGiftCertificatesWithTags(
                tags,
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );

        list.forEach(gift ->
                gift.add(
                        linkTo(
                                methodOn(GiftController.class).findById(gift.getId())
                        ).withSelfRel()
                )
        );
        Link self = linkTo(
                methodOn(GiftController.class).findAllWithTags(tags, page, size)
        ).withSelfRel();
        return CollectionModel.of(list, self);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> create(@RequestBody GiftCertificate certificate) {
        GiftCertificateDto response = giftCertificateService.save(certificate);
        response.add(
                linkTo(
                        methodOn(GiftController.class).create(certificate)
                ).withSelfRel(),
                linkTo(
                        methodOn(GiftController.class).patch(certificate)
                ).withRel("patch"),
                linkTo(
                        methodOn(GiftController.class).findById(response.getId())
                ).withRel("findById")
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<GiftCertificateDto> patch(@RequestBody GiftCertificate certificate) {
        GiftCertificateDto response = giftCertificateService.patchGiftCertificate(certificate);
        response.add(
                linkTo(
                        methodOn(GiftController.class).patch(certificate)
                ).withSelfRel(),
                linkTo(
                        methodOn(GiftController.class).findById(response.getId())
                ).withRel("findById")
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
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
