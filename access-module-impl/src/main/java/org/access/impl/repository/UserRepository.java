package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserImpl, UUID> {
	public UserImpl findByEmail(String email);

	public UserImpl findByNickname(String nickname);

	public UserImpl findById(UUID id);

	@Query("select u from UserImpl u inner join u.tokens t where t.token = :token")
	public UserImpl findByToken(@Param("token") String token);
}
