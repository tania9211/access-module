package org.access.impl;

import java.util.Calendar;
import java.util.Date;

import org.access.impl.entity.UserImpl;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
@Transactional
public class RoleServiceTest {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired RoleRepository roleRepository;
	
	private UserImpl user;
	
	@Before
	public void init() {
		user = new UserImpl();
		Date date = Calendar.getInstance().getTime();
		user.setDateCreate(date);
		user.setDateModify(date);
		user.setDeleted(false);
		user.setEmail("tania@mail.ru");
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname("kitty99");

		userRepository.save(user);
	}
	
	@Test
	public void test() {
		roleService.setRole(user, "admin");
		//roleRepository.findByUser(user);
	}
}
