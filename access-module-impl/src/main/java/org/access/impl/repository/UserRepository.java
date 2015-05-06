package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserImpl, UUID> {
	//@Query("select u from UserImpl u where u.email = :email and u.deleted = false")
	public UserImpl findByEmail(String email);

	public UserImpl findByNickname(String nickname);

//	@Query("select u from UserImpl u where u.id = :id and u.deleted = false")
	public UserImpl findById(UUID id);
}
