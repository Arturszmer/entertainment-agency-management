package com.agency.project.assembler;

import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.project.model.Project;

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
                project.getStatus(),
                project.getContractType(),
                project.isInternal(),
                getOrganizerName(project)
        );
    }

    public static ProjectSearchDto toSearchDto(Project project) {
        return new ProjectSearchDto(
                project.getContractNumber(),
                getOrganizerName(project),
                project.getSubjectOfTheContract(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContractors().size()
        );
    }

    private static String getOrganizerName(Project project) {
        return project.getOrganizer() != null
                ? project.getOrganizer().getOrganizerName()
                : "Internal project";
    }
}
