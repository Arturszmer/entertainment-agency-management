package com.agency.documents.service.placeholdergenerator;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.TemplateDefinition;

class PlaceholderGeneratorFactory {

    static PlaceholderGenerationStrategy getPlaceholderGenerationStrategy(TemplateContext context) {
        switch (context){
            case CONTRACT_WORK -> {
                return new ContractWorkPlaceholderGenerator();
            }
            case CONTRACT_WORK_BILL -> {
                return null;
            }
            default -> throw new IllegalArgumentException("Unsupported placeholder generation strategy");
        }
    }

    static PlaceholderGenerationStrategy getCustomPlaceholderGenerationStrategy(TemplateDefinition templateDefinition){
        return new CustomPlaceholderGenerator(templateDefinition);
    }
}
