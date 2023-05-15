package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private Long orderId;
    private Long giftCertificateId;
}
