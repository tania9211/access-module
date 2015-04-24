package org.access.impl.entity;

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
	protected UUID id;
	
	@Column(name = "date_created", nullable = false)
	protected String dateCreated;
	
	@Column(name = "date_modified", nullable = false)
	protected String dateModified;
	
	@Column(name = "version", nullable = false)
	protected long version;
	
	@Column(name = "deleted")
	protected boolean deleted;

	public String getDateCreate() {
		return dateCreated;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreated = dateCreate;
	}

	public String getDateModify() {
		return dateModified;
	}

	public void setDateModify(String dateModify) {
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
}
