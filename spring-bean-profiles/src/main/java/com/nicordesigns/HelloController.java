package com.nicordesigns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    private final GreetingService greetingService;

    // Constructor injection
    @Autowired
    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @ResponseBody
    @RequestMapping("/")
    public String helloWorld() {
    	return this.greetingService.getGreeting("Started"); //return "Hello, World!";
    }

    @ResponseBody
    @RequestMapping(value = "/", params = {"name"})
    public String helloName(@RequestParam("name") String name) {
        return this.greetingService.getGreeting(name);
    }
}
