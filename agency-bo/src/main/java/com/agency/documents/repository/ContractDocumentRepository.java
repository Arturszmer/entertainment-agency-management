package com.agency.documents.repository;

import com.agency.documents.model.ContractDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractDocumentRepository extends JpaRepository<ContractDocument, Long> {

    Optional<ContractDocument> findContractorDocumentByContractPublicId(UUID contractorPublicId);
}
