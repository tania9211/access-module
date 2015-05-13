package org.access.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test_spring_config.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RoleServiceTest {
	@Autowired
	private RoleServiceImpl roleServiceImpl;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private User user;

	@Before
	public void init() {
		user = new User();
		user.setDeleted(false);
		user.setEmail("tania@mail.ru");
		user.setHash("2345");
		user.setSalt("4567");
		user.setNickname("kitty99");
		user.setActive(true);

		userRepository.save(user);
	}

	@Test
	/**
	 * Create role and check all fields.
	 */
	public void testCreateRole() {
		final Role roleImpl = (Role) roleServiceImpl.create("admin",
				user.getId());
		final Role roleImpl2 = roleRepository.findById(roleImpl.getId());

		assertEquals(roleImpl.getId(), roleImpl2.getId());
		assertEquals(roleImpl.getName(), roleImpl2.getName());
		assertEquals(roleImpl.getCreatorId(), roleImpl2.getCreatorId());
		assertEquals(roleImpl.getVersion(), roleImpl2.getVersion());
		assertEquals(roleImpl.getUsers().size(), roleImpl2.getUsers().size());
	}

	@Test
	/**
	 * Update role and check all fields.
	 */
	public void testUpdateRole() {
		Role roleImpl = (Role) roleServiceImpl.create("admin",
				user.getId());
		roleImpl.setName("user");
		roleImpl = (Role) roleServiceImpl.update(roleImpl);
		final Role roleImpl2 = roleRepository.findById(roleImpl.getId());

		assertEquals(roleImpl.getId(), roleImpl2.getId());
		assertEquals(roleImpl.getName(), roleImpl2.getName());
		assertEquals(roleImpl.getCreatorId(), roleImpl2.getCreatorId());
		assertEquals(roleImpl.getVersion(), roleImpl2.getVersion());
		assertEquals(roleImpl.getUsers().size(), roleImpl2.getUsers().size());
	}

	@Test
	/**
	 * Create role and save. Then get role by it is name.
	 * 
	 * @result both name should be equals
	 * 
	 *         After that get this role by it id.
	 * @result id should be equals
	 */
	public void testGetRole() {
		final String roleName = "admin";

		Role roleImpl = (Role) roleServiceImpl.create(roleName,
				user.getId());

		Role roleImpl2 = (Role) roleServiceImpl.getById(roleImpl
				.getId());
		assertEquals(roleImpl2.getName(), roleName);

		roleImpl2 = (Role) roleServiceImpl.getByName(roleName);
		assertEquals(roleImpl2.getId(), roleImpl.getId());

		/** check if list include role */
		roleImpl = null;
		final List<Role> list = roleServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(roleName))
				roleImpl = list.get(i);
		}
		assertNotNull(roleImpl);
	}

	@Test
	/**
	 * Delete role and try to find role by id and by email. Also try to find
	 * this role in list.
	 * 
	 * @result it it impossible to find deleted role.
	 */
	public void testDeleteRole() {
		final String roleName = "admin";
		Role roleImpl = (Role) roleServiceImpl.create(roleName,
				user.getId());

		roleServiceImpl.delete(roleImpl);

		Role roleImpl2 = (Role) roleServiceImpl.getById(roleImpl
				.getId());
		assertNull(roleImpl2);

		roleImpl2 = (Role) roleServiceImpl.getByName(roleName);
		assertNull(roleImpl2);

		/** check if list include role */
		roleImpl2 = null;
		final List<Role> list = roleServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName() == roleName)
				roleImpl2 = list.get(i);
		}
		assertNull(roleImpl2);
	}

	@Test
	/**
	 * Create two roles with the same name.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUniqueNickname() {
		final String roleName = "admin";

		expectedException.expect(DataIntegrityViolationException.class);

		Role roleImpl = (Role) roleServiceImpl.create(roleName,
				user.getId());

		Role roleImpl2 = (Role) roleServiceImpl.create(roleName,
				user.getId());

	}

	@Test
	/**
	 * Update nickname of second role to not unique value.
	 * 
	 * @result expect DataIntegrityViolationException exception.
	 */
	public void testUpdateNickname() {
		final String roleName = "admin";

		expectedException.expect(DataIntegrityViolationException.class);

		Role roleImpl = (Role) roleServiceImpl.create(roleName,
				user.getId());

		Role roleImpl2 = (Role) roleServiceImpl.create("user",
				user.getId());

		roleImpl2.setName("admin");
		roleServiceImpl.update(roleImpl2);
	}
}
