package org.access.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.access.api.UserService;
import org.access.api.entity.User;
import org.access.api.exceptions.DataInsertionException;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User create(String nickname, String email)
			throws DataInsertionException {
		if (userRepository.findByEmail(email) != null)
			throw new DataInsertionException(
					"Email not unique, please choose another email.");
		if (userRepository.findByNickname(nickname) != null)
			throw new DataInsertionException(
					"Nickname not unique, please choose another nickname.");

		final UserImpl user = new UserImpl();
		final Date date = Calendar.getInstance().getTime();
		user.setDateCreate(date);
		user.setDateModify(date);
		user.setDeleted(false);
		user.setHash("1234");
		user.setSalt("1234");
		user.setVersion(1L);

		user.setNickname(nickname);
		user.setEmail(email);
		user.setActive(true);

		userRepository.save(user);

		return user;
	}

	@Override
	public User update(User user) throws DataInsertionException {
		final UserImpl userImpl = (UserImpl) user;
		if (userRepository.findByEmail(userImpl.getEmail()) != null)
			throw new DataInsertionException(
					"Email not unique, please choose another email.");
		if (userRepository.findByNickname(userImpl.getNickname()) != null)
			throw new DataInsertionException(
					"Nickname not unique, please choose another nickname.");

		UserImpl userImpls = userRepository.updateUser(userImpl.getNickname(),
				userImpl.getEmail(), userImpl.getId());
		return userImpl;
	}

	@Override
	public void delete(User user) {
		userRepository.delete((UserImpl) user);
	}

	@Override
	public User getById(UUID userId) {
		UserImpl userImpl = userRepository.findById(userId);
		return userImpl;
	}

	@Override
	public User getByEmail(String email) {
		UserImpl userImpl = userRepository.findByEmail(email);
		return userImpl;
	}

	@Override
	public List<UserImpl> list() {
		List<UserImpl> list = userRepository.findAll();
		return list;
	}
}
