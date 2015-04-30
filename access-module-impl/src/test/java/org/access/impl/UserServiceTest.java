package org.access.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.access.api.exceptions.DataInsertionException;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_config.xml")
public class UserServiceTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	/**
	 * Create user and save. Then get user by it is email.
	 * 
	 * @result both email should be equals
	 * 
	 *         After that get this user by it id.
	 * @result id should be equals
	 */
	public void testGetUser() {
		final String email = "vasia@mail.ru";
		UserImpl user = null;
		try {
			user = (UserImpl) userServiceImpl.create("Vasia", email);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		UserImpl resultUser = (UserImpl) userServiceImpl.getByEmail(email);
		assertEquals(email, user.getEmail());

		resultUser = (UserImpl) userServiceImpl.getById(user.getId());
		assertEquals(user.getId(), resultUser.getId());

		/** check if list include user */
		resultUser = null;
		final List<UserImpl> list = userServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == user.getId())
				resultUser = list.get(i);
		}
		assertNotNull(resultUser);
	}

	@Test
	/**
	 * Delete user and try to find user by id and by email. Also try to find
	 * this user in list.
	 * 
	 * @result it it impossible to find deleted user.
	 */
	public void testDeleteUser() {
		final String email = "vasia@mail.ru";
		UserImpl user = null;
		try {
			user = (UserImpl) userServiceImpl.create("Vasia", "vasia@mail.ru");
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
		userServiceImpl.delete(user);

		UserImpl resultUser = (UserImpl) userServiceImpl.getByEmail(email);
		assertNull(resultUser);

		resultUser = (UserImpl) userServiceImpl.getById(user.getId());
		assertNull(resultUser);

		/** check if list include user */
		resultUser = null;
		final List<UserImpl> list = userServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == user.getId())
				resultUser = list.get(i);
		}
		assertNull(resultUser);
	}

	@Test
	/**
	 * Create two users with the same email.
	 * @result expect DataInsertionException exception.
	 * Update email of second user to not unique.
	 * @result expect DataInsertionException exception.
	 */
	public void testUniqueEmail() {
		final String email = "vasia@mail.ru";
		try {
			UserImpl user = (UserImpl) userServiceImpl.create("VasiaFirst",
					email);

			UserImpl user2 = (UserImpl) userServiceImpl.create("VasiaSecond",
					email);
			expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
		/** update email to not unique */
		try {
			UserImpl user3 = (UserImpl) userServiceImpl.create("Pasha1",
					"Pasha123@mail.ru");

			user3.setEmail(email);
			user3.setActive(false);

	//		userServiceImpl.update(user3);
	//		expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Create two users with the same nickname.
	 * 
	 * @result expect DataInsertionException exception. Update nickname of
	 *         second user to not unique.
	 * @result expect DataInsertionException exception.
	 */
	public void testUniqueNickname() {
		final String nickname = "VasiaFirst";
		try {
			UserImpl user = (UserImpl) userServiceImpl.create(nickname,
					"vasia@mail.ru");
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		try {
			UserImpl user2 = (UserImpl) userServiceImpl.create(nickname,
					"vasiaSecond@mail.ru");

			expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		/** update nickname to not unique */
		try {
			UserImpl user3 = (UserImpl) userServiceImpl.create("Pasha",
					"vasia123@mail.ru");

			user3.setNickname(nickname);
		//	userServiceImpl.update(user3);
	//		expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

	}

	@Test
	/**
	 * Create user and update it. Check nickname and email of update user.
	 */
	public void testUpdate() {
		try {
			UserImpl userImpl = (UserImpl) userServiceImpl.create("VasiaFirst",
					"vasia@mail.ru");
			final String nickname = "New nick";
			userImpl.setNickname(nickname);
			userServiceImpl.update(userImpl);

			/** check user nikcname */
			userImpl = (UserImpl) userServiceImpl.getById(userImpl.getId());
			assertEquals(userImpl.getNickname(), nickname);

			final String email = "New email";
			userImpl.setEmail(email);
			userServiceImpl.update(userImpl);

			/** check user email */
			userImpl = (UserImpl) userServiceImpl.getById(userImpl.getId());
			assertEquals(userImpl.getEmail(), email);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
	}
}
