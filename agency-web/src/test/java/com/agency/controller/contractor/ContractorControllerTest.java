package com.agency.controller.contractor;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.model.ContractorModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WithMockUser(authorities = "CONTRACTOR_MANAGEMENT")
class ContractorControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    ContractorRepository repository;

    private final static String urlPath = "/contractor";

    @Test
    public void should_add_new_contractor() throws Exception {
        // given
        ContractorCreateRequest contractorCreateRequest = ContractorModel.contractorCreateRequestBuild();
        String body = mapper.writeValueAsString(contractorCreateRequest);

        // when
        MvcResult mvcResult = postRequest(urlPath, body).andReturn();
        ContractorDto contractorDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContractorDto.class);

        // then
        Optional<Contractor> contractor = repository.findByPesel(contractorCreateRequest.pesel());
        assertTrue(contractor.isPresent());
        assertNotNull(contractor.get().getPublicId());
        assertTrue(contractorDto.contracts().isEmpty());
        assertEquals(contractorDto.publicId(), contractor.get().getPublicId());
    }

}
