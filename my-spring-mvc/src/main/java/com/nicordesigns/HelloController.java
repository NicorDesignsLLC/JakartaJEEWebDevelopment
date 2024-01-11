package com.nicordesigns;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping(value = "/")
    public String hello(Model model) {
        model.addAttribute("message", "Hello, Spring MVC!");
        return "hello";
    }
}
