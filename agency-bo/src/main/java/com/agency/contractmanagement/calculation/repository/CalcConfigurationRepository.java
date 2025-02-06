package com.agency.contractmanagement.calculation.repository;

import com.agency.contractmanagement.calculation.model.TaxConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CalcConfigurationRepository extends JpaRepository<TaxConfiguration, Long> {

    @Query("SELECT c FROM TaxConfiguration c WHERE :billDate BETWEEN c.validFrom AND c.validTo")
    Optional<TaxConfiguration> findConfigurationByDate(@Param("billDate") LocalDate billDate);
}
