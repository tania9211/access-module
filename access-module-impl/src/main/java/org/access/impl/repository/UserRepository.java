package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	public User findByEmail(String email);
}
