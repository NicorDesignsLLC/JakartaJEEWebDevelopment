package com.nicordesigns.site;

import java.util.List;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class RegistrationSoapEndpoint {
    private static final String NAMESPACE = "http://nicordesigns.com/xmlns/registration";  // Updated namespace

    @Inject 
    RegistrationService registrationService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAllRegistrationsRequest")
    @ResponsePayload
    public RegistrationWebServiceList getAllRegistrations() {
        RegistrationWebServiceList list = new RegistrationWebServiceList();
        list.setValue(this.registrationService.getAllRegistrations());
        return list;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "RegistrationRequest")
    @ResponsePayload
    public Registration getRegistrationById(@RequestPayload Registration request) {
        long id = request.getId();
        return this.registrationService.getRegistration(id);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createRegistration")
    @ResponsePayload
    public Registration createRegistration(@RequestPayload RegistrationForm form) {
        Registration charityRegistration = new Registration();
        charityRegistration.setUserName("WebServiceAnonymous");
        charityRegistration.setSubject(form.getSubject());
        charityRegistration.setBody(form.getBody());
        charityRegistration.setFileAttachments(form.getAttachments());

        this.registrationService.save(charityRegistration);
        return charityRegistration;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRegistration")
    @ResponsePayload
    public void deleteRegistration(@RequestPayload Registration request) throws ResourceNotFoundException {
        long id = request.getId();
        if (this.registrationService.getRegistration(id) == null) {
            throw new ResourceNotFoundException();
        }
        this.registrationService.deleteRegistration(id);
    }

    // RegistrationForm class
    @XmlType(name = "SoapRegistrationForm", namespace = "http://www.nicordesigns.com/soap")
    public static class RegistrationForm {
        private String subject;
        private String body;
        private List<FileAttachment> fileAttachments;

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

        @XmlElement(name = "attachment")
        public List<FileAttachment> getAttachments() {
            return fileAttachments;
        }

        public void setAttachments(List<FileAttachment> attachments) {
            this.fileAttachments = attachments;
        }
    }
}
