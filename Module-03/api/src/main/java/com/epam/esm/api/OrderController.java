package com.epam.esm.api;

import com.epam.esm.dto.CreateOrderRequestDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/users/{userId}/orders")
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

    @GetMapping("/users/orders/{orderId}")
    public ResponseEntity<OrderInfoDto> findOrderInfo(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrderInfo(orderId));
    }

    @PostMapping("/users/orders")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(requestDto));
    }
}
