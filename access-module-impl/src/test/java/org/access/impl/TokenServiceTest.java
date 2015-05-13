package org.access.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.access.api.TokenType;
import org.access.api.annotation.Transactional;
import org.access.impl.entity.Token;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.TokenRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:test_spring_config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class TokenServiceTest {
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private TokenServiceImpl tokenServiceImpl;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RunTracsational runTracsational;

	private UserImpl user1;
	private UserImpl user2;
	private Token token1;
	private Token token2;

	@Before
	public void init() {
		user1 = new UserImpl();
		user1.setEmail("tania@mail.ru");
		user1.setHash("2345");
		user1.setSalt("4567");
		user1.setNickname("kitty99");
		userRepository.save(user1);

		user2 = new UserImpl();
		user2.setEmail("tania123@mail.ru");
		user2.setHash("2345");
		user2.setSalt("4567");
		user2.setNickname("kitty991");
		userRepository.save(user2);

		token1 = new Token();
		token1.setType(TokenType.VERIFICATION);
		token1.setToken(tokenServiceImpl.generateToken());

		token2 = new Token();
		token2.setToken(tokenServiceImpl.generateToken());
		token2.setType(TokenType.VERIFICATION);
	}

	@Test
	public void testUniqueToken() {
		token1.setUser(user1);
		tokenRepository.save(token1);

		token1.setUser(user2);
		tokenRepository.save(token1);
	}

	@Test
	public void testAnnotation() {
		runTracsational.run();
	}

	@Transactional
	public void blas() {

	}

	@Transactional(enabled = false)
	public void tyty() {

	}

	@Transactional(enabled = true)
	public void tyu() {

	}
}
