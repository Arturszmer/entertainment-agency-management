package com.agency.controller.contractor;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.agency.model.ContractorModel.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@WithMockUser(authorities = "CONTRACTOR_MANAGEMENT")
@Sql(scripts = "/sql-init/20-contractor-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/20-contractor-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ContractorControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ContractorRepository repository;

    private final static String urlPath = "/contractor";
    private final static String PUBLIC_ID_OF_EXISTING_CONTRACTOR = "fb75951a-fe54-11ee-92c8-0242ac120002";

    @Test
    public void should_add_new_contractor() throws Exception {
        // given
        ContractorCreateRequest contractorCreateRequest = contractorCreateRequestBuild();
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

    @Test
    public void should_edit_existing_contractor() throws Exception {
        // given
        String editedLastName = "Doe";
        String pesel = "03260785766";
        ContractorCreateRequest contractorCreateRequest = contractorCreateCustomRequestBuild(FIRST_NAME, editedLastName, pesel);
        String body = mapper.writeValueAsString(contractorCreateRequest);

        // when
        putRequest(urlPath + "/" + PUBLIC_ID_OF_EXISTING_CONTRACTOR, body);

        // then
        Optional<Contractor> contractor = repository.findContractorByPublicId(UUID.fromString(PUBLIC_ID_OF_EXISTING_CONTRACTOR));
        assertTrue(contractor.isPresent());
        assertEquals(editedLastName, contractor.get().getLastName());

    }

    @Test
    public void should_delete_existing_contractor() throws Exception {
        // given
        // when
        deleteRequest(urlPath + "/" + PUBLIC_ID_OF_EXISTING_CONTRACTOR);

        // then
        assertFalse(repository.findContractorByPublicId(UUID.fromString(PUBLIC_ID_OF_EXISTING_CONTRACTOR)).isPresent());

    }

}
