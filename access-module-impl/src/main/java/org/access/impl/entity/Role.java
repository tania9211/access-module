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

@Entity
@Table(name = "role")
public class Role extends AbstractEntity {
	@Column(name = "name", unique = true, nullable = false)
	private String name;
	@Column(name = "creator_id", nullable = false)
	private UUID creator_id;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
	private Set<User> users = new HashSet<User>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<Permission> permissions = new HashSet<Permission>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(UUID creator_id) {
		this.creator_id = creator_id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
