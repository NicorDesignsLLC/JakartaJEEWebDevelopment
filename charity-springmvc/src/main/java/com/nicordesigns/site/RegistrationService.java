package com.nicordesigns.site;

import java.util.List;

public interface RegistrationService
{
    List<Registration> getAllRegistrations();
    Registration getRegistration(long id);
    void save(Registration registration);
}
