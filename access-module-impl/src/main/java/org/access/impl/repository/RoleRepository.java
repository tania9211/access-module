package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.RoleImpl;
import org.access.impl.entity.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleImpl, UUID> {
	public RoleImpl findByCreatorId(UUID creatorId);
	
	public RoleImpl findById(UUID id);

	public RoleImpl findByName(String name);
	
	@Query("update RoleImpl r set r.creatorId = :creatorId, r.name = :name where r.id = :id")
	public RoleImpl updateRole(@Param("creatorId") UUID creatorId, @Param("name") String name, @Param("id") UUID id);
}
