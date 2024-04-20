package com.agency.controller.contractor;

import com.agency.BaseIntegrationTestSettings;
import com.agency.dto.contractor.ContractorDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Sql(scripts = "/sql-init/contractor-search-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/contractor-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ContractorSearchControllerTest extends BaseIntegrationTestSettings {

    private final static String urlPath = "/contractor";
    private final static String PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH = "13ff2322-ff08-11ee-92c8-0242ac120002";

    @Test
    @WithMockUser(authorities = "CONTRACTOR_MANAGEMENT")
    public void should_get_contractor_details() throws Exception {
        // given

        // when
        MvcResult mvcResult = getRequest(urlPath + "/" + PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH, new LinkedMultiValueMap<>()).andReturn();
        ContractorDto contractorDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContractorDto.class);

        // then
        assertEquals(PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH, contractorDto.publicId().toString());

    }

}
