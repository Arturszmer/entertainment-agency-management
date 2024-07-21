package com.agency.project.model;

import com.agency.contractmanagement.model.contract.AbstractContract;
import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.dict.project.ProjectStatus;
import com.agency.exception.ContractorErrorResult;
import com.agency.exception.AgencyException;
import com.agency.organizer.model.Organizer;
import jakarta.persistence.*;
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

    @ManyToMany
    private List<Contractor> contractors = new ArrayList<>();

    @ManyToOne
    private Organizer organizer;

    /*
    If it's true organizer should be null, and the owner of the project is agency
     */
    private boolean isInternal;

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
                   ProjectStatus status,
                   boolean isInternal) {
        super(contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType);
        this.status = status;
        this.isInternal = isInternal;
    }

    public static Project create(String contractNumber, ProjectCreateDto createDto){
        return new Project(
            contractNumber, createDto.signDate(), createDto.startDate(), createDto.endDate(),
                createDto.projectSubject(), createDto.salary(), createDto.additionalInformation(),
                ContractType.PROJECT, ProjectStatus.DRAFT, createDto.isInternal()
        );
    }

    public void updateStatus(ProjectStatus updatedStatus) {
        if(ProjectStatus.SIGNED == status || ProjectStatus.TERMINATED == status){
            throw new AgencyException(ContractorErrorResult.PROJECT_CANNOT_CHANGE_SIGN_OR_TERMINATE_STATUS);
        }
        status = updatedStatus;
    }

    public void addContractWork(ContractWork contractWork) {
        this.getContracts().add(contractWork);
    }

    public void addOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
}
