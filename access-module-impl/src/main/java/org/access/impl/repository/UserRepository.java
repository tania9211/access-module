package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserImpl, UUID> {
	public UserImpl findByEmail(String email);
	
	public UserImpl findByNickname(String nickname);
	
	public UserImpl findById(UUID id);
}
