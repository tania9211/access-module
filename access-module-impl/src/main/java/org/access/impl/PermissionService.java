package org.access.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.access.impl.entity.Permission;
import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.access.impl.repository.PermissionRepository;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public void setRolePermission(Role role, byte level, UUID objectId,
			String type) {
		if (!checkRolePermission(role, type, objectId)) {
			final Permission permission = setNewPermissiondetails(level,
					objectId, type);
			permission.setRole(role);

			permissionRepository.save(permission);
		}
	}

	public void setUserPermission(User user, byte level, UUID objectId,
			String type) {
		if (!checkUserPermission(user, type, objectId)) {
			final Permission permission = setNewPermissiondetails(level,
					objectId, type);
			permission.setUser(user);

			permissionRepository.save(permission);
		}
	}

	public boolean checkUserPermission(User user, String type, UUID objectId) {
		final Permission permission = permissionRepository
				.findByObjectIdAndTypeAndUser(objectId, type, user);

		if (permission == null && objectId != null) {
			final Permission permissionAll = permissionRepository
					.findByObjectIdAndTypeAndUser(null, type, user);

			if (permissionAll != null) {
				return isEmptyTable(type, objectId);
			}
		}

		return permission == null ? false : true;
	}

	public boolean checkRolePermission(Role role, String type, UUID objectId) {
		final Permission permission = permissionRepository
				.findByObjectIdAndTypeAndRole(objectId, type, role);

		if (permission == null && objectId != null) {
			final Permission permissionAll = permissionRepository
					.findByObjectIdAndTypeAndRole(null, type, role);

			if (permissionAll != null) {
				return isEmptyTable(type, objectId);
			}
		}

		return permission == null ? false : true;
	}

	private Permission setNewPermissiondetails(byte level, UUID objectId,
			String type) {
		final Permission permission = new Permission();

		final Date date = Calendar.getInstance().getTime();
		permission.setDateCreate(date);
		permission.setDateModify(date);
		permission.setVersion(1L);
		permission.setDeleted(false);

		permission.setLevel(level);
		permission.setObjectId(objectId);
		permission.setType(type);

		return permission;
	}

	private boolean isEmptyTable(String type, UUID objectId) {
		final String querySrt = "from " + type + " a where a.id = :id";
		final Query query = entityManager.createQuery(querySrt);
		query.setParameter("id", objectId);

		if (!query.getResultList().isEmpty()) {
			return true;
		}

		return false;
	}

}
