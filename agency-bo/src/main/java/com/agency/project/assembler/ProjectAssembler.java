package com.agency.project.assembler;

import com.agency.contractmanagement.assembler.ContractAssembler;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.project.ProjectDto;
import com.agency.project.model.Project;

import java.util.ArrayList;

public class ProjectAssembler {

    public static ProjectDto toDto(Project project){
        BasicContractDetailsDto basicContractDetailsDto = new BasicContractDetailsDto(
                project.getSignDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getSubjectOfTheContract(),
                project.getSalary(),
                project.getAdditionalInformation()
        );
        return new ProjectDto(
                project.getContractNumber(),
                basicContractDetailsDto,
                project.getContractType(),
                project.getContracts() == null
                        ? new ArrayList<>()
                        : project.getContracts().stream()
                        .map(ContractAssembler::toContractShortDto)
                        .toList()
        );
    }
}
