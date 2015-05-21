package org.access.impl;

import org.access.ApplicationConfig;
import org.access.impl.entity.User;
import org.access.impl.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = ApplicationConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTEst {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Test
	public void test() {
		final User user = new User();

		user.setHash("1234");
		user.setSalt("1234");

		user.setNickname("34");
		user.setEmail("345");
		user.setActive(true);

		userRepository.save(user);

	}
}
