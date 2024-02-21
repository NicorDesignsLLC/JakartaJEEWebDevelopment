package com.nicordesigns.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("registration")
public class RegistrationController
{
    private static final Logger log = LogManager.getLogger();

    private volatile long CHARITY_ID_SEQUENCE = 1;

    private Map<Long, Registration> registrationDatabase = new LinkedHashMap<>();

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(Map<String, Object> model)
    {
        log.debug("Listing registrations.");
        model.put("registrationDatabase", this.registrationDatabase);

        return "charity-springmvc/list";
    }

    @RequestMapping(value = "view/{registrationId}", method = RequestMethod.GET)
    public ModelAndView view(Map<String, Object> model,
                             @PathVariable("registrationId") long registrationId)
    {
        Registration registration = this.registrationDatabase.get(registrationId);
        if(registration == null)
            return this.getListRedirectModelAndView();
        model.put("registrationId", Long.toString(registrationId));
        model.put("registration", registration);
        return new ModelAndView("charity-springmvc/view");
    }

    @RequestMapping(
            value = "/{registrationId}/attachment/{attachment:.+}",
            method = RequestMethod.GET
    )
    public View download(@PathVariable("registrationId") long registrationId,
                         @PathVariable("attachment") String name)
    {
        Registration registration = this.registrationDatabase.get(registrationId);
        if(registration == null)
            return this.getListRedirectView();

        FileAttachment attachment = registration.getAttachment(name);
        if(attachment == null)
        {
            log.info("Requested attachment {} not found on registration {}.", name, registration);
            return this.getListRedirectView();
        }

        return new DownloadingView(attachment.getName(),
                attachment.getContents());
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Map<String, Object> model)
    {
        model.put("registrationForm", new Form());
        return "charity-springmvc/add";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public View create(HttpSession session, Form form) throws IOException
    {
        Registration registration = new Registration();
        //registration.setId(this.getNextregistrationId());
        registration.setUserName((String)session.getAttribute("username"));
        registration.setSubject(form.getSubject());
        registration.setBody(form.getBody());
        registration.setDateCreated(Instant.now());

        for(MultipartFile filePart : form.getAttachments())
        {
            log.debug("Processing attachment for new registration.");
            FileAttachment attachment = new FileAttachment();
            attachment.setName(filePart.getOriginalFilename());
            //attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if((attachment.getName() != null && attachment.getName().length() > 0) ||
                    (attachment.getContents() != null && attachment.getContents().length > 0))
                registration.addAttachment(attachment);
        }

        long nextId = getNextregistrationId();
        this.registrationDatabase.put(nextId , registration);

        return new RedirectView("/charity-springmvc/view/" + nextId, true, false);
    }

    private ModelAndView getListRedirectModelAndView()
    {
        return new ModelAndView(this.getListRedirectView());
    }

    private View getListRedirectView()
    {
        return new RedirectView("/charity-springmvc/list", true, false);
    }

    private synchronized long getNextregistrationId()
    {
        return this.CHARITY_ID_SEQUENCE++;
    }

    public static class Form
    {
        private String subject;
        private String body;
        private List<MultipartFile> attachments;

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

        public List<MultipartFile> getAttachments()
        {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments)
        {
            this.attachments = attachments;
        }
    }
}
