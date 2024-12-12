package com.agency.project.repository;

import com.agency.project.model.ProjectCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectCostRepository extends JpaRepository<ProjectCost, Long> {

    List<ProjectCost> findProjectCostByCostReference(String costReference);
    void deleteProjectCostByPublicId(UUID publicId);
}
