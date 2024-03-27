package com.nicordesigns.site;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryRegistrationRepository implements RegistrationRepository
{
    private volatile long REGISTRATION_ID_SEQUENCE = 1L;

    private final Map<Long, Registration> registrationDatabase = new LinkedHashMap<>();

    @Override
    public List<Registration> getAll()
    {
        return new ArrayList<>(this.registrationDatabase.values());
    }

    @Override
    public Registration get(long id)
    {
        return this.registrationDatabase.get(id);
    }

    @Override
    public void add(Registration registration)
    {
        registration.setId(this.getNextRegistrationId());
        this.registrationDatabase.put(registration.getId(), registration);
    }

    @Override
    public void update(Registration Registration)
    {
        this.registrationDatabase.put(Registration.getId(), Registration);
    }

    private synchronized long getNextRegistrationId()
    {
        return this.REGISTRATION_ID_SEQUENCE++;
    }
}
