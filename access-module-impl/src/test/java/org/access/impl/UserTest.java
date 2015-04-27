package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.access.impl.entity.User;
import org.access.impl.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
public class UserTest {
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testUserFind() {
		final String email = "tania@mail.ru";
		final String nickname = "kitty";
		User user = new User();
		final Date date = Calendar.getInstance().getTime();
		user.setDateCreate(date);
		user.setDateModify(date);
		user.setDeleted(false);
		user.setEmail(email);
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname(nickname);

		userRepository.save(user);

		User user1 = userRepository.findByEmail("tania@mail.ru");
		assertEquals(user1.getEmail(), email);

		User user2 = userRepository.findByNickname(nickname);
		assertEquals(user2.getNickname(), nickname);
	}
}
