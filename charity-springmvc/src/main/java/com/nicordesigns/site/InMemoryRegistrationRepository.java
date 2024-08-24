package com.nicordesigns.site;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRegistrationRepository implements RegistrationRepository {
    private final AtomicLong REGISTRATION_ID_SEQUENCE = new AtomicLong(1);

    private final Map<Long, Registration> registrationDatabase = new ConcurrentHashMap<>();

    @Override
    public List<Registration> getAll() {
        return new ArrayList<>(this.registrationDatabase.values());
    }

    @Override
    public Registration get(long id) {
        return this.registrationDatabase.get(id);
    }

    @Override
    public void add(Registration registration) {
        registration.setId(this.getNextRegistrationId());
        this.registrationDatabase.put(registration.getId(), registration);
    }

    @Override
    public void update(Registration registration) {
        this.registrationDatabase.put(registration.getId(), registration);
    }

    private long getNextRegistrationId() {
        return this.REGISTRATION_ID_SEQUENCE.getAndIncrement();
    }

	@Override
	public void remove(long id) {
		this.registrationDatabase.remove(id);
		
	}
}
