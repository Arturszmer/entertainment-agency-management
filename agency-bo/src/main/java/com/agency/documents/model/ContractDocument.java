package com.agency.documents.model;

import com.agency.documentcontext.doccontext.DocContextType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@DiscriminatorValue("CONTRACT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ContractDocument extends Document {

    @Column(name = "contract_public_id")
    private UUID contractPublicId;
    @Setter
    @Column(name = "is_error_file")
    private boolean isErrorFile;

    public ContractDocument(String filename, UUID contractPublicId) {
        super(filename);
        this.contractPublicId = contractPublicId;
    }

    public ContractDocument(String filename, UUID contractPublicId, boolean isErrorFile) {
        super(filename);
        this.contractPublicId = contractPublicId;
        this.isErrorFile = isErrorFile;
    }

    @Override
    public DocContextType getContextType() {
        return DocContextType.CONTRACT_WORK;
    }
}
