package com.agency.contractmanagement.contractwork.model;

import com.agency.common.ExcludeFromPlaceholders;
import com.agency.contractmanagement.bills.model.Bill;
import com.agency.contractmanagement.bills.model.BillsContracts;
import com.agency.contractmanagement.bills.model.ContractWorkBill;
import com.agency.contractor.model.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dict.contract.ContractWorkStatus;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.agency.dict.contract.ContractType.CONTRACT_WORK;
import static com.agency.dict.contract.ContractWorkStatus.CONFIRMED;
import static com.agency.dict.contract.ContractWorkStatus.DRAFT;

@Entity
@Table(name = "contract_work")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ContractWork extends AbstractContract implements BillsContracts<ContractWorkBill> {

    @Column(name = "with_copyrights")
    @Setter
    private boolean withCopyrights;

    @Column(name = "project_number", nullable = false)
    @ExcludeFromPlaceholders
    private String projectNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @NonNull
    @Setter
    @ExcludeFromPlaceholders
    private Contractor contractor;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ExcludeFromPlaceholders
    private ContractWorkStatus status;

    @Column(name = "file_id_reference")
    @Setter
    @ExcludeFromPlaceholders
    private String filename;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @ExcludeFromPlaceholders
    private List<ContractWorkBill> bills = new ArrayList<>();

    public ContractWork(String contractNumber,
                        LocalDate signDate,
                        LocalDate startDate,
                        LocalDate endDate,
                        String subjectOfTheContract,
                        BigDecimal salary,
                        String additionalInformation,
                        ContractType contractType,
                        boolean withCopyrights,
                        String projectNumber,
                        @NonNull Contractor contractor,
                        ContractWorkStatus status) {
        super(UUID.randomUUID(), contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType);
        this.withCopyrights = withCopyrights;
        this.projectNumber = projectNumber;
        this.contractor = contractor;
        this.status = status;
    }

    public static ContractWork create(String contractNumber, ContractWorkCreateDto createDto, Contractor contractor) {
        return new ContractWork(
                contractNumber,
                createDto.contractDetailsDto().signDate(),
                createDto.contractDetailsDto().startDate(),
                createDto.contractDetailsDto().endDate(),
                createDto.contractDetailsDto().contractSubject(),
                createDto.contractDetailsDto().salary(),
                createDto.contractDetailsDto().additionalInformation(),
                CONTRACT_WORK,
                createDto.withCopyrights(),
                createDto.projectContractNumber(),
                contractor,
                DRAFT
        );
    }

    @Override
    public BigDecimal getContractBalance() {
        BigDecimal existedBillsAmount = this.bills.stream()
                .map(Bill::getGrossAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return getSalary().subtract(existedBillsAmount);
    }

    @Override
    public void checkForDelete() {
        if (DRAFT != status) {
            throw new AgencyException(ContractErrorResult.CONTRACT_CANNOT_BE_DELETED);
        }
    }

    @Override
    public int getBillsNumber() {
        return bills.size();
    }

    public boolean hasGeneratedFile() {
        return filename != null && !filename.isEmpty();
    }

    @Override
    public void addBill(ContractWorkBill bill) {
        bills.add(bill);
    }

    public void confirm() {
        if (status == DRAFT) {
            status = CONFIRMED;
        }
    }

    public void cancelConfirmation() {
        if (status == CONFIRMED) {
            status = DRAFT;
        }
    }
}
