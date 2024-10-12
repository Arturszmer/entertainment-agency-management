package com.agency.controller.contract;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.agency.model.ContractModel.createContractWorkCreateDto;
import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(authorities = "CONTRACT_MANAGEMENT")
@Sql(scripts = "/sql-init/40-contract-create-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/40-contract-creat-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ContractControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ContractorRepository repository;
    private final static String CONTRACT_URL_PATH = "/contract-work";

    @Test
    @Transactional
    public void should_add_new_contract_to_contractor_being_in_existing_project() throws Exception {
        // given
        ContractWorkCreateDto contractWorkCreateDto = createContractWorkCreateDto();
        String body = mapper.writeValueAsString(contractWorkCreateDto);

        // when
        MvcResult mvcResult = postRequest(CONTRACT_URL_PATH, body).andReturn();
        ContractWorkDto contractWorkDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContractWorkDto.class);
        Optional<Contractor> contractor = repository.findContractorByPublicId(UUID.fromString(contractWorkCreateDto.contractorPublicId()));

        // then
        assertNotNull(contractWorkDto);
        assertTrue(contractor.isPresent());
        assertEquals(contractor.get().getContracts().size(), 1);

    }

}
