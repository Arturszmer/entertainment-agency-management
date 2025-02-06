package com.agency.controller.contract;

import com.agency.contractmanagement.bills.model.Bill;
import com.agency.contractmanagement.bills.service.BillCalculationRequest;
import com.agency.contractmanagement.bills.service.ContractWorkBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('CONTRACT_MANAGEMENT')")
@RequestMapping("api/v1/contract-work/bill")
@RequiredArgsConstructor
public class ContractWorkBillController {

    private final ContractWorkBillService contractWorkBillService;

    @PostMapping
    ResponseEntity<Bill> createBill(@RequestBody BillCalculationRequest billCalculationRequest) {
        return ResponseEntity.ok(contractWorkBillService.createBill(billCalculationRequest));
    }

}
