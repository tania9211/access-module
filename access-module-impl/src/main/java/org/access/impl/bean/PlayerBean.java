package org.access.impl.bean;

import org.access.impl.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerBean {
	@Autowired
	PlayerRepository playerRepository;

	public PlayerRepository getPlayerRepository() {
		return playerRepository;
	}

	public void setPlayerRepository(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

}
