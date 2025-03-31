package com.agency.documents.assembler;

import com.agency.dto.document.TemplateSystemPlaceholdersDto;

import java.util.Set;

public class TemplatePlaceholdersAssembler {

    public static TemplateSystemPlaceholdersDto toDto(String groupName, Set<String> placeholders) {
        return new TemplateSystemPlaceholdersDto(groupName, placeholders);
    }
}
