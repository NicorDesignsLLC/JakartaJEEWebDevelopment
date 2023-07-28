package com.nicordesigns;

import java.time.Instant;
import java.util.Date;

public class Address implements Comparable<Address>
{
    private String charityId;

    private String charityName;

    private String phoneNumber;

    private String webAddress;

    private Date registrationday;

    private Instant dateCreated;

    public Address(String charityId, String charityName, String phoneNumber,
                   String address, Date registrationday,
                   Instant dateCreated)
    {
        this.charityId = charityId;
        this.charityName = charityName;
        this.phoneNumber = phoneNumber;
        this.webAddress = address;
        this.setRegistrationday(registrationday);
        this.dateCreated = dateCreated;
    }

    public String getCharityId()
    {
        return charityId;
    }

    public void setCharityId(String charityId)
    {
        this.charityId = charityId;
    }

    public String getCharityName()
    {
        return charityName;
    }

    public void setCharityName(String charityName)
    {
        this.charityName = charityName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return webAddress;
    }

    public void setAddress(String address)
    {
        this.webAddress = address;
    }

    
    public Instant getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    {
        this.dateCreated = dateCreated;
    }
    
    
    @Override
    public int compareTo(Address other)
    {
        int last = charityName.compareTo(other.charityName);
        if(last == 0)
            return charityId.compareTo(other.charityId);
        return last;
    }

	public Date getRegistrationday() {
		return registrationday;
	}

	public void setRegistrationday(Date registrationday) {
		this.registrationday = registrationday;
	}
}
