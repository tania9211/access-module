package org.access.impl.repository;

import java.util.List;
import java.util.UUID;

import org.access.impl.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
	public Permission findById(UUID id);
	
	public List<Permission> findByObjectId(UUID objectId);

	public List<Permission> findByLevel(byte level);

	public List<Permission> findByType(String type);

	@Query("select p from Permission p inner join p.user u where"
			+ " p.type = :type and (not p.level < :level) and u.id = :userId and u.isActive = true and p.objectId is null")
	public Permission findUserTablePermission(@Param("type") String type,
			@Param("level") byte level, @Param("userId") UUID userId);

	@Query("select p from Permission p inner join p.user u where (p.objectId = :objectId or p.objectId is null)"
			+ " and p.type = :type and (not p.level < :level) and u.id = :userId and u.isActive = true")
	public Permission findUserRowPermission(@Param("type") String type,
			@Param("level") byte level, @Param("userId") UUID userId,
			@Param("objectId") UUID objectId);

	@Query("select p from Permission p inner join p.user u where"
			+ " p.type = :type and u.id = :userId and p.objectId is null and u.isActive = true")
	public Permission findPrevUserTablePermission(@Param("type") String type,
			@Param("userId") UUID userId);

	@Query("select p from Permission p inner join p.user u where (p.objectId = :objectId or p.objectId is null)"
			+ " and p.type = :type and u.id = :userId and u.isActive = true")
	public Permission findPrevUserRowPermission(@Param("type") String type,
			@Param("userId") UUID userId, @Param("objectId") UUID objectId);

	@Query("select p from Permission p inner join p.role r where p.type = :type and (not p.level < :level)"
			+ " and r.name = :roleName and p.objectId is null")
	public Permission findRoleTablePermission(@Param("type") String type,
			@Param("level") byte level, @Param("roleName") String roleName);

	@Query("select p from Permission p inner join p.role r where p.type = :type and (not p.level < :level)"
			+ " and (p.objectId = :objectId or p.objectId is null) and r.name = :roleName")
	public Permission findRoleRowPermission(@Param("type") String type,
			@Param("level") byte level, @Param("objectId") UUID objectId,
			@Param("roleName") String roleName);
	
	@Query("select p from Permission p inner join p.role r where p.type = :type"
			+ " and r.name = :roleName and p.objectId is null")
	public Permission findPrevRoleTablePermission(@Param("type") String type, @Param("roleName") String roleName);

	@Query("select p from Permission p inner join p.role r where p.type = :type"
			+ " and p.objectId = :objectId and r.name = :roleName")
	public Permission findPrevRoleRowPermission(@Param("type") String type, @Param("objectId") UUID objectId,
			@Param("roleName") String roleName);
}
