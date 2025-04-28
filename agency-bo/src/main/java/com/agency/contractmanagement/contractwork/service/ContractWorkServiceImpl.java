package com.agency.contractmanagement.contractwork.service;

import com.agency.contractmanagement.contractnumber.model.ContractNumberStatus;
import com.agency.contractmanagement.contractnumber.service.ContractNumberService;
import com.agency.contractmanagement.contractwork.assembler.ContractAssembler;
import com.agency.contractmanagement.contractwork.model.ContractWork;
import com.agency.contractmanagement.contractwork.repository.ContractWorkRepository;
import com.agency.contractmanagement.project.model.Project;
import com.agency.contractmanagement.project.model.ProjectCost;
import com.agency.contractmanagement.project.service.CostService;
import com.agency.contractor.model.Contractor;
import com.agency.contractor.repository.ContractorRepository;
import com.agency.dict.contract.ContractType;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkDto;
import com.agency.exception.AgencyException;
import com.agency.exception.ContractErrorResult;
import com.agency.exception.ContractorErrorResult;
import com.agency.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.agency.contractmanagement.contractwork.service.ContractLogsMessage.*;
import static com.agency.contractmanagement.contractwork.validator.ContractDatesValidator.isContractHasCorrectDatesForProject;
import static com.agency.dict.project.ProjectStatus.TERMINATED;

@RequiredArgsConstructor
@Service
@Slf4j
public class ContractWorkServiceImpl implements ContractService {

    private final ContractWorkRepository contractWorkRepository;
    private final ContractorRepository contractorRepository;
    private final ContractWorkDocumentServiceImpl contractWorkDocumentService;
    private final ContractNumberService contractNumberService;
    private final CostService costService;

    @Override
    @Transactional
    public ContractWorkDto createContractOfWork(ContractWorkCreateDto createDto) {
        Contractor contractor = getContractor(createDto);

        Project project = getProjectForContract(createDto, contractor);

        isContractHasCorrectDatesForProject(project, createDto);
        checkIsProjectTerminated(createDto, project);

        String contractNumber = contractNumberService.createContractNumber(createDto.contractDetailsDto().signDate(), ContractType.CONTRACT_WORK);
        ContractWork contract = contractWorkRepository.save(ContractWork.create(contractNumber, createDto, contractor));
        addProjectCost(createDto, contract, project);
        contractor.addNewContract(contract);
        contractorRepository.save(contractor);
        log.info("The new contract of work for contractor no. id: {} has been created", contractor.getId());
        return ContractAssembler.toContractWorkDto(contract);
    }

    @Override
    @Transactional
    public void deleteContractOfWork(String publicId) {
        ContractWork contractWork = getEntity(publicId);
        log.info(REMOVING_PROCESS_STARTED, publicId);
        contractWork.checkForDelete();
        if (contractWork.hasGeneratedFile()) {
            log.info(TRY_TO_REMOVE_DOCUMENT, publicId);
            contractWorkDocumentService.removeDocument(publicId);
        }
        costService.removeCostsByCostReference(contractWork.getContractNumber());
        contractNumberService.deleteContractNumber(contractWork.getContractNumber());
        contractWorkRepository.delete(contractWork);
        log.info(SUCCESSFULLY_DELETED, publicId);
    }

    @Override
    @Transactional
    public void confirmContract(String publicId) {
        ContractWork contractWork = getEntity(publicId);
        log.info(CONFIRM_CONTRACT_PROCESS_STARTED, publicId);
        contractWork.confirm();
        contractNumberService.updateContractNumberStatus(contractWork.getContractNumber(), ContractNumberStatus.FINAL)
                .ifPresent(contractWork::updateContractNumber);
        contractWorkRepository.save(contractWork);
        log.info(CONFIRMED_SUCCESSFULLY, publicId);
    }

    @Override
    public void cancelConfirmation(String publicId) {
        ContractWork contractWork = getEntity(publicId);
        if (canBeCancelled(contractWork)) {
            contractWork.cancelConfirmation();
            contractNumberService.updateContractNumberStatus(contractWork.getContractNumber(), ContractNumberStatus.DRAFT)
                    .ifPresent(contractWork::updateContractNumber);
            contractWorkRepository.save(contractWork);
            log.info(CANCEL_CONFIRMATION, publicId);
        }
    }

    private void addProjectCost(ContractWorkCreateDto createDto, ContractWork contract, Project project) {
        if (createDto.generateCost()) {
            ProjectCost projectCost = costService.addContractTypeCost(contract, project);
            project.addCost(projectCost);
        }
    }

    private boolean canBeCancelled(ContractWork contractWork) {
        return contractWork.getBills().isEmpty() && !contractWork.hasGeneratedFile();
    }

    private ContractWork getEntity(String publicId) {
        return contractWorkRepository.findContractWorkByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new AgencyException(ContractErrorResult.CONTRACT_NOT_EXISTS, publicId));
    }

    private static void checkIsProjectTerminated(ContractWorkCreateDto createDto, Project projectForContract) {
        if (TERMINATED.equals(projectForContract.getStatus())) {
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
