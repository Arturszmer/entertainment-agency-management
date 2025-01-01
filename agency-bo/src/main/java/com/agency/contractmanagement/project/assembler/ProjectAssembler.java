package com.agency.contractmanagement.project.assembler;

import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.project.ProjectContractorAssignResponse;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.dto.project.ProjectToAssignContractorDto;
import com.agency.contractmanagement.project.model.Project;

public class ProjectAssembler {

    public static ProjectDto toDto(Project project){
        BasicContractDetailsDto basicContractDetailsDto = new BasicContractDetailsDto(
                project.getPublicId().toString(),
                project.getSignDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContractSubject(),
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
                        .map(contractor -> contractor.getContractorWithProjectContracts(project.getContractNumber()))
                        .map(ContractorAssembler::toShortContractorDto).toList()
        );
    }

    public static ProjectSearchDto toSearchDto(Project project) {
        return new ProjectSearchDto(
                project.getPublicId().toString(),
                project.getContractNumber(),
                getOrganizerName(project),
                project.getContractSubject(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContractors().size()
        );
    }

    public static ProjectContractorAssignResponse toProjectContractorAssignResponse(Project project){
        return new ProjectContractorAssignResponse(
                project.getPublicId().toString(),
                project.getContractors().stream()
                        .map(contractor -> contractor.getContractorWithProjectContracts(project.getContractNumber()))
                        .map(ContractorAssembler::toShortContractorDto).toList()
        );
    }

    public static ProjectToAssignContractorDto toProjectToAssignContractor(Project project) {
        return new ProjectToAssignContractorDto(
                project.getPublicId().toString(),
                project.getContractNumber(),
                getOrganizerName(project),
                project.getContractSubject()
        );
    }

    private static String getOrganizerName(Project project) {
        return project.getOrganizer() != null
                ? project.getOrganizer().getOrganizerName()
                : "Internal project";
    }
}
