package com.agency.user.repository;

import com.agency.auth.RoleType;
import com.agency.user.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findUserProfileByUsername(String username);

    Optional<UserProfile> findUserProfileByEmail(String email);

    @Query("select u from UserProfile u join Role r on u.id = r.id where r.name = :roleType")
    Optional<UserProfile> findByRoleType(@Param("roleType") RoleType roleType);

}
