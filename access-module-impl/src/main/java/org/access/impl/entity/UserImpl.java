package org.access.impl.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.access.api.entity.User;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "\"user\"", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "nickname") })
@SQLDelete(sql = "UPDATE \"user\" SET deleted = true WHERE id = ?")
@Where(clause = "deleted = 'false'")
public class UserImpl extends AbstractEntity implements User {
	@Column(name = "hash", nullable = false)
	private String hash;

	@Column(name = "salt", nullable = false)
	private String salt;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "nickname", unique = true, nullable = false)
	private String nickname;

	@Column(name = "isActive")
	private boolean isActive;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Set<RoleImpl> roles = new HashSet<RoleImpl>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Token> tokens = new HashSet<Token>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Permission> permissions = new HashSet<Permission>();

	public boolean isActive() {
		return isActive;
	}

	public Set<Token> getTokens() {
		return tokens;
	}

	public void setTokens(Set<Token> tokens) {
		this.tokens = tokens;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

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

	public Set<RoleImpl> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleImpl> roles) {
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
