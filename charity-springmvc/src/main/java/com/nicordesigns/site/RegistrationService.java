package com.nicordesigns.site;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationService {
	@NotNull
	List<Registration> getAllRegistrations();

	Registration getRegistration(
			@Min(value = 1L, message = "{validate.registrationService.getRetrigistration.id}") long id);

	void save(@NotNull(message = "{validate.registrationService.save.registration}") @Valid Registration registration);
}
