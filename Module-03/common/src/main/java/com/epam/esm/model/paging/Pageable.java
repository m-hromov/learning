package com.epam.esm.model.paging;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Pageable {
    @Min(1)
    private int page;
    @Min(1)
    private int size;
}
