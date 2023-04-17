package com.epam.esm.api;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
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
    public List<TagDto> findAll() {
        return tagService.getTags();
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
