package com.agency.documents.assembler;

import com.agency.dto.document.TemplateSystemPlaceholdersDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplatePlaceholdersAssembler {

    public static TemplateSystemPlaceholdersDto toDto(String groupName, Map<String, Integer> placeholders) {
        List<String> orderedVariables = placeholders.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return new TemplateSystemPlaceholdersDto(groupName, placeholders, orderedVariables);
    }
}
