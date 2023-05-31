package com.epam.esm.api;

import com.epam.esm.LearningApplication;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.model.User;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(
        classes = LearningApplication.class
)
class UserControllerITest {
    private final User user1 = User.builder().username("user1").password("pass").build();
    private final User user2 = User.builder().username("user2").password("pass").build();
    private final User user3 = User.builder().username("user3").password("pass").build();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<Arguments> validCases() {
        return Stream.of(
                Arguments.of("1", "1", 1),
                Arguments.of("1", "2", 2),
                Arguments.of("1", "3", 3),
                Arguments.of("1", "4", 3),
                Arguments.of("2", "4", 0)
        );
    }

    @BeforeEach
    void setUp() {
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @ParameterizedTest
    @MethodSource("validCases")
    void testFindAll(String page, String size, int expectedRespSize) throws Exception {
        if (expectedRespSize == 0) {
            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                            .param("page", page)
                            .param("size", size))
                    .andExpect(status().isOk()).andExpect(jsonPath("$._embedded").doesNotExist());
        } else {
            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                            .param("page", page)
                            .param("size", size))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$._embedded.userLoginResponseDtoList.*", Matchers.hasSize(expectedRespSize)));
        }
    }

    @Test
    void testFindAllWhenNoTags() throws Exception {
        userRepository.delete(user1.getId());
        userRepository.delete(user2.getId());
        userRepository.delete(user3.getId());

        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").doesNotExist());
    }

    @Test
    void testRegister() throws Exception {
        userRepository.delete(user1.getId());
        userRepository.delete(user2.getId());
        userRepository.delete(user3.getId());
        UserRegisterRequestDto userRegisterRequestDto = UserRegisterRequestDto.builder()
                .username("usssername")
                .password("passs")
                .build();

        MvcResult mvcResult = mvc.perform(post("/users").accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        UserLoginResponseDto response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );

        assertThat(response.getId()).isNotNull();
        assertThat(response.getUsername()).isEqualTo(userRegisterRequestDto.getUsername());
    }

    @ParameterizedTest(name = "{index} => page: {0}, size: {1}")
    @CsvSource(delimiterString = "&&", textBlock = """
             1  &&  0
             0  &&  1
             0  &&  0
             0  && -1
            -1  && -1
            """)
    void testFindAllWhenNoUsers(String page, String size) throws Exception {
        userRepository.delete(user1.getId());
        userRepository.delete(user2.getId());
        userRepository.delete(user3.getId());

        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                        .param("page", page)
                        .param("size", size))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    void testDelete() throws Exception {
        mvc.perform(delete("/users").accept(MediaType.APPLICATION_JSON)
                        .param("id", user1.getId().toString()))
                .andExpect(status().isOk());
        mvc.perform(delete("/users").accept(MediaType.APPLICATION_JSON)
                        .param("id", user2.getId().toString()))
                .andExpect(status().isOk());
        mvc.perform(delete("/users").accept(MediaType.APPLICATION_JSON)
                        .param("id", user3.getId().toString()))
                .andExpect(status().isOk());

        assertTrue(userRepository.findAll(Pageable.builder().page(1).size(10).build()).isEmpty());
    }
}