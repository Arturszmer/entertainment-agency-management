package com.agency.documents.model;

import com.agency.common.BaseEntity;
import com.agency.documentcontext.doccontext.DocContextType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type", discriminatorType = DiscriminatorType.STRING)
@Getter
public abstract class Document extends BaseEntity<Long> {

    @Column(name = "file_name")
    @Setter
    private String fileName;

    @Column(name = "reference_id")
    private UUID referenceId;

    public Document(String fileName) {
        this.fileName = fileName;
        this.referenceId = UUID.randomUUID();
    }

    protected Document() {
        this.referenceId = UUID.randomUUID();
    }

    protected Document(String fileName, UUID referenceId) {
        this.fileName = fileName;
        this.referenceId = referenceId;
    }

    abstract public DocContextType getContextType();
}
