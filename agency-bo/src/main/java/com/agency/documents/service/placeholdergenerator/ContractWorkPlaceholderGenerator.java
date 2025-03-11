package com.agency.documents.service.placeholdergenerator;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.utils.PlaceholderGenerator;
import com.agency.contractor.model.Contractor;
import com.agency.documents.assembler.TemplatePlaceholdersAssembler;
import com.agency.dto.document.TemplateSystemPlaceholdersDto;

import java.util.ArrayList;
import java.util.List;

class ContractWorkPlaceholderGenerator implements PlaceholderGenerationStrategy {

    @Override
    public List<TemplateSystemPlaceholdersDto> generatePlaceholders() {
        List<TemplateSystemPlaceholdersDto> placeholders = new ArrayList<>();
        placeholders.add(TemplatePlaceholdersAssembler.toDto(ContractWork.class.getSimpleName(),
                PlaceholderGenerator.generatePlaceholders(ContractWork.class)));
        placeholders.add(TemplatePlaceholdersAssembler.toDto(Contractor.class.getSimpleName(),
                PlaceholderGenerator.generatePlaceholders(Contractor.class)));
        placeholders.add(TemplatePlaceholdersAssembler.toDto(AgencyDetails.class.getSimpleName(),
                PlaceholderGenerator.generatePlaceholders(AgencyDetails.class)));
        return placeholders;
    }
}
