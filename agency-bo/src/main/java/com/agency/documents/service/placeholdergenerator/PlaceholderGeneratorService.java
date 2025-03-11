package com.agency.documents.service.placeholdergenerator;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.dto.document.TemplateSystemPlaceholdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceholderGeneratorService {

    public List<TemplateSystemPlaceholdersDto> getPlaceholdersByContextType(TemplateContext templateContext) {
        PlaceholderGenerationStrategy placeholderGenerationStrategy = PlaceholderGeneratorFactory.getPlaceholderGenerationStrategy(templateContext);
        assert placeholderGenerationStrategy != null;
        return placeholderGenerationStrategy.generatePlaceholders();
    }
}
