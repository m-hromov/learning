package com.epam.esm.api;

import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.epam.esm.api.TagController.TAGS_BASE;

@RestController
@RequestMapping(TAGS_BASE)
@RequiredArgsConstructor
public class TagController {
    public static final String TAGS_BASE = "/tags";
    private final TagService tagService;

    @GetMapping
    public List<Tag> findAll() {
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
