package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
	public Role findByCreatorId(UUID creatorId);
}
