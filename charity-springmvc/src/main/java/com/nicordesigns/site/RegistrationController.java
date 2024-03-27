package com.nicordesigns.site;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("registration")
public class RegistrationController {
	private static final Logger log = LogManager.getLogger();

	@Inject
	RegistrationService registrationService;

	@GetMapping(value = { "", "list" })
	public String list(Map<String, Object> model) {
		log.debug("Listing registrations.");
		model.put("registrations", this.registrationService.getAllRegistrations());

		return "registration/list";
	}

	@GetMapping(value = "view/{registrationId}")
	public ModelAndView view(Map<String, Object> model, @PathVariable("registrationId") long registrationId) {
		Registration registration = this.registrationService.getRegistration(registrationId);
		if (registration == null)
			return this.getListRedirectModelAndView();
		model.put("registrationId", Long.toString(registrationId));
		model.put("registration", registration);
		return new ModelAndView("registration/view");
	}

	@GetMapping(value = "/{registrationId}/attachment/{attachment:.+}")
    public View download(@PathVariable("registrationId") long registrationId,
                         @PathVariable("attachment") String name)
    {
        Registration registration = this.registrationService.getRegistration(registrationId);
        if(registration == null)
            return this.getListRedirectView();

        FileAttachment attachment = registration.getAttachment(name);
        if(attachment == null)
        {
            log.info("Requested attachment {} not found on Registration {}.", name, registration);
            return this.getListRedirectView();
        }

        return new DownloadingView(attachment.getName(),
                attachment.getMimeContentType(), attachment.getContents());
    }

	@GetMapping(value = "create")
	public String create(Map<String, Object> model) {
		model.put("registrationForm", new Form());
		return "registration/add";
	}

	@PostMapping(value = "create")
    public View create(Principal principal, Form form) throws IOException
    {
        Registration registration = new Registration();
        registration.setUserName(principal.getName());
        registration.setSubject(form.getSubject());
        registration.setBody(form.getBody());

        for(MultipartFile filePart : form.getAttachments())
        {
            log.debug("Processing attachment for new Registration.");
            FileAttachment attachment = new FileAttachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            if((attachment.getName() != null && attachment.getName().length() > 0) ||
                    (attachment.getContents() != null && attachment.getContents().length > 0))
            	registration.addAttachment(attachment);
        }

        this.registrationService.save(registration);

        return new RedirectView("/registration/view/" + registration.getId(), true, false);
    }
	 
	private ModelAndView getListRedirectModelAndView() {
		return new ModelAndView(this.getListRedirectView());
	}

	private View getListRedirectView() {
		return new RedirectView("/registration/list", true, false);
	}

	public static class Form {
		private long subject;
		private String body;
		private List<MultipartFile> attachments;

		public long getSubject() {
			return subject;
		}

		public void setSubject(long subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public List<MultipartFile> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<MultipartFile> attachments) {
			this.attachments = attachments;
		}
	}
}
