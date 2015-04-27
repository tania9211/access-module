package org.access.impl.repository;

import java.util.List;
import java.util.UUID;

import org.access.impl.entity.Permission;
import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	public List<Permission> findByObjectId(UUID objectId);
	
	public Permission findByObjectIdAndTypeAndUser(UUID objectId, String type, User user);
	
	public Permission findByObjectIdAndTypeAndRole(UUID objectId, String type, Role role);
	
	public List<Permission> findByLevel(byte level);
	
	public List<Permission> findByType(String type);
}
