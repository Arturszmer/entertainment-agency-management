package com.agency.user.model;

import com.agency.auth.RoleType;
import com.agency.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.agency.user.model.Permission.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleType name;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private List<Permission> permissions = new ArrayList<>();

    public Role(RoleType name) {
        this.name = name;
        switch (name) {
            case ADMIN -> permissions.addAll(List.of(CONTRACT_MANAGEMENT, EVENT_MANAGEMENT, USER_MANAGEMENT));
            case MANAGER -> permissions.addAll(List.of(EVENT_MANAGEMENT, USER_MANAGEMENT));
            case USER -> permissions.add(USER_MANAGEMENT);
        }
    }
}
