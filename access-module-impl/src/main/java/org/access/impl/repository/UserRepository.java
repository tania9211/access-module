package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	public User findByEmail(String email);

	public User findByNickname(String nickname);

	public User findById(UUID id);

	@Query("select u from User u inner join u.tokens t where t.token = :token")
	public User findByToken(@Param("token") String token);
}
