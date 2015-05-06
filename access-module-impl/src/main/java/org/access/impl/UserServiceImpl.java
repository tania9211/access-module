package org.access.impl;

import java.util.List;
import java.util.UUID;

import org.access.api.UserService;
import org.access.api.entity.User;
import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.TokenRepository;
import org.access.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Override
	public User create(String nickname, String email) {
		final UserImpl user = new UserImpl();
		final long time = System.currentTimeMillis();
		user.setDateCreated(time);
		user.setDateModified(time);
		user.setDeleted(false);
		user.setHash("1234");
		user.setSalt("1234");
		user.setVersion(1L);

		user.setNickname(nickname);
		user.setEmail(email);
		user.setActive(true);

		return userRepository.save(user);
	}

	@Override
	public User update(User user) {
		return userRepository.save((UserImpl) user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete((UserImpl) user);
	}

	@Override
	public User getById(UUID userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserImpl> list() {
		return userRepository.findAll();
	}

	@Override
	public void verify(String token) {
		// ((UserImpl) user).setActive(true);
		// / tokenRepository.delete(tokenRepository.findByToken(token));
	}
}
