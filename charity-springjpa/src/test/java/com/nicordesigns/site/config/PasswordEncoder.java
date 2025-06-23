package com.nicordesigns.site.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	
	public static void main(String[] args) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    System.out.println(encoder.encode("Black")); // example output: $2a$10$...
	}

}
