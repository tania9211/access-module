package org.access.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.access.impl.entity.Role;
import org.access.impl.entity.User;
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
		User user = new User();
		user.setDateCreate("2345");
		user.setDateModify("456");
		user.setDeleted(false);
		user.setEmail(email);
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname(nickname);

		userRepository.save(user);
		final String roleName = "Some role name";

		Role role = new Role();
		role.setCreatorId(user.getId());
		role.setDateCreate("456");
		role.setDateModify("345");
		role.setDeleted(false);
		role.setName(roleName);
		role.setVersion(1L);

		roleRepository.save(role);

		Role resultRole = roleRepository.findByCreatorId(user.getId());
		assertEquals(resultRole.getName(), roleName);
		
		List<Role> roles = roleRepository.findByName(roleName);
		assertNotNull(roles);
	}
}
