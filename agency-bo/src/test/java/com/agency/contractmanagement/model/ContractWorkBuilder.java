package com.agency.contractmanagement.model;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contractwork.ContractType;
import com.agency.dto.contractwork.ContractWorkStatus;
import com.agency.project.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.agency.dto.contractwork.ContractType.CONTRACT_WORK;
import static com.agency.dto.contractwork.ContractWorkStatus.DRAFT;

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
    private Contractor contractor;
    private Project project;
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

    public ContractWorkBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    public ContractWorkBuilder withStatus(ContractWorkStatus status) {
        this.status = status;
        return this;
    }

    public ContractWork build() {
        return new ContractWork(
                contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType, withCopyrights, contractor, project, status
        );
    }
}
