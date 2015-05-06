package org.access.impl.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.access.api.entity.Role;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@SQLDelete(sql="UPDATE role SET deleted = true WHERE id = ?")
public class RoleImpl extends AbstractEntity implements Role {
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "creator_id", nullable = false)
	private UUID creatorId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	private Set<UserImpl> users = new HashSet<UserImpl>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<Permission> permissions = new HashSet<Permission>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(UUID creatorId) {
		this.creatorId = creatorId;
	}

	public Set<UserImpl> getUsers() {
		return users;
	}

	public void setUsers(Set<UserImpl> users) {
		this.users = users;
	}
}
