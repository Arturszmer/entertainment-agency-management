package com.agency.service;

import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.project.ProjectDto;

public interface ProjectContractsService {

    ProjectDto createContract(ContractWorkCreateDto contractWorkCreateDto);
}
