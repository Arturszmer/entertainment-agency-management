package com.agency.contractmanagement.utils;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractor.model.Contractor;
import com.agency.documents.model.PlaceholderModel;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceholderGeneratorTest {

    @Test
    public void should_generate_prefix_for_class_type() {

        Map<String, Integer> contractWorkPlaceholders = PlaceholderGenerator.generatePlaceholders(ContractWork.class);
        assertEquals(contractWorkPlaceholders.keySet(), PlaceholderModel.contractWorkPlaceholders);

        Map<String, Integer> contractorPlaceholders = PlaceholderGenerator.generatePlaceholders(Contractor.class);
        assertEquals(contractorPlaceholders.keySet(), PlaceholderModel.contractorPlaceholders);

        Map<String, Integer> projectPlaceholders = PlaceholderGenerator.generatePlaceholders(Project.class);
        assertEquals(projectPlaceholders.keySet(), PlaceholderModel.projectPlaceholders);

        Map<String, Integer> agencyDetailsPlaceholders = PlaceholderGenerator.generatePlaceholders(AgencyDetails.class);
        assertEquals(agencyDetailsPlaceholders.keySet(), PlaceholderModel.agencyDetailsPlaceholders);

    }

}
