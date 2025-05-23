package com.nicordesigns.site;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultRegistrationService implements RegistrationService {
    
	@Inject
    @Qualifier("registrationRepositoryJPA")
	RegistrationRepositoryJPA registrationRepositoryJPA;
    
	
	@Transactional(readOnly = true)
    public Registration getRegistration(long registrationId) {
        return registrationRepositoryJPA.findByIdWithAttachments(registrationId).orElse(null);
    }
	
    @Override
    public List<Registration> getAllRegistrations() {
        return this.registrationRepositoryJPA.getAll();
    }

    @Override
    public void save(Registration registration) {
        if (registration.getId() < 1) {
            registration.setDateCreated(Instant.now());
            this.registrationRepositoryJPA.add(registration);
        } else {
            this.registrationRepositoryJPA.update(registration);
        }
    }

    @Override
    public void deleteRegistration(long id) {
        this.registrationRepositoryJPA.remove(id);
    }
}