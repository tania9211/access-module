package org.access.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class Token extends AbstractEntity {
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "token", nullable = false, unique = true)
	private String token;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private UserImpl user; 

	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
