package org.access.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.access.api.Level;
import org.access.api.PermissionService;
import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.Permission;
import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.access.impl.repository.PermissionRepository;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Permission setRolePermission(Role role, Level level, UUID objectId,
			String type) throws DataInsertionException {
		if (role == null)
			throw new DataInsertionException("Role is null");

		return setRolePermission(((Role) role).getName(), level, objectId, type);
	}

	@Override
	public Permission setRolePermission(String roleName, Level level, UUID objectId,
			String type) throws DataInsertionException {
		final Permission permission = buildPermission(level.getValue(),
				objectId, type);
		final Role role = roleRepository.findByName(roleName);
		if (role == null)
			throw new DataInsertionException("Can not find role by roleName");

		permission.setRole(role);

		Permission previousPermission;

		if (objectId == null) {
			previousPermission = permissionRepository
					.findPrevRoleTablePermission(type, roleName);
		} else {
			previousPermission = permissionRepository
					.findPrevRoleRowPermission(type, objectId, roleName);
		}
		if (previousPermission != null
				&& previousPermission.getLevel() >= level.getValue())
			return previousPermission;

		if (previousPermission != null
				&& previousPermission.getLevel() < level.getValue())
			permissionRepository.delete(previousPermission);

		return permissionRepository.save(permission);
	}

	@Override
	public Permission setUserPermission(UUID userId, Level level, UUID objectId,
			String type) throws DataInsertionException {
		final Permission permission = buildPermission(level.getValue(),
				objectId, type);
		final User user = userRepository.findById(userId);

		if (user == null)
			throw new DataInsertionException("User is null");
		if (!user.isActive())
			throw new DataInsertionException("User is not active");
		permission.setUser(user);

		Permission previousPermission;

		if (objectId == null) {
			previousPermission = permissionRepository
					.findPrevUserTablePermission(type, userId);
		} else {
			previousPermission = permissionRepository
					.findPrevUserRowPermission(type, userId, objectId);
		}
		if (previousPermission != null
				&& previousPermission.getLevel() >= level.getValue())
			return previousPermission;  

		if (previousPermission != null
				&& previousPermission.getLevel() < level.getValue())
			permissionRepository.delete(previousPermission);

		return permissionRepository.save(permission);
	}

	@Override
	public Permission setUserPermission(User user, Level level, UUID objectId,
			String type) throws DataInsertionException {
		if (user == null)
			throw new DataInsertionException("User is null");

		return setUserPermission(((User) user).getId(), level, objectId, type);
	}

	@Override
	public boolean checkUserPermission(UUID userId, String type, UUID objectId,
			Level level) {
		Permission permission;

		if (objectId == null) {
			permission = permissionRepository.findUserTablePermission(type,
					level.getValue(), userId);
		} else {
			permission = permissionRepository.findUserRowPermission(type,
					level.getValue(), userId, objectId);
		}

		return permission == null ? false : true;
	}

	@Override
	public boolean checkUserPermission(UUID userId, String type, Level level) {
		return checkUserPermission(userId, type, null, level);
	}

	@Override
	public boolean checkRolePermission(String roleName, String type,
			UUID objectId, Level level) {
		Permission permission;

		if (objectId == null) {
			permission = permissionRepository.findRoleTablePermission(type,
					level.getValue(), roleName);
		} else {
			permission = permissionRepository.findRoleRowPermission(type,
					level.getValue(), objectId, roleName);
		}

		return permission == null ? false : true;
	}

	@Override
	public boolean checkRolePermission(String roleName, String type, Level level) {
		return checkRolePermission(roleName, type, null, level);
	}

	private Permission buildPermission(byte level, UUID objectId, String type) {
		final Permission permission = new Permission();

		final Date date = Calendar.getInstance().getTime();
		//final long time = System.currentTimeMillis();
		//permission.setDateCreate(time);
		//permission.setDateModify(time);
		permission.setVersion(1L);
		permission.setDeleted(false);

		permission.setLevel(level);
		permission.setObjectId(objectId);
		permission.setType(type);

		return permission;
	}
}
