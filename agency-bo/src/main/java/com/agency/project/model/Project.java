package com.agency.project.model;

import com.agency.contractmanagement.model.contract.AbstractContract;
import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.dto.contractwork.ContractType;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dto.project.ProjectStatus;
import com.agency.exception.AgencyErrorResult;
import com.agency.exception.AgencyException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends AbstractContract {

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ContractWork> contracts = new ArrayList<>();

    @Override
    public ContractType getContractType() {
        return ContractType.PROJECT;
    }

    protected Project(String contractNumber,
                   LocalDate signDate,
                   LocalDate startDate,
                   LocalDate endDate,
                   String subjectOfTheContract,
                   BigDecimal salary,
                   String additionalInformation,
                   ContractType contractType,
                   ProjectStatus status) {
        super(contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType);
        this.status = status;
    }

    public static Project create(String contractNumber, ProjectCreateDto createDto){
        return new Project(
            contractNumber, createDto.signDate(), createDto.startDate(), createDto.endDate(),
                createDto.subjectOfTheContract(), createDto.salary(), createDto.additionalInformation(),
                ContractType.PROJECT, ProjectStatus.DRAFT
        );
    }

    public void updateStatus(ProjectStatus updatedStatus) {
        if(ProjectStatus.SIGNED == status || ProjectStatus.TERMINATED == status){
            throw new AgencyException(AgencyErrorResult.PROJECT_CANNOT_CHANGE_SIGN_OR_TERMINATE_STATUS);
        }
        status = updatedStatus;
    }

    public void addContractWork(ContractWork contractWork) {
        this.getContracts().add(contractWork);
    }
}
