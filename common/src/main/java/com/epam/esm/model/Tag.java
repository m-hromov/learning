package com.epam.esm.model;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Long id;
    private String name;
}
