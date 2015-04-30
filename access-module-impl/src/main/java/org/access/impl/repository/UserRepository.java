package org.access.impl.repository;

import java.util.List;
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
	
	@Query("update UserImpl u set u.nickname = :nickname, u.email = :email where u.id = :id")
	public UserImpl updateUser(@Param("nickname")String nickname, @Param("email")String email, @Param("id")UUID id);
}
