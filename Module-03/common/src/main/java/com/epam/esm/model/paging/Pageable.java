package com.epam.esm.model.paging;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Pageable {
    @Min(1)
    private int page;
    @Min(1)
    private int size;
}
