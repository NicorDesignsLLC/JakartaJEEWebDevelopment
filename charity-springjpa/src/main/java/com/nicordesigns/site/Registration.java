package com.nicordesigns.site;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@XmlRootElement(name = "registration")
@Entity
@Table(name = "REGISTRATION")
public class Registration {

	@Override
	public int hashCode() {
		return Objects.hash(body, createdDate, fileAttachments, id, subject, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registration other = (Registration) obj;
		return Objects.equals(body, other.body) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(fileAttachments, other.fileAttachments) && id == other.id
				&& Objects.equals(subject, other.subject) && Objects.equals(userName, other.userName);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REGISTRATION_ID")
	private long id;

	@NotBlank(message = "{validate.registration.userName}")
	@Column(name = "USER_NAME", nullable = false)
	private String userName;

	@NotBlank(message = "{validate.registration.subject}")
	@Column(name = "SUBJECT", nullable = false)
	private String subject;

	@NotBlank(message = "{validate.registration.body}")
	@Column(name = "BODY", nullable = false)
	private String body;

	@Column(name = "CREATED_DATE", nullable = false)
	private Instant createdDate;

	@OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<FileAttachment> fileAttachments = new ArrayList<>();

	public int getNumberOfAttachments() {
		return fileAttachments != null ? fileAttachments.size() : 0;
	}

//	public List<FileAttachment> getAttachments() {
//		return fileAttachments;
//	}

    public Collection<FileAttachment> getAttachments() {
        return Collections.unmodifiableList(fileAttachments);
    }

	// Getters and Setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public Instant getDateCreated() {
		return createdDate;
	}

	public void setDateCreated(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public List<FileAttachment> getFileAttachments() {
		return fileAttachments;
	}

	public void setFileAttachments(List<FileAttachment> fileAttachments) {
		this.fileAttachments = fileAttachments;
	}

	public void addAttachment(FileAttachment attachment) {
		if (fileAttachments == null) {
			fileAttachments = new ArrayList<>();
		}
		fileAttachments.add(attachment);
		attachment.setRegistration(this);
	}

	public FileAttachment getAttachment(String name) {
		return fileAttachments.stream().filter(attachment -> attachment.getName().equals(name)).findFirst()
				.orElse(null);
	}

}