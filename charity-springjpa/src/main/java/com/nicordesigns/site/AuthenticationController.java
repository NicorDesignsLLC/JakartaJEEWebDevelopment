package com.nicordesigns.site;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.nicordesigns.site.config.annotation.WebController;

@WebController
public class AuthenticationController {
    private static final Logger log = LogManager.getLogger();

    @Inject
    AuthenticationService authenticationService;

    @RequestMapping("logout")
    public View logout(HttpServletRequest request, HttpSession session) {
        if (log.isDebugEnabled() && request.getUserPrincipal() != null)
            log.debug("User {} logged out.", request.getUserPrincipal().getName());
        session.invalidate();

        return new RedirectView("/login", true, false);
    }

    @GetMapping(value = "login")
    public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, Map<String, Object> model, HttpSession session) {
        log.info("login GET");
        if (UserAdminPrincipal.getPrincipal(session) != null)
            return this.getRegistrationRedirect();

        model.put("loginFailed", false);

        return new ModelAndView("login");
    }

    @PostMapping(value = "login")
    public ModelAndView login(@ModelAttribute("loginForm") @Valid LoginForm form, Errors errors, Map<String, Object> model, HttpSession session, HttpServletRequest request) {
        log.info("login POST");

        if (UserAdminPrincipal.getPrincipal(session) != null)
            return this.getRegistrationRedirect();

        if (errors.hasErrors()) {
            form.setPassword(null);
            return new ModelAndView("login");
        }

        Principal principal;
        try {
            principal = this.authenticationService.authenticate(form.getUsername(), form.getPassword());
        } catch (ConstraintViolationException e) {
            form.setPassword(null);
            model.put("validationErrors", e.getConstraintViolations());
            return new ModelAndView("login");
        }

        if (principal == null) {
            form.setPassword(null);
            model.put("loginFailed", true);
            return new ModelAndView("login");
        }

        UserAdminPrincipal.setPrincipal(session, principal);
        request.changeSessionId();
        return this.getRegistrationRedirect();
    }

    private ModelAndView getRegistrationRedirect() {
        return new ModelAndView(new RedirectView("/registration/list", true, false));
    }

    @ModelAttribute("loginForm")
    public LoginForm loginForm() {
        return new LoginForm();
    }

    public static class LoginForm {
        @NotBlank(message = "{validate.authenticate.username}")
        private String username;

        @NotBlank(message = "{validate.authenticate.password}")
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
