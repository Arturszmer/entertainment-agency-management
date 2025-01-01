package com.agency.contractor.service;

import com.agency.contractor.assembler.ContractorAssembler;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorSearchRequest;
import com.agency.dto.contractor.ContractorShortInfoDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractorErrorResult;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.repository.ProjectRepository;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.agency.exception.ProjectErrorResult.PROJECT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ContractorSearchServiceImpl implements ContractorSearchService {

    private final ContractorRepository repository;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public ContractorDto getContractorFullInfo(String publicId) {
        return ContractorAssembler.toDto(repository.findContractorByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ContractorErrorResult.CONTRACTOR_DOES_NOT_EXISTS, publicId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractorShortInfoDto> getContractorsShortInfo(ContractorSearchRequest request) {
        ContractorSearchFilter filter = ContractorSearchFilter.of(request);
        return repository.findAll(filter.getPredicate(), filter.getPageable())
                .map(ContractorAssembler::toShortContractorDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractorAssignDto> getContractorsForAssign(String projectNumber) {
        Project project = projectRepository.findByContractNumber(projectNumber)
                .orElseThrow(() -> new AgencyException(PROJECT_NOT_FOUND, projectNumber));

        List<Contractor> contractors = repository.findAll();
        return contractors.stream()
                .map(contractor -> ContractorAssembler.toAssignContractors(contractor, project))
                .collect(Collectors.toList());
    }
}
