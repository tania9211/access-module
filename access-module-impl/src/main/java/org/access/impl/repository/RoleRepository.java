package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.RoleImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleImpl, UUID> {
	public RoleImpl findByCreatorId(UUID creatorId);

	@Query("select r from RoleImpl r where r.id = :id and r.deleted = false")
	public RoleImpl findById(@Param("id") UUID id);

	@Query("select r from RoleImpl r where r.name = :name and r.deleted = false")
	public RoleImpl findByName(@Param("name") String name);
}
