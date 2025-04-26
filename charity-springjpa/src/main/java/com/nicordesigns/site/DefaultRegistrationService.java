package com.nicordesigns.site;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

@Service
public class DefaultRegistrationService implements RegistrationService
{
    @Inject RegistrationRepository registrationRepository;

    @Override
    public List<Registration> getAllRegistrations()
    {
        return this.registrationRepository.getAll();
    }

    @Override
    public Registration getRegistration(long id)
    {
        return this.registrationRepository.get(id);
    }

    @Override
    public void save(Registration registration)
    {
        if(registration.getId() < 1)
        {
        	registration.setDateCreated(Instant.now());
            this.registrationRepository.add(registration);
        }
        else
            this.registrationRepository.update(registration);
    }

	@Override
	public void deleteRegistration(long id) {
		this.registrationRepository.remove(id);
		
	}
}
