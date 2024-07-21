package com.agency.project.model;

import com.agency.contractmanagement.model.contract.ContractWork;
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
    private ProjectStatus status;
    private List<ContractWork> contracts;
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
