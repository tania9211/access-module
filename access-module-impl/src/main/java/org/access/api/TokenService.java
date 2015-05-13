package org.access.api;


public interface TokenService {
	/**
	 * Create new token for user.
	 * 
	 * @return generated token.
	 */
	public String generateToken();
}
