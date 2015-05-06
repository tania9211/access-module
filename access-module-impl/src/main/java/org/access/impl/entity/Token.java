package org.access.impl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.access.api.TokenType;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "token", uniqueConstraints = { @UniqueConstraint(columnNames = "token") })
@SQLDelete(sql="UPDATE token SET deleted = true WHERE id = ?")
public class Token extends AbstractEntity {
	@Column(name = "type", nullable = false)
	private TokenType type;

	@Column(name = "token", nullable = false, unique = true)
	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserImpl user;

	public UserImpl getUser() {
		return user;
	}

	public void setUser(UserImpl user) {
		this.user = user;
	}

	public TokenType getType() {
		return type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
