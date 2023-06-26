package com.epam.esm.api;

import com.epam.esm.dto.CreateOrderRequestDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.OrderInfoDto;
import com.epam.esm.model.paging.Pageable;
import com.epam.esm.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    @SecurityRequirement(name = "Bearer authentication")
    public ResponseEntity<CollectionModel<OrderDto>> findAllUserOrders(@PathVariable Long userId,
                                                                       @RequestParam(defaultValue = "1") @Min(1) int page,
                                                                       @RequestParam(defaultValue = "10") @Min(1) int size) {
        List<OrderDto> response = orderService.findUserOrders(
                userId,
                Pageable.builder()
                        .page(page)
                        .size(size)
                        .build()
        );
        response.forEach(orderDto ->
                orderDto.add(
                        linkTo(
                                methodOn(OrderController.class).findOrderInfo(orderDto.getOrderId())
                        ).withSelfRel()
                )
        );
        Link selfLink = linkTo(
                methodOn(OrderController.class).findAllUserOrders(userId, page, size)
        ).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(response, selfLink));
    }

    @GetMapping("/users/orders/{orderId}")
    @SecurityRequirement(name = "Bearer authentication")
    public ResponseEntity<OrderInfoDto> findOrderInfo(@PathVariable Long orderId) {
        OrderInfoDto orderInfoDto = orderService.findOrderInfo(orderId);
        orderInfoDto.add(
                linkTo(
                        methodOn(OrderController.class).findOrderInfo(orderId)
                ).withSelfRel()
        );
        return ResponseEntity.ok(orderInfoDto);
    }

    @PostMapping("/users/orders")
    @PreAuthorize("hasAnyAuthority(T(com.epam.esm.Authority).USER.name(), T(com.epam.esm.Authority).ADMIN.name())")
    @SecurityRequirement(name = "Bearer authentication")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid CreateOrderRequestDto requestDto) {
        OrderDto orderDto = orderService.createOrder(requestDto);
        orderDto.add(
                linkTo(
                        methodOn(OrderController.class).findOrderInfo(orderDto.getOrderId())
                ).withRel("findOrderInfo")
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDto);
    }
}
