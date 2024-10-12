package com.agency.contractmanagement.model;

import com.agency.contractor.model.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dict.contract.ContractWorkStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.agency.dict.contract.ContractType.CONTRACT_WORK;
import static com.agency.dict.contract.ContractWorkStatus.DRAFT;

public class ContractWorkBuilder {

    private String contractNumber;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String subjectOfTheContract;
    private BigDecimal salary;
    private String additionalInformation;
    private ContractType contractType = CONTRACT_WORK; // Default value as per the create method
    private boolean withCopyrights;
    private String projectNumber;
    private Contractor contractor;
    private ContractWorkStatus status = DRAFT; // Default value as per the create method

    public static ContractWorkBuilder aContractWorkBuilder() {
        return new ContractWorkBuilder();
    }

    public ContractWorkBuilder withContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public ContractWorkBuilder withSignDate(LocalDate signDate) {
        this.signDate = signDate;
        return this;
    }

    public ContractWorkBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ContractWorkBuilder withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public ContractWorkBuilder withSubjectOfTheContract(String subjectOfTheContract) {
        this.subjectOfTheContract = subjectOfTheContract;
        return this;
    }

    public ContractWorkBuilder withSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public ContractWorkBuilder withAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public ContractWorkBuilder withContractType(ContractType contractType) {
        this.contractType = contractType;
        return this;
    }

    public ContractWorkBuilder withCopyrights(boolean withCopyrights) {
        this.withCopyrights = withCopyrights;
        return this;
    }

    public ContractWorkBuilder withContractor(Contractor contractor) {
        this.contractor = contractor;
        return this;
    }

    public ContractWorkBuilder withProject(String projectNumber) {
        this.projectNumber = projectNumber;
        return this;
    }

    public ContractWorkBuilder withStatus(ContractWorkStatus status) {
        this.status = status;
        return this;
    }

    public ContractWork build() {
        return new ContractWork(
                contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType, withCopyrights, projectNumber, contractor, status
        );
    }
}
