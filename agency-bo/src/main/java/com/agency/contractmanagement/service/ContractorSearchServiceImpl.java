package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorAssignDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractor.ContractorShortInfoDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractorErrorResult;
import com.agency.project.model.Project;
import com.agency.project.repository.ProjectRepository;
import com.agency.search.SortableConfig;
import com.agency.service.ContractorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final SortableConfig sortableConfig;

    @Override
    @Transactional(readOnly = true)
    public ContractorDto getContractorFullInfo(String publicId) {
        return ContractorAssembler.toDto(repository.findContractorByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ContractorErrorResult.CONTRACTOR_DOES_NOT_EXISTS, publicId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractorShortInfoDto> getContractorsShortInfo(int page, int size, String sort, String order) {
        Pageable pagesRequest = sortableConfig.getPageable(page, size, sort, order);
        Page<Contractor> contractorsPage = repository.findAll(pagesRequest);
        List<ContractorShortInfoDto> contractorDtos = contractorsPage.getContent().stream()
                .map(ContractorAssembler::toShortContractorDto)
                .toList();
        return new PageImpl<>(contractorDtos, pagesRequest, contractorsPage.getTotalElements());
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
