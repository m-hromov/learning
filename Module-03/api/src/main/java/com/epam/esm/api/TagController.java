package com.epam.esm.api;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.TagService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Validated
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<CollectionModel<TagDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                           @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<TagDto> response = tagService.getTags(
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
        return ResponseEntity.ok(CollectionModel.of(response,
                linkTo(
                        methodOn(TagController.class).findAll(page, size)
                ).withSelfRel(),
                linkTo(
                        methodOn(TagController.class).findMostWidelyUsedTagForUser()
                ).withRel("findMostWidelyUsedTagForUser")));
    }

    @GetMapping("/most-widely-used")
    public ResponseEntity<TagDto> findMostWidelyUsedTagForUser() {
        TagDto responseDto = tagService.findMostWidelyUsedTagOfUserWithHighestOrderCost();
        responseDto.add(
                linkTo(
                        methodOn(TagController.class).findMostWidelyUsedTagForUser()
                ).withSelfRel()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TagDto> create(@RequestBody Tag tag) {
        TagDto responseDto = tagService.save(tag);
        responseDto.add(
                linkTo(
                        methodOn(TagController.class).create(
                                tag
                        )
                ).withSelfRel(),
                linkTo(
                        methodOn(TagController.class).findMostWidelyUsedTagForUser()
                ).withRel("findMostWidelyUsedTagForUser")
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        tagService.delete(id);
    }
}
