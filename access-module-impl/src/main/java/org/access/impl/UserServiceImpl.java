package org.access.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.access.api.TokenType;
import org.access.api.UserService;
import org.access.api.entity.User;
import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.Token;
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
		
		user.setHash("1234");
		user.setSalt("1234");

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
		final UserImpl userImpl = userRepository.findByToken(token);

		/** check if token has verification type */
		for (Iterator<Token> it = userImpl.getTokens().iterator(); it.hasNext();) {
			Token tempToken = it.next();
			if (tempToken.getToken().equals(token)
					&& tempToken.getType().equals(TokenType.VERIFICATION)) {
				/** set user active and delete token */
				userImpl.setActive(true);
				tokenRepository.delete(tempToken);
			}
		}
	}
}
