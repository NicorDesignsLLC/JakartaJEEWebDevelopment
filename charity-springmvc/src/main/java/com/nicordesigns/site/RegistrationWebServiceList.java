package com.nicordesigns.site;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Registrations")
public class RegistrationWebServiceList
{
    private List<Registration> value;

    @XmlElement(name = "Registration")
    public List<Registration> getValue()
    {
        return this.value;
    }

    public void setValue(List<Registration> value)
    {
        this.value = value;
    }
}
