package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.access.impl.entity.RoleImpl;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
public class RoleTest {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testRoleFind() {
		final String email = "tania@mail.ru";
		final String nickname = "kitty";
		UserImpl user = new UserImpl();
	//	final long time = System.currentTimeMillis();
	//	user.setDateCreate(time);
	//	user.setDateModify(time);
		user.setDeleted(false);
		user.setEmail(email);
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname(nickname);

		userRepository.save(user);
		final String roleName = "Some role name";

		RoleImpl role = new RoleImpl();
		role.setCreatorId(user.getId());
	//	final long time2 = System.currentTimeMillis();
	//	user.setDateCreate(time2);
	//	user.setDateModify(time2);
		role.setDeleted(false);
		role.setName(roleName);
		role.setVersion(1L);

		roleRepository.save(role);

		RoleImpl resultRole = roleRepository.findByCreatorId(user.getId());
		assertEquals(resultRole.getName(), roleName);
		
		RoleImpl roles = roleRepository.findByName(roleName);
		assertNotNull(roles);
	}
}
