package org.access.impl.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@MappedSuperclass
public class AbstractEntity {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type = "pg-uuid")
	private  UUID id;

	@Column(name = "date_created", nullable = false)
	private Date dateCreated;

	@Column(name = "date_modified", nullable = false)
	private Date dateModified;

	@Column(name = "version", nullable = false)
	private long version;

	@Column(name = "deleted")
	private boolean deleted;

	public Date getDateCreate() {
		return dateCreated;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreated = dateCreate;
	}

	public Date getDateModify() {
		return dateModified;
	}

	public void setDateModify(Date dateModify) {
		this.dateModified = dateModify;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public UUID getId() {
		return id;
	}
}
