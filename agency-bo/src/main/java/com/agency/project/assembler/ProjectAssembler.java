package com.agency.project.assembler;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.project.*;
import com.agency.project.model.Project;

public class ProjectAssembler {

    public static ProjectDto toDto(Project project){
        BasicContractDetailsDto basicContractDetailsDto = new BasicContractDetailsDto(
                project.getPublicId().toString(),
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
                project.getStatus(),
                project.getContractType(),
                project.isInternal(),
                getOrganizerName(project),
                project.getContractors().stream()
                        .map(ContractorAssembler::toShortContractorDto).toList()
        );
    }

    public static ProjectSearchDto toSearchDto(Project project) {
        return new ProjectSearchDto(
                project.getPublicId().toString(),
                project.getContractNumber(),
                getOrganizerName(project),
                project.getSubjectOfTheContract(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContractors().size()
        );
    }

    public static ProjectContractorAssignResponse toProjectContractorAssignResponse(Project project){
        return new ProjectContractorAssignResponse(
                project.getPublicId().toString(),
                project.getContractors().stream()
                        .map(ContractorAssembler::toShortContractorDto).toList()
        );
    }

    public static ProjectToAssignContractorDto toProjectToAssignContractor(Project project) {
        return new ProjectToAssignContractorDto(
                project.getPublicId().toString(),
                project.getContractNumber(),
                getOrganizerName(project),
                project.getSubjectOfTheContract()
        );
    }

    private static String getOrganizerName(Project project) {
        return project.getOrganizer() != null
                ? project.getOrganizer().getOrganizerName()
                : "Internal project";
    }
}
