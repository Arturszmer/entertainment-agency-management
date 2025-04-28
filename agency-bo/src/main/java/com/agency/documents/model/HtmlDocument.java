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
@Table(name = "html_document")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class HtmlDocument extends BaseEntity<Long> {

    @Column(name = "content", columnDefinition = "TEXT")
    @Setter
    private String content;

    @Column(name = "reference_id")
    private UUID referenceId;

    public HtmlDocument(String content) {
        this.content = content;
        this.referenceId = UUID.randomUUID();
    }

    abstract public DocContextType getContextType();

}
