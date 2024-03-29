package com.epam.esm.api;

import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<CollectionModel<UserLoginResponseDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                              @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<UserLoginResponseDto> response = userService.findAll(
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
        response.forEach(user ->
                user.add(
                        linkTo(
                                methodOn(OrderController.class).findAllUserOrders(user.getId(), 1 ,10)
                        ).withRel("findAllUserOrders")
                )
        );
        Link selfLink = linkTo(
                methodOn(UserController.class).findAll(page, size)
        ).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(response, selfLink));
    }

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> register(@RequestBody UserRegisterRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.register(requestDto);
        responseDto.add(
                linkTo(methodOn(UserController.class).register(requestDto)).withSelfRel()
        );
        responseDto.add(
                linkTo(
                        methodOn(OrderController.class).findAllUserOrders(
                                responseDto.getId(),
                                1,
                                10
                        )
                ).withSelfRel()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }
}
