package com.nicordesigns.site;

import java.security.Principal;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TemporaryAuthenticationService implements AuthenticationService
{
    private static final Logger log = LogManager.getLogger();

    @Autowired UserAdminRepository userAdminRepository;

    @Override
    @Transactional(readOnly = true)
    public Principal authenticate(String username, String password)
    {
        String currentPassword = this.userAdminRepository.getPasswordForUser(username);
        if(currentPassword == null)
        {
            log.warn("Authentication failed for non-existent user {}.", username);
            return null;
        }

        if(!currentPassword.equals(password))
        {
            log.warn("Authentication failed for user {}.", username);
            return null;
        }

        log.debug("User {} successfully authenticated.", username);

        return new UserAdminPrincipal(username);
    }
}
