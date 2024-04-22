package com.agency.project.assembler;

import com.agency.contractmanagement.assembler.ContractAssembler;
import com.agency.dto.project.ProjectDto;
import com.agency.project.model.Project;

import java.util.ArrayList;

public class ProjectAssembler {

    public static ProjectDto toDto(Project project){
        return new ProjectDto(
                project.getContractNumber(),
                project.getSignDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getSubjectOfTheContract(),
                project.getSalary(),
                project.getAdditionalInformation(),
                project.getContractType(),
                project.getContracts() == null
                        ? new ArrayList<>()
                        : project.getContracts().stream()
                        .map(ContractAssembler::toContractShortDto)
                        .toList()
        );
    }
}
