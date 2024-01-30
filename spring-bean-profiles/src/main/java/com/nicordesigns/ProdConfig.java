package com.nicordesigns;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//ProdConfig.java
@Configuration
@Profile("prod")
public class ProdConfig {
 @Bean
 public String startupMessage() {
     return "Production Profile - Application starting up!";
 }
}
