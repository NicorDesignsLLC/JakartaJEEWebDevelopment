package com.nicordesigns.site;

import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.Map;

@Repository
public class InMemoryUserAdminRepository implements UserAdminRepository
{
    private final Map<String, String> userDatabase = new Hashtable<>();

    public InMemoryUserAdminRepository()
    {
    	this.userDatabase.put("Nicolaas", "Black");
    	this.userDatabase.put("Danette", "White");
    	this.userDatabase.put("Tom", "Green");
        
    }

    @Override
    public String getPasswordForUser(String username)
    {
        return this.userDatabase.get(username);
    }
}
