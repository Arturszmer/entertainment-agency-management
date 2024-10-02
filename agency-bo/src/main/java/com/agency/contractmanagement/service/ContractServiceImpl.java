package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractAssembler;
import com.agency.contractmanagement.model.contract.ContractWork;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractWorkRepository;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dict.contract.ContractType;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.exception.ContractorErrorResult;
import com.agency.project.model.Project;
import com.agency.project.model.ProjectCost;
import com.agency.project.service.ContractNumberGenerator;
import com.agency.service.ContractService;
import com.agency.project.service.CostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.agency.contractmanagement.validator.ContractDatesValidator.isContractHasCorrectDatesForProject;
import static com.agency.dict.project.ProjectStatus.TERMINATED;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    private final ContractWorkRepository contractWorkRepository;
    private final ContractorRepository contractorRepository;
    private final ContractNumberGenerator numberGenerator;
    private final CostService costService;

    @Override
    @Transactional
    public ContractWorkDto createContractOfWork(ContractWorkCreateDto createDto) {
        Contractor contractor = getContractor(createDto);

        Project projectForContract = getProjectForContract(createDto, contractor);

        isContractHasCorrectDatesForProject(projectForContract, createDto);
        checkIsProjectTerminated(createDto, projectForContract);

        String contractNumber = numberGenerator.generateContractNumber(createDto.contractDetailsDto().signDate(), ContractType.CONTRACT_WORK);

        ContractWork contract = contractWorkRepository.save(ContractWork.create(contractNumber, createDto, contractor));
        ProjectCost projectCost = costService.addContractTypeCost(contract, projectForContract);
        projectForContract.addCost(projectCost);
        contractor.addNewContract(contract);
        contractorRepository.save(contractor);
        log.info("The new contract of work for contractor no. id: {} has beed created", contractor.getId());
        return ContractAssembler.toContractWorkDto(contract);
    }

    private static void checkIsProjectTerminated(ContractWorkCreateDto createDto, Project projectForContract) {
        if(TERMINATED.equals(projectForContract.getStatus())){
            throw new AgencyException(ContractErrorResult.PROJECT_IS_TERMINATED, createDto.projectContractNumber());
        }
    }

    private Project getProjectForContract(ContractWorkCreateDto createDto, Contractor contractor) {
        return contractor.getProjects().stream()
                .filter(project -> createDto.projectContractNumber().equals(project.getContractNumber()))
                .findFirst()
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACTOR_IS_NOT_PART_OF_THE_PROJECT,
                        contractor.getFirstName(), contractor.getLastName(), createDto.projectContractNumber()));
    }

    private Contractor getContractor(ContractWorkCreateDto createDto) {
        return contractorRepository.findContractorByPublicId(UUID.fromString(createDto.contractorPublicId()))
                .orElseThrow(() -> new AgencyException(ContractorErrorResult.CONTRACTOR_DOES_NOT_EXISTS, createDto.contractorPublicId()));
    }
}
