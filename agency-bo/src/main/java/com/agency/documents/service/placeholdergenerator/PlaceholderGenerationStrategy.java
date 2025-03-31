package com.agency.documents.service.placeholdergenerator;

import com.agency.dto.document.TemplateSystemPlaceholdersDto;

import java.util.List;

public interface PlaceholderGenerationStrategy {

    List<TemplateSystemPlaceholdersDto> generatePlaceholders();
}
