package com.epam.esm.api;

import com.epam.esm.LearningApplication;
import com.epam.esm.model.Tag;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(
        classes = LearningApplication.class
)
class TagControllerITest {
    private final Tag tag1 = Tag.builder().name("tag1").build();
    private final Tag tag2 = Tag.builder().name("tag2").build();
    private final Tag tag3 = Tag.builder().name("tag3").build();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
    }

    @ParameterizedTest
    @MethodSource("validCases")
    void testFindAll(String page, String size, int expectedRespSize) throws Exception {
        if (expectedRespSize == 0) {
            mvc.perform(get("/tags").accept(MediaType.APPLICATION_JSON)
                            .param("page", page)
                            .param("size", size))
                    .andExpect(status().isOk())
                    .andExpect(status().isOk()).andExpect(jsonPath("$._embedded").doesNotExist());
        } else {
            mvc.perform(get("/tags").accept(MediaType.APPLICATION_JSON)
                            .param("page", page)
                            .param("size", size))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.tagDtoList.*", Matchers.hasSize(expectedRespSize)));
        }
    }

    private static Stream<Arguments> validCases() {
        return Stream.of(
                Arguments.of("1", "1", 1),
                Arguments.of("1", "2", 2),
                Arguments.of("1", "3", 3),
                Arguments.of("1", "4", 3),
                Arguments.of("2", "4", 0)
        );
    }

    @Test
    void testFindAllWhenNoTags() throws Exception {
        tagRepository.deleteAll();

        mvc.perform(get("/tags").accept(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(status().isOk()).andExpect(jsonPath("$._embedded").doesNotExist());
    }

    //TODO: Maybe better use standalone mockMvc setup for this test
    @ParameterizedTest(name = "{index} => page: {0}, size: {1}")
    @CsvSource(delimiterString = "&&", textBlock = """
             1  &&  0
             0  &&  1
             0  &&  0
             0  && -1
            -1  && -1
            """)
    void testFindAllWhenNoTags(String page, String size) throws Exception {
        tagRepository.deleteAll();

        mvc.perform(get("/tags").accept(MediaType.APPLICATION_JSON)
                        .param("page", page)
                        .param("size", size))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(delete("/tags").accept(MediaType.APPLICATION_JSON)
                        .param("id", tag1.getId().toString()))
                .andExpect(status().isOk());
        mvc.perform(delete("/tags").accept(MediaType.APPLICATION_JSON)
                        .param("id", tag2.getId().toString()))
                .andExpect(status().isOk());
        mvc.perform(delete("/tags").accept(MediaType.APPLICATION_JSON)
                        .param("id", tag3.getId().toString()))
                .andExpect(status().isOk());

        assertTrue(tagRepository.findAll(Pageable.builder().page(1).size(10).build(), ).isEmpty());
    }
}