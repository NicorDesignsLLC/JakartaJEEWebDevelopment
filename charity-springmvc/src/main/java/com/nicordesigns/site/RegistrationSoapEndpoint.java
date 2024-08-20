package com.nicordesigns.site;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.Namespace;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.server.endpoint.annotation.XPathParam;

import com.nicordesigns.ws.CharityRegistrationRequest.CharityRegistrationInfo.FileAttachments.Attachment;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Endpoint
public class RegistrationSoapEndpoint
{
    private static final String NAMESPACE = "http://example.com/xmlns/support";

    @Inject RegistrationService RegistrationService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "RegistrationsRequest")
    @ResponsePayload
    public RegistrationWebServiceList read()
    {
        RegistrationWebServiceList list = new RegistrationWebServiceList();
        list.setValue(this.RegistrationService.getAllRegistrations());
        return list;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "RegistrationRequest")
    @Namespace(uri = NAMESPACE, prefix = "s")
    @ResponsePayload
    public Registration read(@XPathParam("/s:RegistrationRequest/id") long id)
    {
        return this.RegistrationService.getRegistration(id);
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "createRegistration")
    @ResponsePayload
    public Registration create(@RequestPayload CreateRegistration form)
    {
        Registration Registration = new Registration();
        Registration.setUserName("WebServiceAnonymous");
        Registration.setSubject(form.getSubject());
        Registration.setBody(form.getBody());
        if(form.getAttachments() != null)
            Registration.setAttachments(form.getAttachments());

        this.RegistrationService.save(Registration);

        return Registration;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "deleteRegistration")
    @Namespace(uri = NAMESPACE, prefix = "s")
    public void delete(@XPathParam("/s:deleteRegistration/id") long id)
    {
        this.RegistrationService.deleteRegistration(id);
    }

    @XmlRootElement(namespace = NAMESPACE, name = "createRegistration")
    public static class CreateRegistration
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
