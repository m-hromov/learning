package com.epam.esm.api;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.dto.UserLoginRequestDto;
import com.epam.esm.dto.UserLoginResponseDto;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<UserLoginResponseDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) int page,
                                                              @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<UserLoginResponseDto> response = userService.findAll(
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderDto>> findAllUserOrders(@PathVariable Long userId,
                                                            @RequestParam(defaultValue = "1") @Min(1) int page,
                                                            @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<OrderDto> response = orderService.findUserOrders(
                userId,
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderInfoDto> findOrderInfo(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderInfo(orderId));
    }

    @PostMapping
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.login(requestDto));
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        userService.delete(id);
    }
}
