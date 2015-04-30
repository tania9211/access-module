package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.RoleImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleImpl, UUID> {
	public RoleImpl findByCreatorId(UUID creatorId);
	
	public RoleImpl findById(UUID id);

	public RoleImpl findByName(String name);
}
