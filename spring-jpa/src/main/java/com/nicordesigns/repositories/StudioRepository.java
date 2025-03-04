package com.nicordesigns.repositories;

import com.nicordesigns.entities.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    // Find Studio by its name (ensures uniqueness if StudioName is unique)
    Optional<Studio> findByStudioName(String studioName);
}
