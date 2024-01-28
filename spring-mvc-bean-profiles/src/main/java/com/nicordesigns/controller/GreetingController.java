package com.nicordesigns.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @Value("${greeting.message}")
    private String greetingMessage;

    @GetMapping("/greet")
    public String greet(Model model) {
        model.addAttribute("greeting", greetingMessage);
        return "greeting";
    }
}
