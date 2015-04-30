package org.access.api;

import java.util.List;
import java.util.UUID;

import org.access.api.entity.User;
import org.access.api.exceptions.DataInsertionException;

public interface UserService<T> {
	/**
	 * Create new user.
	 * 
	 * @param nickname
	 *            The nickname of the user
	 * @param email
	 *            The email of the user
	 * @throws DataInsertionException
	 *             If user has not unique nickname or email.
	 */
	public User create(String nickname, String email)
			throws DataInsertionException;

	/**
	 * Update user.
	 * 
	 * @param user
	 *            The user which should be updated
	 * @throws DataInsertionException
	 *             If user has not unique nickname or email.
	 */
	public User update(User user) throws DataInsertionException;

	/**
	 * Delete user.
	 * 
	 * @param user
	 *            The user which should be updated
	 */
	public void delete(User user);

	/**
	 * Get user by it is id.
	 * 
	 * @param userId
	 *            Id of the user
	 */
	public User getById(UUID userId);

	/**
	 * Get user by it is email
	 * 
	 * @param email
	 *            Email of the user
	 */
	public User getByEmail(String email);

	/**
	 * Get all active users.
	 */
	public List<T> list();
}
