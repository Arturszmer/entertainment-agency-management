package com.agency.contractmanagement.model.contract;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.dto.contractwork.ContractType;
import com.agency.dto.contractwork.ContractWorkCreateDto;
import com.agency.dto.contractwork.ContractWorkStatus;
import com.agency.project.model.Project;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.agency.dto.contractwork.ContractType.CONTRACT_WORK;
import static com.agency.dto.contractwork.ContractWorkStatus.DRAFT;

@Entity
@Table(name = "contract_work")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ContractWork extends AbstractContract {

    @Setter
    private boolean withCopyrights;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NonNull
    private Contractor contractor;
    @ManyToOne
    private Project project;
    @Enumerated(EnumType.STRING)
    private ContractWorkStatus status;

    public ContractWork(String contractNumber,
                        LocalDate signDate,
                        LocalDate startDate,
                        LocalDate endDate,
                        String subjectOfTheContract,
                        BigDecimal salary,
                        String additionalInformation,
                        ContractType contractType,
                        boolean withCopyrights,
                        @NonNull Contractor contractor,
                           Project project,
                           ContractWorkStatus status) {
        super(contractNumber, signDate, startDate, endDate, subjectOfTheContract, salary, additionalInformation, contractType);
        this.withCopyrights = withCopyrights;
        this.contractor = contractor;
        this.project = project;
        this.status = status;
    }

    public static ContractWork create(String contractNumber, ContractWorkCreateDto createDto, Contractor contractor, Project project){
        return new ContractWork(
                contractNumber,
                createDto.contractDetailsDto().signDate(),
                createDto.contractDetailsDto().startDate(),
                createDto.contractDetailsDto().endDate(),
                createDto.contractDetailsDto().subjectOfTheContract(),
                createDto.contractDetailsDto().salary(),
                createDto.contractDetailsDto().additionalInformation(),
                CONTRACT_WORK,
                createDto.withCopyrights(),
                contractor,
                project,
                DRAFT
        );
    }
}
