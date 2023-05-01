package com.epam.esm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GIFT_CERTIFICATE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @Column
    private Long duration;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(name = "GIFT_CERTIFICATE_TAG",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tag> tags;
}
