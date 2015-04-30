package org.access.impl.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permission")
public class Permission extends AbstractEntity {
	@Column(name = "level")
	private byte level;
	@Column(name = "type", unique = true, nullable = false)
	private String type;
	@Column(name = "object_id")
	private UUID objectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private UserImpl user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = true)
	private RoleImpl role;

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public RoleImpl getRole() {
		return role;
	}

	public void setRole(RoleImpl role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}

	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}
}
