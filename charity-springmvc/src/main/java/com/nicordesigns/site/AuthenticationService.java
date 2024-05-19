package com.nicordesigns.site;

import java.security.Principal;

import javax.validation.constraints.NotBlank;

public interface AuthenticationService
{
    Principal authenticate(  @NotBlank(message = "{validate.authenticate.username}") String username, 
    		 @NotBlank(message = "{validate.authenticate.password}") String password);
}
