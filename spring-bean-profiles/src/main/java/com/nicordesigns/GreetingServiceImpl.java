package com.nicordesigns;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService
{
	 private String greetingMessage;

	    public void setGreetingMessage(String greetingMessage) {
	        this.greetingMessage = greetingMessage;
	    }

	    @Override
	    public String getGreeting(String name) {
	        return greetingMessage + " " + name + "!";
	    }
}