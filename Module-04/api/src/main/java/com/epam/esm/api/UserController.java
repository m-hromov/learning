package com.epam.esm.api;

import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.dto.UserRegisterRequestDto;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.security.model.SecurityToken;
import com.epam.esm.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping
    @SecurityRequirement(name = "Bearer authentication")
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
                                methodOn(OrderController.class).findAllUserOrders(user.getId(), 1, 10)
                        ).withRel("findAllUserOrders")
                )
        );
        Link selfLink = linkTo(
                methodOn(UserController.class).findAll(page, size)
        ).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(response, selfLink));
    }

    @PostMapping("signup")
    public ResponseEntity<SecurityToken> signup(@RequestBody UserRegisterRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signup(requestDto));
    }

    @PostMapping("signin")
    public ResponseEntity<SecurityToken> signin(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.signin(requestDto));
    }

    @PostMapping("signout")
    @SecurityRequirement(name = "Bearer authentication")
    public ResponseEntity<Void> signout(@AuthenticationPrincipal Object principal) {
        userService.signout(principal);
        return ResponseEntity.ok().build();
    }
}
