package com.agency.project.model;

import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.dto.contractwork.ContractType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
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


}
