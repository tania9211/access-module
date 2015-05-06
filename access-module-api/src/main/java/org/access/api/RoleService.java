package org.access.api;

import java.util.List;
import java.util.UUID;

import org.access.api.entity.Role;
import org.access.api.exception.DataInsertionException;

public interface RoleService<T extends Role> {
	/**
	 * Create new role.
	 * 
	 * @param name
	 *            The name of the role.
	 * @param creatorId
	 *            The id of the creator.
	 */
	public Role create(String name, UUID creatorId);

	/**
	 * Update role.
	 * 
	 * @param role
	 *            The role which should be updated.
	 */
	public Role update(Role role);

	/**
	 * Delete role.
	 * 
	 * @param role
	 *            The role which should be updated
	 */
	public void delete(Role role);

	/**
	 * Get role by it is name
	 * 
	 * @param name
	 *            Name of the role
	 */
	public Role getByName(String roleName);

	/**
	 * Get role by it is id
	 * 
	 * @param id
	 *            Id of the role
	 */
	public Role getById(UUID id);

	/**
	 * Get all roles.
	 */
	public List<T> list();
}
