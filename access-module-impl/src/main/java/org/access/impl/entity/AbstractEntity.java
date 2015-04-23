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
	
	@Column(name = "date_create", nullable = false)
	protected String dateCreate;
	
	@Column(name = "date_modify", nullable = false)
	protected String dateModify;
	
	@Column(name = "version", nullable = false)
	protected Long version;
	
	@Column(name = "deleted")
	protected Boolean deleted;

	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(String dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getDateModify() {
		return dateModify;
	}

	public void setDateModify(String dateModify) {
		this.dateModify = dateModify;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
