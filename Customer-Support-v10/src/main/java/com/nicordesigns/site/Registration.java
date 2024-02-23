package com.nicordesigns.site;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Registration implements Serializable
{
    private String userName;

    private String subject;

    private String body;
    
    private Instant dateCreated;
    
    private Date createdDate;
    
    
    private Map<String, FileAttachment> fileAttachments = new LinkedHashMap<>();

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
    
    public Instant getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated)
    
    {
        this.dateCreated = dateCreated;
        this.createdDate = Date.from(dateCreated);
    }

    public FileAttachment getAttachment(String name)
    {
        return this.fileAttachments.get(name);
    }

    public Collection<FileAttachment> getAttachments()
    {
        return this.fileAttachments.values();
    }

    public void addAttachment(FileAttachment attachment)
    {
        this.fileAttachments.put(attachment.getName(), attachment);
    }

    public int getNumberOfAttachments()
    {
        return this.fileAttachments.size();
    }

	public Date getCreatedDate() {
		return Date.from(this.dateCreated);
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = Date.from(this.dateCreated);
	}
}