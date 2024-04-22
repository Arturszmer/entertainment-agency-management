package com.agency.contractmanagement.model.contract;

import com.agency.contractmanagement.model.contractor.Contractor;
import com.agency.project.model.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "contract_work")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractWork extends AbstractContract {

    @Setter
    private boolean withCopyrights;
    @ManyToOne
    @NonNull
    private Contractor contractor;
    @ManyToOne
    private Project project;
}
