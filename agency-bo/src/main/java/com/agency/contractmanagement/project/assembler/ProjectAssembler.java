package com.agency.contractmanagement.project.assembler;

import com.agency.contractmanagement.project.model.Project;
import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.contractor.model.Contractor;
import com.agency.dto.bill.ContractWorkBillDto;
import com.agency.dto.contractwork.BasicContractDetailsDto;
import com.agency.dto.project.ProjectContractorAssignResponse;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.dto.project.ProjectToAssignContractorDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectAssembler {

    public static ProjectDto toDto(Project project) {
        BasicContractDetailsDto basicContractDetailsDto = getBasicContractDetailsDto(project);

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

    public static Set<ContractWorkBillDto> toProjectContractWorkBillDto(Project project) {
        List<Contractor> projectContractors = project.getContractors().stream()
                .map(contractor -> contractor.getContractorWithProjectContracts(project.getContractNumber()))
                .toList();
        return projectContractors.stream()
                .flatMap(contractor -> contractor.getContracts().stream())
                .flatMap(contractWork -> contractWork.getBills().stream()
                        .map(bill -> BillsAssembler.toContractWorkBillDto(bill, contractWork))).collect(Collectors.toSet());
    }

//    public static ProjectDto toDtoFullInfo(Project project) {
//        BasicContractDetailsDto basicContractDetailsDto = getBasicContractDetailsDto(project);
//        // TODO: Może jednak to wywalić i zostawić odseparowane enpointy na koszty i inne | UŻYC MECHANIZM DO MAPOWANIA RACHUNKÓW
//        List<Contractor> projectContractors = project.getContractors().stream()
//                .map(contractor -> contractor.getContractorWithProjectContracts(project.getContractNumber()))
//                .toList();
//        List<ContractWorkBillDto> bills = projectContractors.stream()
//                .flatMap(contractor -> contractor.getContracts().stream())
//                .flatMap(contractWork -> contractWork.getBills().stream()
//                        .map(BillsAssembler::toContractWorkBillDto)).toList();
//
//
//        return new ProjectDto(
//                project.getContractNumber(),
//                basicContractDetailsDto,
//                project.getStatus(),
//                project.getContractType(),
//                project.isInternal(),
//                getOrganizerName(project),
//                project.getContractors().stream()
//                        .map(contractor -> contractor.getContractorWithProjectContracts(project.getContractNumber()))
//                        .map(ContractorAssembler::toShortContractorDto).toList()
//
//        );
//    }

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

    @NotNull
    private static BasicContractDetailsDto getBasicContractDetailsDto(Project project) {
        return new BasicContractDetailsDto(
                project.getPublicId().toString(),
                project.getSignDate(),
                project.getStartDate(),
                project.getEndDate(),
                project.getContractSubject(),
                project.getSalary(),
                project.getAdditionalInformation()
        );
    }

    public static ProjectContractorAssignResponse toProjectContractorAssignResponse(Project project) {
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
