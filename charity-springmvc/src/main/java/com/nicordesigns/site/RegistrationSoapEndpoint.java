package com.nicordesigns.site;

import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;

@Endpoint
public class RegistrationSoapEndpoint
{
    private static final String NAMESPACE = "http://nicordesigns.com/xmlns/charityregistration";

    @Inject RegistrationService registrationService;

    @PayloadRoot(namespace = "http://nicordesigns.com/xmlns/charityregistration", localPart = "getAllCharityRegistrationsRequest")
    @ResponsePayload
    public RegistrationWebServiceList read() {
        RegistrationWebServiceList list = new RegistrationWebServiceList();
        list.setValue(this.registrationService.getAllRegistrations());
        return list;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "CharityRegistrationRequest")
    @Namespace(uri = NAMESPACE, prefix = "s")
    @ResponsePayload
    public Registration read(@XPathParam("/s:charityRegistrationRequest/id") long id)
    {
        return this.registrationService.getRegistration(id);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createRegistration")
    @ResponsePayload
    public Registration create(@RequestPayload RegistrationForm form)
    {
        Registration charityRegistration = new Registration();
        charityRegistration.setUserName("WebServiceAnonymous");
        charityRegistration.setSubject(form.getSubject());
        charityRegistration.setBody(form.getBody());
        charityRegistration.setFileAttachments(form.getAttachments());

        this.registrationService.save(charityRegistration);

        return charityRegistration;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRegistration")
    @Namespace(uri = NAMESPACE, prefix = "s")
    public void delete(@XPathParam("/s:deleteCharityRegistration/id") long id) throws ResourceNotFoundException
    {
    	if(this.registrationService.getRegistration(id) == null)
            throw new ResourceNotFoundException();
        this.registrationService.deleteRegistration(id);
    }

    @XmlType(name = "SoapRegistrationForm", namespace = "http://www.nicordesigns.com/soap")
    public static class RegistrationForm
    {
        private String subject;
        private String body;
        private List<FileAttachment> fileAttachments;

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
        public List<FileAttachment> getAttachments()
        {
            return fileAttachments;
        }

        public void setAttachments(List<FileAttachment> attachments)
        {
            this.fileAttachments = attachments;
        }
    }
}
