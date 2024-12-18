package com.nicordesigns.site;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nicordesigns.site.config.annotation.RestEndpoint;

@RestEndpoint
public class RegistrationRestEndpoint {
	@Inject
	RegistrationService registrationService;

	@RequestMapping(value = "registration", method = RequestMethod.OPTIONS, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Void> discover() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,HEAD,GET,POST,DELETE");
		return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "registration/{id}", method = RequestMethod.OPTIONS, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Void> discover(@PathVariable("id") long id) throws ResourceNotFoundException {
		if (this.registrationService.getRegistration(id) == null)
			throw new ResourceNotFoundException();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Allow", "OPTIONS,HEAD,GET,DELETE");
		return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "registration", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public RegistrationWebServiceList read() {
		RegistrationWebServiceList list = new RegistrationWebServiceList();
		list.setValue(this.registrationService.getAllRegistrations());
		return list;
	}

	@RequestMapping(value = "registration/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Registration read(@PathVariable("id") long id) throws ResourceNotFoundException {
		Registration registration = this.registrationService.getRegistration(id);
		if (registration == null)
			throw new ResourceNotFoundException();
		return registration;
	}

	@RequestMapping(value = "registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Registration> create(@RequestBody RegistrationForm form) {
	    Registration registration = new Registration();
	    registration.setUserName("WebServiceAnonymous");
	    registration.setSubject(form.getSubject());
	    registration.setBody(form.getBody());

	    // Save the registration
	    this.registrationService.save(registration);

	    // Return the response with the created registration
	    String uri = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/registration/{id}")
	            .buildAndExpand(registration.getId()).toString();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Location", uri);

	    return new ResponseEntity<>(registration, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "registration/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") long id) throws ResourceNotFoundException {
		if (this.registrationService.getRegistration(id) == null)
			throw new ResourceNotFoundException();
		this.registrationService.deleteRegistration(id);
	}

	@XmlRootElement(name = "registration")
	public static class RegistrationForm {
		private String subject;
		private String body;

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

	}
}
