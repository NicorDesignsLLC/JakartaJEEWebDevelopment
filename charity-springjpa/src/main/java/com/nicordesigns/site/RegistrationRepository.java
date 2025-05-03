package com.nicordesigns.site;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository
{
    List<Registration> getAll();
    Optional<Registration> get(long id);
    void add(Registration registration);
    void update(Registration registration);
	void remove(long id);
	Optional<Registration> findById(long registrationId);
}

