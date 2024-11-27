package com.agency.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import com.agency.user.helper.SecurityContextUsers;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
@Getter
public abstract class EntityLog {

    @Column(name = "CREATED_BY", updatable = false)
    @CreatedBy
    @ExcludeFromPlaceholders
    protected String createdBy;

    @Column(name = "LAST_MODIFIED")
    @LastModifiedBy
    @ExcludeFromPlaceholders
    protected String modifiedBy;

    @Column(name = "CREATION_TIMESTAMP", updatable = false)
    @CreatedDate
    @ExcludeFromPlaceholders
    protected Instant creationTimestamp;

    @Column(name = "MODIFICATION_TIMESTAMP")
    @LastModifiedDate
    @ExcludeFromPlaceholders
    protected Instant modificationTimestamp;

    @PrePersist
    public void prePersist(){
        this.createdBy = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        this.modifiedBy = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        this.creationTimestamp = Instant.now();
        this.modificationTimestamp = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.modifiedBy = SecurityContextUsers.getUsernameFromAuthenticatedUser();
        this.modificationTimestamp = Instant.now();
    }

}
