package org.access.impl;

import java.util.UUID;

import org.access.api.TokenService;

public class TokenServiceImpl implements TokenService {

	/**
	 * Create new token for user.
	 * 
	 * @return generated token.
	 */
	public String generateToken() {
		return UUID.randomUUID().toString();
	}
}
