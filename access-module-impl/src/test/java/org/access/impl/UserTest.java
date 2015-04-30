package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.access.impl.entity.UserImpl;
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
	}
}
