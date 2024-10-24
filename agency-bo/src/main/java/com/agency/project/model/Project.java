package com.agency.project.model;

import com.agency.contractmanagement.model.AbstractContract;
import com.agency.contractor.model.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;
import com.agency.dto.project.ProjectCreateDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractorErrorResult;
import com.agency.exception.ProjectErrorResult;
import com.agency.organizer.model.Organizer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class Project extends AbstractContract implements CostRelated {

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "project_contractor",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "contractor_id")
    )
    private List<Contractor> contractors = new ArrayList<>();

    @ManyToOne
    private Organizer organizer;

    /*
    If it's true organizer should be null, and the owner of the project is agency
     */
    private boolean isInternal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectCost> projectCosts = new ArrayList<>();

    @Override
    public ContractType getContractType() {
        return ContractType.PROJECT;
    }

    protected Project(
                   String contractNumber,
                   LocalDate signDate,
                   LocalDate startDate,
                   LocalDate endDate,
                   String subjectOfTheContract,
                   BigDecimal salary,
                   String additionalInformation,
                   ProjectStatus status,
                   boolean isInternal) {
        super(UUID.randomUUID(), contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, ContractType.PROJECT);
        this.status = status;
        this.isInternal = isInternal;
    }

    public static Project create(String contractNumber, ProjectCreateDto createDto){
        return new Project(contractNumber, createDto.signDate(), createDto.startDate(), createDto.endDate(),
                createDto.projectSubject(), createDto.salary(), createDto.additionalInformation(),
                ProjectStatus.DRAFT, createDto.isInternal()
        );
    }

    public void updateStatus(ProjectStatus updatedStatus) {
        if(ProjectStatus.SIGNED == status || ProjectStatus.TERMINATED == status){
            throw new AgencyException(ContractorErrorResult.PROJECT_CANNOT_CHANGE_SIGN_OR_TERMINATE_STATUS);
        }
        status = updatedStatus;
    }

    public void addOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public void assignContractor(Contractor contractor){
        if(contractors.stream().anyMatch(c -> c.getPublicId().equals(contractor.getPublicId()))){
            log.info("Contractor with public id {} exists into the project", contractor.getPublicId());
            return;
        }
        this.contractors.add(contractor);
    }

    public void addCost(ProjectCost newCost) {
        getProjectCosts().add(newCost);
    }

    @Override
    public void checkForDelete() {
        if (status != ProjectStatus.DRAFT) {
            throw new AgencyException(ProjectErrorResult.CANNOT_DELETE_PROJECT, "Project has not in draft status");
        }
        if (!contractors.isEmpty()) {
            throw new AgencyException(ProjectErrorResult.CANNOT_DELETE_PROJECT, "Contractors are existed in the project");
        }
    }
}
