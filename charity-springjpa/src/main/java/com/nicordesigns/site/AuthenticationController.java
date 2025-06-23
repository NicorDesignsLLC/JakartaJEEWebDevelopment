package com.nicordesigns.site;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {
    private static final Logger log = LogManager.getLogger();

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Map<String, Object> model,
            HttpSession session) {

        model.put("loginFailed", "true".equals(error));
        model.put("logoutSuccess", "true".equals(logout));

        return new ModelAndView("login", model);
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
