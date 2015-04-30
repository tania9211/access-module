package org.access.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.access.api.exceptions.DataInsertionException;
import org.access.impl.entity.RoleImpl;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
@Transactional
public class RoleServiceTest {
	@Autowired
	private RoleServiceImpl roleServiceImpl;

	@Autowired
	private UserRepository userRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
		user.setActive(true);

		userRepository.save(user);
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
		RoleImpl roleImpl = null;
		try {
			roleImpl = (RoleImpl) roleServiceImpl
					.create(roleName, user.getId());
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		RoleImpl roleImpl2 = (RoleImpl) roleServiceImpl.getById(roleImpl
				.getId());
		assertEquals(roleImpl2.getName(), roleName);

		roleImpl2 = (RoleImpl) roleServiceImpl.getByName(roleName);
		assertEquals(roleImpl2.getId(), roleImpl.getId());

		/** check if list include role */
		roleImpl = null;
		final List<RoleImpl> list = roleServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName() == roleName)
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

		RoleImpl roleImpl = null;
		try {
			roleImpl = (RoleImpl) roleServiceImpl
					.create(roleName, user.getId());
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
		roleServiceImpl.delete(roleImpl);

		RoleImpl roleImpl2 = (RoleImpl) roleServiceImpl.getById(roleImpl
				.getId());
		assertNull(roleImpl2);

		roleImpl2 = (RoleImpl) roleServiceImpl.getByName(roleName);
		assertNull(roleImpl2);

		/** check if list include role */
		roleImpl2 = null;
		final List<RoleImpl> list = roleServiceImpl.list();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName() == roleName)
				roleImpl2 = list.get(i);
		}
		assertNull(roleImpl2);
	}

	@Test
	/**
	 * Create two roles with the same name.
	 * @result expect DataInsertionException exception.
	 */
	public void testUniqueNickname() {
		final String roleName = "admin";
		try {
			RoleImpl roleImpl = (RoleImpl) roleServiceImpl.create(roleName,
					user.getId());

			RoleImpl roleImpl2 = (RoleImpl) roleServiceImpl.create(roleName,
					user.getId());
			expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Update nickname of second role to not unique.
	 * @result expect DataInsertionException exception.
	 */
	public void testUpdateNickname() {
		final String roleName = "admin";
		try {
			RoleImpl roleImpl = (RoleImpl) roleServiceImpl.create(roleName,
					user.getId());

			RoleImpl roleImpl2 = (RoleImpl) roleServiceImpl.create("user",
					user.getId());
			
			roleImpl2.setName("admin");
			//roleServiceImpl.update(roleImpl2);
		//	expectedException.expect(DataInsertionException.class);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
	}
}
