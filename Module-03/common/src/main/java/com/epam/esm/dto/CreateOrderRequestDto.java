package com.epam.esm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {
    @NotNull
    @Min(0)
    private Long userId;
    @NotNull
    @Min(0)
    private Long giftCertificateId;
}
