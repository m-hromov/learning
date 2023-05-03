package com.epam.esm.api;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.TagService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<TagDto> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                @RequestParam(defaultValue = "10") @Min(1) int size) {
        return tagService.getTags(
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
    }

    @GetMapping("/most-widely-used")
    public TagDto findMostWidelyUsedTagForUser() {
        return tagService.findMostWidelyUsedTagOfUserWithHighestOrderCost();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Tag tag) {
        tagService.save(tag);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        tagService.delete(id);
    }
}
