package com.agency.project.repository;

import com.agency.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByContractNumber(String contractNumber);
    Optional<Project> findProjectByPublicId(UUID publicId);
    @Query("SELECT COUNT(p) FROM Project p WHERE YEAR(p.signDate) = :year")
    int getNumberOfContractsByYear(int year);
}
