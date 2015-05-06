package org.access.api;

import java.util.List;
import java.util.UUID;

import org.access.api.entity.User;
import org.access.api.exception.DataInsertionException;

public interface UserService<T extends User> {
	/**
	 * Create new user.
	 * 
	 * @param nickname
	 *            The nickname of the user
	 * @param email
	 *            The email of the user.
	 */
	public User create(String nickname, String email);

	/**
	 * Update user.
	 * 
	 * @param user
	 *            The user which should be updated.
	 */
	public User update(User user);

	/**
	 * Delete user.
	 * 
	 * @param user
	 *            The user which should be updated.
	 */
	public void delete(User user);

	/**
	 * Get user by it is id.
	 * 
	 * @param userId
	 *            Id of the user.
	 */
	public User getById(UUID userId);

	/**
	 * Get user by it is email
	 * 
	 * @param email
	 *            Email of the user.
	 */
	public User getByEmail(String email);

	/**
	 * Get all active users.
	 */
	public List<T> list();
	
	
	public void verify(String token);
}
