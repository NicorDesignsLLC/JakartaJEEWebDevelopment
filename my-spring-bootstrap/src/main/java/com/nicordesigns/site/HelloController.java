package com.nicordesigns.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
public class HelloController
{
    private GreetingService greetingService;

    @ResponseBody
    @RequestMapping("/")
    public String helloWorld()
    {
        return "Hello, World! from Spring WebApplicationInitializer Bootstrap Config";
    }

    @ResponseBody
    @RequestMapping(value = "/", params = {"name"})
    public String helloName(@RequestParam("name") String name)
    {
        return this.greetingService.getGreeting(name);
    }

    @Inject
    public void setGreetingService(GreetingService greetingService)
    {
        this.greetingService = greetingService;
    }
}
