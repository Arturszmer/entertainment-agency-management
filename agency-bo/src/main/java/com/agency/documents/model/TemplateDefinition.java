package com.agency.documents.model;

import com.agency.common.BaseEntity;
import com.agency.documents.service.placeholdergenerator.TemplateResource;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "template_definition")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TemplateDefinition extends BaseEntity<Long> {

    @Column(name = "definitionName", unique = true, nullable = false)
    private String definitionName;
    @ElementCollection(targetClass = TemplateResource.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "template_type_resources")
    @Column(name = "resource")
    private Set<TemplateResource> resources;
}
