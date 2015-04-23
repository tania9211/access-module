package org.access.impl.repo;

import org.access.impl.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
	public Player findByEmail(String email);
}
