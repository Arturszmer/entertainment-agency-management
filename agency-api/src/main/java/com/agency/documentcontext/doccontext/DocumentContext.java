package com.agency.documentcontext.doccontext;

import com.agency.dto.agencydetails.AgencyDetailsDto;
import com.agency.dto.contractor.ContractorDto;
import com.agency.dto.contractwork.ContractWorkDto;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DocumentContext {

    private AgencyDetailsDto agencyDetailsDto;
    private ContractorDto contractor;
    private ContractWorkDto contractWork;
    private Map<String, Object> placeholders = new HashMap<>();
    @NonNull
    private DocContextType docContextType;

}
