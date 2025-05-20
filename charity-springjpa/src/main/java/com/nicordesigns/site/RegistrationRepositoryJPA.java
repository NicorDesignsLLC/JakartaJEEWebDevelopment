package com.nicordesigns.site;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("registrationRepositoryJPA")
public interface RegistrationRepositoryJPA extends JpaRepository<Registration, Long>, RegistrationRepository {
    
	@Override
    default List<Registration> getAll() { return findAll(); }
    @Override
    default Optional<Registration> get(long id) { return (Optional<Registration>) findById(id); }
    @Override
    default void add(Registration registration) { save(registration); }
    @Override
    default void update(Registration registration) { save(registration); }
    @Override
    default void remove(long id) { deleteById(id); }
    
    @Query("SELECT r FROM Registration r LEFT JOIN FETCH r.fileAttachments WHERE r.id = :id")
    Optional<Registration> findByIdWithAttachments(@Param("id") Long id);
    
    @Override
    default Optional<Registration> findById(Long id) {
        return findByIdWithAttachments(id);
    }

}