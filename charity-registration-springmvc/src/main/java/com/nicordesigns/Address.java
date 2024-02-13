package com.nicordesigns;

import java.net.URL;
import java.time.Instant;
import java.util.Date;

public class Address implements Comparable<Address> {
    private String charityId;
    private String charityName;
    private String phoneNumber;
    private URL webAddress; 
    private Date registrationday;
    private Instant dateCreated;

    public Address(String charityId, String charityName, String phoneNumber,
                   URL webAddress, Date registrationday,
                   Instant dateCreated) {
        this.charityId = charityId;
        this.charityName = charityName;
        this.phoneNumber = phoneNumber;
        this.webAddress = webAddress;
        this.setRegistrationday(registrationday);
        this.dateCreated = dateCreated;
    }

    public String getCharityId() {
        return charityId;
    }

    public void setCharityId(String charityId) {
        this.charityId = charityId;
    }

    public String getCharityName() {
        return charityName;
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public URL getWebAddress() { // Getter returns URL instead of String
        return webAddress;
    }

    public void setWebAddress(URL webAddress) { // Setter receives URL instead of String
        this.webAddress = webAddress;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int compareTo(Address other) {
        int last = charityName.compareTo(other.charityName);
        if (last == 0)
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
