package org.access.impl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "\"user\"")
public class User extends AbstractEntity {
	@Column(name = "hash", nullable = false)
	private String hash;

	@Column(name = "salt", nullable = false)
	private String salt;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) },
	inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Set<Role> roles = new HashSet<Role>();
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Permission> permissions = new HashSet<Permission>();

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
