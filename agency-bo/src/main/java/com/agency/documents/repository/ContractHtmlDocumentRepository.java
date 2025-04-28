package com.agency.documents.repository;

import com.agency.documents.model.ContractHtmlDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractHtmlDocumentRepository extends JpaRepository<ContractHtmlDocument, Long> {

    Optional<ContractHtmlDocument> findByContractPublicId(UUID contractPublicId);
}
