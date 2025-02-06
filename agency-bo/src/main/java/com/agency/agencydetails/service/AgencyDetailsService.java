package com.agency.agencydetails.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.common.validators.PeselValidator;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.exception.AgencyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.agency.exception.AgencyExceptionResult.AGENCY_NOT_INITIALIZED_EXCEPTION;
import static com.agency.exception.AgencyExceptionResult.ONLY_ONE_AGENCY_CAN_EXIST;
import static com.agency.exception.ContractorErrorResult.PESEL_INVALID_EXCEPTION;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyDetailsService {

    private final AgencyDetailsRepository repository;

    public AgencyDetailsDto initializeAgency(AgencyDetailsDto agencyDetailsDto) {

        validatePeselIfExists(agencyDetailsDto);

        if(repository.findAll().isEmpty()){
            AgencyDetails agencyDetails = repository.save(AgencyDetails.initialize(agencyDetailsDto));
            log.info("Agency with NIP number {} has been initialized", agencyDetails.getNip());
            return AgencyDetailsAssembler.toDto(agencyDetails);
        } else {
            throw new AgencyException(ONLY_ONE_AGENCY_CAN_EXIST);
        }
    }

    public AgencyDetailsDto updateAgencyDetails(AgencyDetailsDto agencyDetailsDto) {
        validatePeselIfExists(agencyDetailsDto);
        Optional<AgencyDetails> agencyDetailsOpt = repository.findAll().stream().findFirst();
        return agencyDetailsOpt.map(agencyDetails -> {
            agencyDetails.update(agencyDetailsDto);
            return AgencyDetailsAssembler.toDto(repository.save(agencyDetails));
        }).orElseThrow(() -> new AgencyException(AGENCY_NOT_INITIALIZED_EXCEPTION));
    }

    public AgencyDetailsDto getAgencyDetails() {
        return repository.findAll().stream().findFirst()
                .map(AgencyDetailsAssembler::toDto)
                .orElseThrow(() -> new AgencyException(AGENCY_NOT_INITIALIZED_EXCEPTION));
    }

    public boolean isInitialized(){
        return repository.findAll().stream().findFirst().isPresent();
    }

    private static void validatePeselIfExists(AgencyDetailsDto agencyDetailsDto) {
        if(hasText(agencyDetailsDto.pesel())){
            if(!new PeselValidator().validate(agencyDetailsDto.pesel())) {
                throw new AgencyException(PESEL_INVALID_EXCEPTION);
            }
        }
    }
}
