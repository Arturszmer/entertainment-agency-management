package com.agency.contractmanagement.service;

import com.agency.contractmanagement.model.QContractWork;
import com.agency.dto.contractwork.ContractWorkSearchRequest;
import com.agency.search.AbstractSortableConfig;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
class ContractWorkSearchFilter extends AbstractSortableConfig {

    private static final QContractWork CONTRACT_WORK = QContractWork.contractWork;

    private String contractNumber;
    private LocalDate signDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractorFirstName;
    private String contractorLastName;
    private String contractorPesel;
    private String projectNumber;

    @Override
    public Predicate getPredicate() {
        BooleanBuilder bb = new BooleanBuilder();

        ofNullable(contractNumber)
                .ifPresent(contractNumber -> bb.and(CONTRACT_WORK.contractNumber.contains(contractNumber)));
        ofNullable(signDate)
                .ifPresent(signDate -> bb.and(CONTRACT_WORK.signDate.eq(signDate)));
        ofNullable(startDate)
                .ifPresent(startDate -> bb.and(CONTRACT_WORK.startDate.eq(startDate)));
        ofNullable(endDate)
                .ifPresent(endDate -> bb.and(CONTRACT_WORK.endDate.eq(endDate)));
        ofNullable(contractorFirstName)
                .ifPresent(contractorFirstName -> bb.and(CONTRACT_WORK.contractor.firstName.contains(contractorFirstName)));
        ofNullable(contractorLastName)
                .ifPresent(contractorLastName -> bb.and(CONTRACT_WORK.contractor.lastName.contains(contractorLastName)));
        ofNullable(contractorPesel)
                .ifPresent(contractorPesel -> bb.and(CONTRACT_WORK.contractor.pesel.eq(contractorPesel)));
        ofNullable(projectNumber)
                .ifPresent(projectNumber -> bb.and(CONTRACT_WORK.projectNumber.contains(projectNumber)));
        return bb;
    }

    public static ContractWorkSearchFilter of(ContractWorkSearchRequest request) {
        ContractWorkSearchFilter filter = ContractWorkSearchFilter.builder()
                .contractNumber(request.contractNumber())
                .signDate(request.signDate())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .contractorFirstName(request.contractorFirstName())
                .contractorLastName(request.contractorLastName())
                .contractorPesel(request.contractorPesel())
                .projectNumber(request.projectNumber())
                .build();
        filter.setPage(request.page());
        filter.setSize(request.size());
        filter.setSort(request.sort());
        filter.setOrder(request.order());
        return filter;
    }
}
