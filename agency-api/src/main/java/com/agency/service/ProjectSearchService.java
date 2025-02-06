package com.agency.service;

import com.agency.dto.bill.ContractWorkBillDto;
import com.agency.dto.project.ProjectDto;
import com.agency.dto.project.ProjectSearchDto;
import com.agency.dto.project.ProjectToAssignContractorDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProjectSearchService {

    Page<ProjectSearchDto> findAll(int page, int size, String sort, String order);

    ProjectDto getProjectDetails(String publicId);

    List<ProjectToAssignContractorDto> findAllToAssign(String assignedContractorPublicId);

    Set<ContractWorkBillDto> getProjectBills(UUID publicId);
}
