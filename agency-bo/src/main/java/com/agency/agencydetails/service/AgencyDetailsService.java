package com.agency.agencydetails.service;

import com.agency.agencydetails.model.AgencyDetails;
import com.agency.agencydetails.repository.AgencyDetailsRepository;
import com.agency.common.validators.PeselValidator;
import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.exception.AgencyException;
import com.agency.user.assembler.AddressAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.agency.exception.AgencyErrorResult.*;
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
            return toDto(agencyDetails);
        } else {
            throw new AgencyException(ONLY_ONE_AGENCY_CAN_EXIST);
        }
    }

    public AgencyDetailsDto updateAgencyDetails(AgencyDetailsDto agencyDetailsDto) {
        validatePeselIfExists(agencyDetailsDto);
        Optional<AgencyDetails> agencyDetailsOpt = repository.findAll().stream().findFirst();
        return agencyDetailsOpt.map(agencyDetails -> {
            agencyDetails.update(agencyDetailsDto);
            return toDto(repository.save(agencyDetails));
        }).orElseThrow(() -> new AgencyException(AGENCY_NOT_INITIALIZED_EXCEPTION));
    }

    private static void validatePeselIfExists(AgencyDetailsDto agencyDetailsDto) {
        if(hasText(agencyDetailsDto.pesel())){
            if(new PeselValidator().validate(agencyDetailsDto.pesel())) {
                throw new AgencyException(PESEL_INVALID_EXCEPTION);
            }
        }
    }

    private AgencyDetailsDto toDto(AgencyDetails agencyDetails) {
        return new AgencyDetailsDto(agencyDetails.getName(), agencyDetails.getNip(), agencyDetails.getRegon(), agencyDetails.getPesel(),
                agencyDetails.getKrsNumber(), AddressAssembler.toDto(agencyDetails.getAddress()));
    }
}
