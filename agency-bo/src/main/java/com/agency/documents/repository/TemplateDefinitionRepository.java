package com.agency.documents.repository;

import com.agency.documents.model.TemplateDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateDefinitionRepository extends JpaRepository<TemplateDefinition, Long> {

    Optional<TemplateDefinition> findByDefinitionName(String name);
}
