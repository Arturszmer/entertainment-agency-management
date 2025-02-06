package com.agency.controller.contract;

import com.agency.BaseIntegrationTestSettings;
import com.agency.contractmanagement.bills.model.ContractWorkBill;
import com.agency.contractmanagement.bills.service.BillCalculationRequest;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.contractwork.repository.ContractWorkRepository;
import com.agency.model.BillModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WithMockUser(authorities = "CONTRACT_MANAGEMENT")
@Sql(scripts = "/sql-init/60-bill-init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql-init/60-bill-clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ContractWorkBillControllerTest extends BaseIntegrationTestSettings {

    @Autowired
    private ContractWorkRepository repository;
    private final static String CONTRACT_URL_PATH = "/contract-work/bill";
    private final static String CONTRACT_WORK_NUMBER = "D2024/STY/UoD1";

    @Test
    @Transactional
    void should_createContractWorkBill() throws Exception {
        //given
        BillCalculationRequest billCalculationRequest = BillModel.getBillCalculationRequest();
        String body = mapper.writeValueAsString(billCalculationRequest);

        //when
        postRequest(CONTRACT_URL_PATH, body).andReturn();

        Optional<ContractWork> contractWorkByContractNumber = repository.findContractWorkByContractNumber(CONTRACT_WORK_NUMBER);
        assertTrue(contractWorkByContractNumber.isPresent());
        ContractWork contractWork = contractWorkByContractNumber.get();
        assertFalse(contractWork.getBills().isEmpty());

        ContractWorkBill contractWorkBill = contractWork.getBills().get(0);
        assertEquals(billCalculationRequest.billPaymentDay(), contractWorkBill.getPaymentDate());
        assertEquals(0, contractWorkBill.getGrossAmount().compareTo(BigDecimal.valueOf(560.0)));
        assertEquals(0, contractWorkBill.getNetAmount().compareTo(BigDecimal.valueOf(526.0)));
        assertEquals(0, contractWorkBill.getTaxAmount().compareTo(BigDecimal.valueOf(34)));
        assertEquals(0, contractWorkBill.getTaxBase().compareTo(BigDecimal.valueOf(280)));

    }

}
