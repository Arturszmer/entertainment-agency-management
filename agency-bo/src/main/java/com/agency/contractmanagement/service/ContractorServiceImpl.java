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
        return null;
    }

    @Override
    public void delete(String pesel) {

    }
}
