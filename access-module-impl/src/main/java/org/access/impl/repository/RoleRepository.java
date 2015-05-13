package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
	public Role findByCreatorId(UUID creatorId);

	@Query("select r from Role r where r.id = :id and r.deleted = false")
	public Role findById(@Param("id") UUID id);

	@Query("select r from Role r where r.name = :name and r.deleted = false")
	public Role findByName(@Param("name") String name);
}
