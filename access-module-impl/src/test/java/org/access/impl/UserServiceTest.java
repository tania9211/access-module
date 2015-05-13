package org.access.impl;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.access.api.TokenType;
import org.access.api.annotation.Transactional;
import org.access.impl.entity.Token;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.TokenRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:test_spring_config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private TokenServiceImpl tokenServiceImpl;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	/**
	 * Create user. 
	 * Then get this user and check his id and other fields.
	 * 
	 * @result id and other fields should be equals in saved and getter user.
	 */
	public void testCreateUser() {
		final UserImpl user = (UserImpl) userServiceImpl.create("Vasia",
				"vasia@mail.ru");
		final UserImpl user2 = userRepository.findById(user.getId());

		assertEquals(user.getId(), user2.getId());
		assertEquals(user.getNickname(), user2.getNickname());
		assertEquals(user.getEmail(), user2.getEmail());
		assertEquals(user.getVersion(), user2.getVersion());
	}

	@Test
	/**
	 * Create user and update it. 
	 * Then get this user and check his id and other fields.
	 * 
	 * @result id and other fields should be equals in saved and getter user.
	 */
	public void testUpdateUser() {
		UserImpl user = (UserImpl) userServiceImpl.create("Vasia",
				"vasia@mail.ru");
		user.setEmail("vasia123@mail.ru");
		user = (UserImpl) userServiceImpl.update(user);

		final UserImpl user2 = userRepository.findById(user.getId());

		assertEquals(user.getId(), user2.getId());
		assertEquals(user.getNickname(), user2.getNickname());
		assertEquals(user.getEmail(), user2.getEmail());
		assertEquals(user.getVersion(), user2.getVersion());
	}

	@Test
	/**
	 * Create user and save. Then get user by it is email.
	 * @result both email should be equals
	
	 * After that get this user by it id.
	 * @result id should be equals
	 */
	public void testGetUser() {
		final String email = "vasia@mail.ru";
		final UserImpl user = (UserImpl) userServiceImpl.create("Vasia", email);

		UserImpl resultUser = (UserImpl) userServiceImpl.getByEmail(email);
		assertEquals(email, user.getEmail());

		resultUser = (UserImpl) userServiceImpl.getById(user.getId());
		assertEquals(user.getId(), resultUser.getId());

		/** check if list include user */
		resultUser = null;
		final List<UserImpl> list = userServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(user.getId()))
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

		final UserImpl user = (UserImpl) userServiceImpl.create("Vasia",
				"vasia@mail.ru");

		userServiceImpl.delete(user);

		UserImpl resultUser = (UserImpl) userServiceImpl.getByEmail(email);
		assertNull(resultUser);

		resultUser = (UserImpl) userServiceImpl.getById(user.getId());
		assertNull(resultUser);

		/** check if list include user */
		resultUser = null;
		final List<UserImpl> list = userServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(user.getId()))
				resultUser = list.get(i);
		}
		assertNull(resultUser);
	}

	@Test
	/**
	 * Create two users with the same email.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUniqueEmail() {
		final String email = "vasia@mail.ru";

		expectedException.expect(DataIntegrityViolationException.class);

		final UserImpl user = (UserImpl) userServiceImpl.create("VasiaFirst",
				email);
		final UserImpl user2 = (UserImpl) userServiceImpl.create("VasiaSecond",
				email);
	}

	@Test
	/**
	 * Update email of second user to not unique value.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUpdateEmail() {
		final String email = "vasia@mail.ru";

		expectedException.expect(DataIntegrityViolationException.class);

		final UserImpl user = (UserImpl) userServiceImpl.create("VasiaFirst",
				email);
		final UserImpl user2 = (UserImpl) userServiceImpl.create("VasiaSecond",
				"vasia123@mail.ru");

		user2.setEmail(email);
		userServiceImpl.update(user2);
	}

	@Test
	/**
	 * Create two users with the same nickname.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUniqueNickname() {
		final String nickname = "VasiaFirst";

		expectedException.expect(DataIntegrityViolationException.class);

		final UserImpl user = (UserImpl) userServiceImpl.create(nickname,
				"vasia@mail.ru");
		final UserImpl user2 = (UserImpl) userServiceImpl.create(nickname,
				"vasia123@mail.ru");
	}

	@Test
	/**
	 * Update nickname of second user to not unique value.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUpdateNickname() {
		final String nickname = "VasiaFirst";

		expectedException.expect(DataIntegrityViolationException.class);

		final UserImpl user = (UserImpl) userServiceImpl.create(nickname,
				"vasia@mail.ru");

		final UserImpl user2 = (UserImpl) userServiceImpl.create("VasiaSecond",
				"vasia123@mail.ru");

		user2.setNickname(nickname);
		userServiceImpl.update(user2);
	}

	@Test
	/**
	 * Create user and update it. Check nickname and email of update user.
	 */
	public void testUpdate() {
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
	}

	@Test
	/**
	 * Create token and set token to user. 
	 * Verify user.
	 * Check user and token after verifying.
	 * 
	 * @result user should be active and token should be deleted.
	 */
	public void testUserVerify() {
		/** create token and set token to user */
		final Token token = new Token();
		token.setType(TokenType.VERIFICATION);
		token.setToken(tokenServiceImpl.generateToken());

		UserImpl user = (UserImpl) userServiceImpl.create("Vasia",
				"vasia@mail.ru");

		token.setUser(user);
		tokenRepository.save(token);
		Set<Token> tokens = new HashSet<Token>();
		tokens.add(token);
		user.setTokens(tokens);

		user = (UserImpl) userServiceImpl.update(user);

		/** verify token */
		userServiceImpl.verify(token.getToken());

		/** user should be active */
		UserImpl resultUser = userRepository.findById(user.getId());
		assertTrue(resultUser.isActive());

		/** token should be deleted */
		Token resultToken = tokenRepository.findByToken(token.getToken());
		assertNull(resultToken);
	}
}
