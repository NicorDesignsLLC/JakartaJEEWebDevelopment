package com.nicordesigns.site;

import com.nicordesigns.config.annotation.RestEndpoint;
import com.nicordesigns.exception.ResourceNotFoundException;
import com.nicordesigns.ws.CharityRegistrationRequest.CharityRegistrationInfo.FileAttachments.Attachment;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@RestEndpoint
public class RegistrationRestEndpoint
{
    @Inject RegistrationService RegistrationService;

    @RequestMapping(value = "Registration", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> discover()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,POST");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "Registration/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> discover(@PathVariable("id") long id)
    {
        if(this.RegistrationService.getRegistration(id) == null)
            throw new ResourceNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "OPTIONS,HEAD,GET,DELETE");
        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "Registration", method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public RegistrationWebServiceList read()
    {
        RegistrationWebServiceList list = new RegistrationWebServiceList();
        list.setValue(this.RegistrationService.getAllRegistrations());
        return list;
    }

    @RequestMapping(value = "Registration/{id}", method = RequestMethod.GET)
    @ResponseBody @ResponseStatus(HttpStatus.OK)
    public Registration read(@PathVariable("id") long id)
    {
        Registration Registration = this.RegistrationService.getRegistration(id);
        if(Registration == null)
            throw new ResourceNotFoundException();
        return Registration;
    }
    
    
    @RequestMapping(value = "Registration", method = RequestMethod.POST)
    public ResponseEntity<Registration> create(@RequestBody RegistrationForm form) {
        // Initialize a new Registration object
        Registration registration = new Registration();
        
        // Set fields from the form
        registration.setUserName("WebServiceAnonymous"); // Or retrieve from an authenticated user
        registration.setSubject(form.getSubject());
        registration.setBody(form.getBody());
        registration.setAttachments(form.getAttachments());

        // Save the registration using the service
        this.RegistrationService.save(registration);

        // Create a URI for the newly created registration
        String uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(registration.getId())
                    .toUriString();
        
        // Set the Location header with the newly created resource URI
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        // Return a response with the created registration and HTTP 201 status
        return new ResponseEntity<>(registration, headers, HttpStatus.CREATED);
    }


//    @RequestMapping(value = "Registration", method = RequestMethod.POST)
//    public ResponseEntity<Registration> create(@RequestBody RegistrationForm form)
//    {
//        Registration Registration = new Registration();
//        Registration.setUserName("WebServiceAnonymous");
//        Registration.setSubject(form.getSubject());
//        Registration.setBody(form.getBody());
//        Registration.setAttachments(form.getAttachments());
//
//        this.RegistrationService.save(Registration);
//
//        String uri = ServletUriComponentsBuilder.fromCurrentServletMapping()
//                .path("/Registration/{id}").buildAndExpand(Registration.getId()).toString();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", uri);
//
//        return new ResponseEntity<>(Registration, headers, HttpStatus.CREATED);
//    }

    @RequestMapping(value = "Registration/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id)
    {
        if(this.RegistrationService.getRegistration(id) == null)
            throw new ResourceNotFoundException();
        this.RegistrationService.deleteRegistration(id);
    }

    @XmlRootElement(name = "Registration")
    public static class RegistrationForm
    {
        private String subject;
        private String body;
        private List<Attachment> attachments;

        public String getSubject()
        {
            return subject;
        }

        public void setSubject(String subject)
        {
            this.subject = subject;
        }

        public String getBody()
        {
            return body;
        }

        public void setBody(String body)
        {
            this.body = body;
        }

        @XmlElement(name = "attachment")
        public List<Attachment> getAttachments()
        {
            return attachments;
        }

        public void setAttachments(List<Attachment> attachments)
        {
            this.attachments = attachments;
        }
    }
}
