package com.nicordesigns.site;

import java.util.List;

public interface RegistrationRepository
{
    List<Registration> getAll();
    Registration get(long id);
    void add(Registration registration);
    void update(Registration registration);
	void remove(long id);
}
