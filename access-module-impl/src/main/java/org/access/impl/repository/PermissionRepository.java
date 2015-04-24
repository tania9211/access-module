package org.access.impl.repository;

import java.util.List;
import java.util.UUID;

import org.access.impl.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	public Permission findByObjectId(UUID objectId);
	
	public List<Permission> findByLevel(byte level);
	
	public List<Permission> findByType(String type);
}
