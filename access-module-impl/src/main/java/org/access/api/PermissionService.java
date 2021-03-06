package org.access.api;

import java.util.UUID;

import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.Permission;
import org.access.impl.entity.Role;
import org.access.impl.entity.User;

public interface PermissionService {
	/**
	 * Set permission to role.
	 * 
	 * @param role
	 *            The role which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 * @throws DataInsertionException
	 *             If role equals to null.
	 */
	public Permission setRolePermission(Role role, Level level, UUID objectId,
			String type) throws DataInsertionException;

	/**
	 * Set permission to role.
	 * 
	 * @param roleName
	 *            The name of the role which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 * @throws DataInsertionException
	 *             If role equals to null.
	 */
	public Permission setRolePermission(String roleName, Level level, UUID objectId,
			String type) throws DataInsertionException;

	/**
	 * Set permission to user.
	 * 
	 * @param userId
	 *            UUID of the user which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 * @throws DataInsertionException
	 *             If user equals to null or user not active.
	 */
	public Permission setUserPermission(UUID userId, Level level, UUID objectId,
			String type) throws DataInsertionException;

	/**
	 * Set permission to user.
	 * 
	 * @param user
	 *            User which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 * @throws DataInsertionException
	 *             If user equals to null or user not active.
	 */
	public Permission setUserPermission(User user, Level level, UUID objectId,
			String type) throws DataInsertionException;

	/**
	 * Check role permission to one object.
	 * 
	 * @param roleName
	 *            The name of the role which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 */
	public boolean checkRolePermission(String roleName, String type,
			UUID objectId, Level level);

	/**
	 * Check role permission to table.
	 * 
	 * @param roleName
	 *            The name of the role which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param type
	 *            Table which you want to set permission
	 */
	public boolean checkRolePermission(String roleName, String type, Level level);

	/**
	 * Check user permission to one object.
	 * 
	 * @param userId
	 *            UUID of user which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param objectId
	 *            UUID of the object which you want to set permission
	 * @param type
	 *            Table which you want to set permission
	 */
	public boolean checkUserPermission(UUID userId, String type, UUID objectId,
			Level level);

	/**
	 * Check user permission to the table.
	 * 
	 * @param userId
	 *            UUID of user which you want to set permission
	 * @param level
	 *            Level of the permission
	 * @param type
	 *            Table which you want to set permission
	 */
	public boolean checkUserPermission(UUID userId, String type, Level level);

}
