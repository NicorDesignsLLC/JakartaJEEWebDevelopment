package com.nicordesigns.site;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import com.nicordesigns.site.config.annotation.WebController;

@WebController
@Controller
public class AuthenticationController {
    private static final Logger log = LogManager.getLogger();

    @GetMapping(value = "login")
    public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, Map<String, Object> model) {
        log.info("AuthenticationController login GET");
        model.put("loginFailed", false);
        return new ModelAndView("login");
    }

    @ModelAttribute("loginForm")
    public LoginForm loginForm() {
        return new LoginForm();
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	log.info("AuthenticationController logout GET");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    public static class LoginForm {
        private String username;
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