package com.agency.contractmanagement.utils;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractor.model.Contractor;
import com.agency.documents.model.PlaceholderModel;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlaceholderGeneratorTest {

    @Test
    public void should_generate_prefix_for_class_type() {

        Set<String> contractWorkPlaceholders = PlaceholderGenerator.generatePlaceholders(ContractWork.class);
        assertEquals(contractWorkPlaceholders, PlaceholderModel.contractWorkPlaceholders);

        Set<String> contractorPlaceholders = PlaceholderGenerator.generatePlaceholders(Contractor.class);
        assertEquals(contractorPlaceholders, PlaceholderModel.contractorPlaceholders);

        Set<String> projectPlaceholders = PlaceholderGenerator.generatePlaceholders(Project.class);
        assertEquals(projectPlaceholders, PlaceholderModel.projectPlaceholders);

        Set<String> agencyDetailsPlaceholders = PlaceholderGenerator.generatePlaceholders(AgencyDetails.class);
        assertEquals(agencyDetailsPlaceholders, PlaceholderModel.agencyDetailsPlaceholders);

    }

}
