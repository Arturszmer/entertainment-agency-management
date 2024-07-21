package com.agency.organizer.repository;

import com.agency.organizer.model.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findOrganizerByPublicId(UUID publicId);
    List<Organizer> findAllByUsername(String username);

    @Query("SELECT o FROM Organizer o WHERE o.organizerName LIKE CONCAT('%', :organizerName, '%')")
    Page<Organizer> findAllByOrganizerName(@Param("organizerName") String organizerName, Pageable pageable);
}
