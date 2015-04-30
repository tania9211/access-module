package org.access.api;

import java.util.UUID;

import org.access.api.entity.Role;
import org.access.api.entity.User;
import org.access.api.exceptions.NotActiveUserException;
import org.access.api.exceptions.WrongRoleException;
import org.access.api.exceptions.WrongUserExceprion;

public interface PermissionService {
	public void setRolePermission(Role role, Level level, UUID objectId,
			String type) throws WrongRoleException;

	public void setRolePermission(String roleName, Level level, UUID objectId,
			String type) throws WrongRoleException;

	public void setUserPermission(UUID userId, Level level, UUID objectId,
			String type) throws WrongUserExceprion, NotActiveUserException;

	public void setUserPermission(User user, Level level, UUID objectId,
			String type) throws WrongUserExceprion, NotActiveUserException;

	public boolean checkRolePermission(String roleName, String type,
			UUID objectId, Level level);

	public boolean checkRolePermission(String roleName, String type, Level level);

	public boolean checkUserPermission(UUID userId, String type, UUID objectId,
			Level level);

	public boolean checkUserPermission(UUID userId, String type, Level level);
}
