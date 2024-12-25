package com.agency.project.repository;

import com.agency.dict.project.ProjectStatus;
import com.agency.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByContractNumber(String contractNumber);

    Optional<Project> findProjectByPublicId(UUID publicId);

    List<Project> findAllByStatusNot(ProjectStatus status);

    @Query("SELECT COUNT(p) FROM Project p WHERE YEAR(p.signDate) = :year")
    int getNumberOfContractsByYear(@Param("year") int year);

}
