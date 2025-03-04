package com.nicordesigns.repositories;

import com.nicordesigns.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    // Find Actor by name (ensures uniqueness if ActorName is unique)
    Optional<Actor> findByActorName(String actorName);
}
