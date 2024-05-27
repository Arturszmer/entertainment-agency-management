package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.exception.AgencyException;
import com.agency.service.ContractorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.agency.contractmanagement.constant.ContractorLogsMessage.*;
import static com.agency.exception.ContractorErrorResult.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository repository;

    @Override
    public ContractorDto add(ContractorCreateRequest request) {
        if(repository.findByPesel(request.pesel()).isPresent()){
            throw new AgencyException(CONTRACTOR_WITH_PESEL_EXISTS);
        }

        Contractor contractor = repository.save(ContractorAssembler.fromCreationRequest(request));
        log.info(SUCCESSFULLY_CREATED, contractor.getPublicId());

        return ContractorAssembler.toDto(contractor);
    }

    @Override
    @Transactional
    public ContractorDto edit(String publicId, ContractorCreateRequest request) {
        return repository.findContractorByPublicId(UUID.fromString(publicId)).map(contractor -> {
            contractor.updatePersonalData(request);
            Contractor save = repository.save(contractor);
            log.info(SUCCESSFULLY_UPDATED, publicId);
            return ContractorAssembler.toDto(save);
        }).orElseThrow(() ->
                new AgencyException(CONTRACTOR_DOES_NOT_EXISTS, publicId)
        );
    }

    @Override
    @Transactional
    public void delete(String publicId) {
        repository.findContractorByPublicId(UUID.fromString(publicId)).ifPresent(contractor -> {
            if(contractor.getContract().isEmpty()){
                repository.delete(contractor);
                log.info(SUCCESSFULLY_DELETED, publicId);
            } else {
                throw new AgencyException(EXISTING_CONTRACT_EXCEPTION, publicId);
            }
        });
    }
}
