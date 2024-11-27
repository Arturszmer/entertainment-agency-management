package com.agency.documents.model;

import com.agency.common.BaseEntity;
import com.agency.documentcontext.doccontext.DocContextType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
abstract class Document extends BaseEntity<Long> {

    @Column(name = "file_name")
    @Setter
    private String fileName;

    @Column(name = "reference_id")
    private UUID referenceId;

    public Document(String fileName) {
        this.fileName = fileName;
        this.referenceId = UUID.randomUUID();
    }

    protected Document(String fileName, UUID referenceId) {
        this.fileName = fileName;
        this.referenceId = referenceId;
    }

    abstract public DocContextType getContextType();
}
