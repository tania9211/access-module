package org.access.impl.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

@MappedSuperclass
// //@SQLInsert(sql =
// "UPDATE AbstractEntity SET dateCreated = CURRENT_TIMESTAMP WHERE id = ?")
// @SQLUpdate(sql =
// "UPDATE AbstractEntity SET dateModified = CURRENT_TIMESTAMP WHERE id = ?")
public class AbstractEntity implements Serializable{
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created")
	private Date dateCreated;

	// @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_modified")
	private Date dateModified;

	@Column(name = "version")
	private long version;

	@Column(name = "deleted")
	private boolean deleted;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreate) {
		this.dateCreated = dateCreate;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModify) {
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
