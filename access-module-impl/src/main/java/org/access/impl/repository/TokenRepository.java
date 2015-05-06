package org.access.impl.repository;

import java.util.UUID;

import org.access.impl.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
	public Token findByToken(String token);
}
