package com.nicordesigns.site;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.nicordesigns.site.config.annotation.WebController;

@WebController
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
	public View download(@PathVariable("registrationId") long registrationId, @PathVariable("attachment") String name) {
		Registration registration = this.registrationService.getRegistration(registrationId);
		if (registration == null)
			return this.getListRedirectView();

		FileAttachment attachment = registration.getAttachment(name);
		if (attachment == null) {
			log.info("Requested attachment {} not found on Registration {}.", name, registration);
			return this.getListRedirectView();
		}

		return new DownloadingView(attachment.getName(), attachment.getMimeContentType(), attachment.getContents());
	}

	@GetMapping(value = "create")
	public String create(Map<String, Object> model) {
		model.put("registrationForm", new RegistrationForm());
		return "registration/add";
	}
	
	@PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ModelAndView create(Principal principal, @Valid RegistrationForm form, BindingResult bindingResult) throws IOException {
	    ModelAndView modelAndView = new ModelAndView("registration/add");

	    if (bindingResult.hasErrors()) {
	        bindingResult.getAllErrors().forEach(error -> log.error(error.toString()));
	        modelAndView.addObject("registrationForm", form);
	        return modelAndView;
	    }

	    Registration registration = new Registration();
	    registration.setUserName(principal.getName());
	    registration.setSubject(form.getSubject());
	    registration.setBody(form.getBody());

	    processAttachments(form, registration);

	    this.registrationService.save(registration);

	    modelAndView.setViewName("redirect:/registration/view/" + registration.getId());
	    return modelAndView;
	}

	private void processAttachments(RegistrationForm form, Registration registration) throws IOException {
	    for (MultipartFile filePart : form.getAttachments()) {
	        log.debug("Processing attachment for new Registration.");
	        FileAttachment attachment = new FileAttachment();
	        attachment.setName(filePart.getOriginalFilename());
	        attachment.setMimeContentType(filePart.getContentType());
	        attachment.setContents(filePart.getBytes());
	        if ((attachment.getName() != null && attachment.getName().length() > 0)
	                || (attachment.getContents() != null && attachment.getContents().length > 0)) {
	            registration.addAttachment(attachment);
	        }
	    }
	}

	private ModelAndView getListRedirectModelAndView() {
		return new ModelAndView(this.getListRedirectView());
	}

	private View getListRedirectView() {
		return new RedirectView("/registration/list", true, false);
	}

	@ModelAttribute("registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

	public static class RegistrationForm {

		@NotBlank(message = "{validate.registration.subject}")
		private String subject;

		@NotBlank(message = "{validate.registration.body}")
		private String body;

		@NotNull(message = "{validate.registration.attachments}")
		private List<MultipartFile> attachments;

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

		public List<MultipartFile> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<MultipartFile> attachments) {
			this.attachments = attachments;
		}
	}
}
