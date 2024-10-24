package com.agency.contractor.service;

import com.agency.contractor.model.QContractor;
import com.agency.dto.contractor.ContractorSearchRequest;
import com.agency.search.AbstractSortableConfig;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.agency.contractor.model.QContractor.contractor;
import static java.util.Optional.ofNullable;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ContractorSearchFilter extends AbstractSortableConfig {

    private static final QContractor CONTRACTOR = contractor;

    private String firstName;
    private String lastName;
    private String pesel;
    private String email;


    @Override
    public Predicate getPredicate() {
        BooleanBuilder bb = new BooleanBuilder();

        ofNullable(firstName)
                .ifPresent(firstName -> bb.and(contractor.firstName.contains(firstName)));
        ofNullable(lastName)
                .ifPresent(lastName -> bb.and(contractor.lastName.contains(lastName)));
        ofNullable(pesel)
                .ifPresent(pesel -> bb.and(contractor.pesel.contains(pesel)));
        ofNullable(email)
                .ifPresent(emailAddress -> bb.and(contractor.email.contains(emailAddress)));
        return bb;
    }

    public static ContractorSearchFilter of(ContractorSearchRequest request) {
        ContractorSearchFilter contractorSearchFilter = ContractorSearchFilter.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .pesel(request.pesel())
                .email(request.email())
                .build();
        contractorSearchFilter.setPage(request.page());
        contractorSearchFilter.setSize(request.size());
        contractorSearchFilter.setSort(request.sort() != null ? request.sort() : "lastName");
        contractorSearchFilter.setOrder(request.order());
        return contractorSearchFilter;
    }
}
