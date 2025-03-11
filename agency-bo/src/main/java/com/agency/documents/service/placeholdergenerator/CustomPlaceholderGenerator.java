package com.agency.documents.service.placeholdergenerator;

import com.agency.contractmanagement.utils.PlaceholderGenerator;
import com.agency.documents.assembler.TemplatePlaceholdersAssembler;
import com.agency.documents.model.TemplateDefinition;
import com.agency.dto.document.TemplateSystemPlaceholdersDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class CustomPlaceholderGenerator implements PlaceholderGenerationStrategy {

    private final TemplateDefinition templateDefinition;

    @Override
    public List<TemplateSystemPlaceholdersDto> generatePlaceholders() {
        List<TemplateSystemPlaceholdersDto> placeholders = new ArrayList<>();
        for (TemplateResource resource : templateDefinition.getResources()) {
            Class<?> clazz = resource.getReourceClass();
            placeholders.add(TemplatePlaceholdersAssembler.toDto(clazz.getSimpleName(),
                    PlaceholderGenerator.generatePlaceholders(clazz)));
        }
        return placeholders;
    }
}
