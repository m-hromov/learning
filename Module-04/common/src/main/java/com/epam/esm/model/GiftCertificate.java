package com.epam.esm.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "GIFT_CERTIFICATE_TABLE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue
    @Column(name = "gift_certificate_id")
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
    private String photoPath = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTV1F8AwmLQbvjs7GoSCKt-srwOv3vhGNAHGQ&usqp=CAU";

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime lastUpdateDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "GIFT_CERTIFICATE_TAG_TABLE",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Tag> tags;

    @PreUpdate
    private void preUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    @PrePersist
    private void prePersist() {
        if (this.createDate == null) {
            this.createDate = LocalDateTime.now();
        }
    }
}
