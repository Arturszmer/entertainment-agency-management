package com.agency.documents.service.placeholdergenerator;

import com.agency.documentcontext.templatecontext.TemplateContext;
import com.agency.documents.model.PlaceholderModel;
import com.agency.dto.document.TemplateSystemPlaceholdersDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.agency.documents.model.PlaceholderModel.contractWorkPlaceholders;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceholderGeneratorServiceTest {

    private PlaceholderGeneratorService placeholderGeneratorService;

    @BeforeEach
    void setUp() {
        placeholderGeneratorService = new PlaceholderGeneratorService();
    }

    @Test
    void should_generate_contract_work_context_placeholders() {
        //given
        List<TemplateSystemPlaceholdersDto> contractWorkContextPlaceholders = placeholderGeneratorService.getPlaceholdersByContextType(TemplateContext.CONTRACT_WORK);

        contractWorkContextPlaceholders.forEach(System.out::println);
        TemplateSystemPlaceholdersDto contractWork = contractWorkContextPlaceholders.get(0);
        assertEquals("ContractWork", contractWork.groupName());
        assertEquals(contractWorkPlaceholders, contractWork.variables());

        TemplateSystemPlaceholdersDto contractor = contractWorkContextPlaceholders.get(1);
        assertEquals("Contractor", contractor.groupName());
        assertEquals(PlaceholderModel.contractorPlaceholders, contractor.variables());

        TemplateSystemPlaceholdersDto agencyDetails = contractWorkContextPlaceholders.get(2);
        assertEquals("AgencyDetails", agencyDetails.groupName());
        assertEquals(PlaceholderModel.agencyDetailsPlaceholders, agencyDetails.variables());
    }


}
