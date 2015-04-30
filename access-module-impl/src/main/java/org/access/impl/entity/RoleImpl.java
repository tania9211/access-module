package org.access.impl.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.access.api.entity.Role;

@Entity
@Table(name = "role")
public class RoleImpl extends AbstractEntity implements Role{
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "creator_id", nullable = false)
	private UUID creatorId;

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
