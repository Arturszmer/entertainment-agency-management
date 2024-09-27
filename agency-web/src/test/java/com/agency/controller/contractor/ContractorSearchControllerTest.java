package com.agency.controller.contractor;

import com.agency.BaseIntegrationTestSettings;
import com.agency.dto.contractor.ContractorDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@Sql(scripts = "/sql-init/10-contractor-search-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/20-contractor-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ContractorSearchControllerTest extends BaseIntegrationTestSettings {

    private final static String urlPath = "/contractor";
    private final static String PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH = "13ff2322-ff08-11ee-92c8-0242ac120002";

    @Test
    @WithMockUser(authorities = "CONTRACTOR_MANAGEMENT")
    void should_get_contractor_details() throws Exception {
        // given

        // when
        MvcResult mvcResult = getRequest(urlPath + "/" + PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH, new LinkedMultiValueMap<>()).andReturn();
        ContractorDto contractorDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), ContractorDto.class);

        // then
        assertEquals(PUBLIC_ID_OF_EXISTING_CONTRACTOR_SEARCH, contractorDto.publicId().toString());

    }

    @Test
    @WithMockUser(authorities = "CONTRACTORS_VIEW")
    void should_get_all_contractors_pageable() throws Exception {
        //given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "0");
        params.add("size", "5");

        // when
        MvcResult mvcResult = getRequest(urlPath, params).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = mapper.readTree(response);

        JsonNode contentNode = jsonNode.get("content");

        // then
        assertEquals(5, contentNode.size());
    }

}
