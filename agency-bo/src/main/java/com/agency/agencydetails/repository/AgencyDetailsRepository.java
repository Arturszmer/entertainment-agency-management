package com.agency.agencydetails.repository;

import com.agency.agencydetails.model.AgencyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyDetailsRepository extends JpaRepository<AgencyDetails, Long> {
}
