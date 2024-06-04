package com.agency.user.model;

import com.agency.auth.RoleType;
import com.agency.common.BaseEntity;
import com.agency.dict.userProfile.Permission;
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

import static com.agency.dict.userProfile.Permission.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(name = "organizerName")
    private RoleType name;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private List<Permission> permissions = new ArrayList<>();

    public Role(RoleType name) {
        this.name = name;
        switch (name) {
            case ADMIN -> permissions.addAll(List.of(Permission.values()));
            case MANAGER -> permissions.addAll(List.of(EVENT_MANAGEMENT,
                    CONTRACTORS_VIEW, PROJECT_MANAGEMENT, PROJECT_VIEW, ORGANIZER_MANAGEMENT, ORGANIZER_VIEW));
            case USER -> permissions.add(ORGANIZER_VIEW);
        }
    }
}
