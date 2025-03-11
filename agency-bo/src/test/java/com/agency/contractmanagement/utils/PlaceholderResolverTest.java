package com.agency.contractmanagement.utils;

import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractor.model.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dict.contract.ContractWorkStatus;
import com.agency.user.model.Address;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

class PlaceholderResolverTest {


    @Test
    void generate() {
        ContractWork contractWork = new ContractWork("a", LocalDate.now(), LocalDate.now(), LocalDate.now(), "aa", BigDecimal.valueOf(1200.20), "assd", ContractType.CONTRACT_WORK, true, "ddd", new Contractor("ee", "www",
                "gg", LocalDate.now(), new Address("plpl", "sad", "sduy", "xvc", "sdas", "si"), "pop", "wwi", "sao"), ContractWorkStatus.CONFIRMED);

        Map<String, Object> contractFilled = PlaceholderResolver.fillInPlaceholders(contractWork);
        Map<String, Object> contractorFilled = PlaceholderResolver.fillInPlaceholders(contractWork.getContractor());

        for (Map.Entry<String, Object> entry : contractFilled.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        for (Map.Entry<String, Object> entry : contractorFilled.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }


    }
}
