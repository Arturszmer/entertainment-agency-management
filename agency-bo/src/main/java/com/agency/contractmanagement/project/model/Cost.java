package com.agency.contractmanagement.project.model;

import com.agency.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Cost extends BaseEntity<Long> {

    @Column(name = "cost_type", nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private String costType;

    @Column(name = "cost_reference")
    @Setter(AccessLevel.PROTECTED)
    private String costReference;

    @Column(name = "description")
    @Setter(AccessLevel.PROTECTED)
    private String description;

    @Column(name = "value")
    @Setter(AccessLevel.PROTECTED)
    private BigDecimal value;

    @Column(name = "public_id")
    private UUID publicId;
}
