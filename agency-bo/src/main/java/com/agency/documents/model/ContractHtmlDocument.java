package com.agency.documents.model;

import com.agency.documentcontext.doccontext.DocContextType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@DiscriminatorValue("CONTRACT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContractHtmlDocument extends HtmlDocument {

    @Column(name = "contract_public_id")
    private UUID contractPublicId;

    public ContractHtmlDocument(String content, UUID contractPublicId) {
        super(content);
        this.contractPublicId = contractPublicId;
    }

    @Override
    public DocContextType getContextType() {
        return DocContextType.CONTRACT_WORK;
    }
}
