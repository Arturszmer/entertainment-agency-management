package com.agency.project.model;

import com.agency.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Cost extends BaseEntity<Long> {

    @Column(name = "cost_type", nullable = false)
    private String costType;

    @Column(name = "cost_reference")
    private String costReference;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private BigDecimal value;
}
