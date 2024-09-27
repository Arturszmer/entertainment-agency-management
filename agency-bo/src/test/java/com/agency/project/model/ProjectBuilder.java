package com.agency.project.model;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dict.contract.ContractType;
import com.agency.dict.project.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProjectBuilder extends Project {

    private String contractNumber;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String subjectOfTheContract;
    private BigDecimal salary;
    private String additionalInformation;
    private ContractType contractType;
    private List<Contractor> contractors;
    private ProjectStatus status;
    private boolean isInternal;

    public static ProjectBuilder aProjectBuilder(){
        return new ProjectBuilder();
    }

    public ProjectBuilder withContractNumber(String contractNumber){
        this.contractNumber = contractNumber;
        return this;
    }

    public ProjectBuilder withProjectStatus(ProjectStatus status){
        this.status = status;
        return this;
    }

    public ProjectBuilder withContractors(List<Contractor> contractors){
        this.contractors = contractors;
        return this;
    }

    public ProjectBuilder withSignDate(LocalDate signDate){
        this.signDate = signDate;
        return this;
    }

    public ProjectBuilder withStartDate(LocalDate startDate){
        this.startDate = startDate;
        return this;
    }

    public ProjectBuilder withEndDate(LocalDate endDate){
        this.endDate = endDate;
        return this;
    }

    public Project buildProject(){
        return new Project(
                contractNumber,
                signDate,
                startDate,
                endDate,
                subjectOfTheContract,
                salary,
                additionalInformation,
                contractType,
                status,
                isInternal
        );
    }


}
