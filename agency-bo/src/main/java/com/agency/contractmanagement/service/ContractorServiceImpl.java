package com.agency.contractmanagement.service;

import com.agency.contractmanagement.assembler.ContractorAssembler;
import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.contractmanagement.repository.ContractorRepository;
import com.agency.dto.contractor.ContractorCreateRequest;
import com.agency.dto.contractor.ContractorDto;
import com.agency.service.ContractorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository repository;

    @Override
    public ContractorDto add(ContractorCreateRequest request) {
        if(repository.findByPesel(request.pesel()).isPresent()){
            throw new IllegalStateException("The Contractor with given PESEL exist");
        }

        Contractor contractor = repository.save(ContractorAssembler.fromCreationRequest(request));
        log.info("The Contractor with public id {} has been created succesfully.", contractor.getPublicId());

        return ContractorAssembler.toDto(contractor);
    }

    @Override
    public ContractorDto edit(String publicId, ContractorCreateRequest request) {
        return repository.findContractorByPublicId(UUID.fromString(publicId)).map(contractor -> {
            contractor.updatePersonalData(request);
            Contractor save = repository.save(contractor);
            log.info("Personal data for contractor with public id {} has been changed successfully.", publicId);
            return ContractorAssembler.toDto(save);
        }).orElseThrow(() ->
                new RuntimeException(String.format("Contractor with public id %s does not exist.", publicId))
        );
    }

    @Override
    public void delete(String publicId) {
        repository.findContractorByPublicId(UUID.fromString(publicId)).ifPresent(contractor -> {
            if(contractor.getContract().isEmpty()){
                repository.delete(contractor);
                log.info("Contractor with public id {} has been deleted successfully.", publicId);
            } else {
                throw new RuntimeException("You cannot delete contractor with existing contracts");
            }
        });
    }
}
