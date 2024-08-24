package com.nicordesigns.site;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class Registration {

	private long id;

	@NotBlank(message = "{validate.registration.userName}")
	private String userName;

	@NotBlank(message = "{validate.registration.subject}")
	private String subject;

	@NotBlank(message = "{validate.registration.body}")
	private String body;

	private Instant createdDate;

	@Valid
	private Map<String, FileAttachment> fileAttachments = new LinkedHashMap<>();

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public FileAttachment getAttachment(String name) {
		return this.fileAttachments.get(name);
	}

	public Collection<FileAttachment> getAttachments() {
		return this.fileAttachments.values();
	}

	public void addAttachment(FileAttachment attachment) {
		this.fileAttachments.put(attachment.getName(), attachment);
	}

	public int getNumberOfAttachments() {
		return this.fileAttachments.size();
	}

	public Instant getDateCreated() {
		return createdDate;
	}

	public void setDateCreated(Instant dateCreated) {
		this.createdDate = dateCreated;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFileAttachments(List<FileAttachment> attachments) {
		// TODO Auto-generated method stub
		
	}

}