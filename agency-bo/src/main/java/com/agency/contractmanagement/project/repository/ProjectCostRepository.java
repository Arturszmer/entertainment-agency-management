package com.agency.contractmanagement.project.repository;

import com.agency.contractmanagement.project.model.ProjectCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectCostRepository extends JpaRepository<ProjectCost, Long> {

    List<ProjectCost> findProjectCostByCostReference(String costReference);

    List<ProjectCost> findProjectCostsByProjectPublicId(UUID projectPublicId);

    Optional<ProjectCost> findProjectCostByPublicId(UUID publicId);

    void deleteProjectCostByPublicId(UUID publicId);
}
