package org.access.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.access.api.Level;
import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.Permission;
import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.access.impl.entity.test.Animal;
import org.access.impl.entity.test.Human;
import org.access.impl.repository.PermissionRepository;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.access.impl.repository.test.AnimalRepository;
import org.access.impl.repository.test.HumanRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = "classpath:test_spring_config.xml")
public class PermissionServiceUserTest {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PermissionServiceImpl permissionServiceImpl;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private HumanRepository humanRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private PermissionRepository permissionRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private User user;
	private Role role;

	private Animal animal1;
	private Animal animal2;

	private Human human1;

	@Before
	public void init() {
		user = new User();
		user.setEmail("tania@mail.ru");
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname("kitty99");
		user.setActive(true);

		userRepository.save(user);

		animal1 = new Animal();
		animal1.setBreed("cat");
		animal1.setName("Kitty");

		animalRepository.save(animal1);

		animal2 = new Animal();
		animal2.setBreed("dog");
		animal2.setName("Tarzan");

		animalRepository.save(animal2);

		human1 = new Human();
		human1.setName("Vasia");
		human1.setNationality("Russian");

		humanRepository.save(human1);
	}

	@Test
	/**
	 * Set user permission and check it.
	 */
	public void testSetPermission() throws DataInsertionException {
		Permission permission = permissionServiceImpl.setUserPermission(
				user.getId(), Level.READ, animal1.getId(), Animal.TYPE);

		/** find user permission by id */
		Permission permission2 = permissionRepository.findById(permission
				.getId());
		assertNotNull(permission2);

		/** compare permissions fields */
		assertEquals(permission.getUser().getId(), permission2.getUser().getId());
		assertEquals(permission.getLevel(), permission2.getLevel());
		assertEquals(permission.getObjectId(), permission2.getObjectId());
	}

	@Test
	/**
	 * Set user permission and try to find it by different parameters.
	 * 
	 * @result permission should be found by different parameters.
	 */
	public void testFindPermission() throws DataInsertionException {
		Permission permission = permissionServiceImpl.setUserPermission(
				user.getId(), Level.READ, animal1.getId(), Animal.TYPE);
		Permission permission2 = null;

		/** find permission by level */
		List<Permission> permissions = permissionRepository
				.findByLevel(permission.getLevel());
		for (int i = 0; i < permissions.size(); i++) {
			if (permissions.get(i).getId().equals(permission.getId()))
				permission2 = permissions.get(i);
		}
		assertNotNull(permission2);

		/** find permission by objectId */
		permissions = permissionRepository.findByObjectId(permission
				.getObjectId());
		for (int i = 0; i < permissions.size(); i++) {
			if (permissions.get(i).getId().equals(permission.getId()))
				permission2 = permissions.get(i);
		}
		assertNotNull(permission2);

		/** find permission in all permissions list */
		permissions = permissionRepository.findAll();
		for (int i = 0; i < permissions.size(); i++) {
			if (permissions.get(i).getId().equals(permission.getId()))
				permission2 = permissions.get(i);
		}
		assertNotNull(permission2);
	}

	@Test
	/**
	 * User has no permission. Check user permissions to one row and to all table.
	 * 
	 * @result user has no permission
	 */
	public void testUserHasNoPermission() {

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Human.TYPE, human1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Human.TYPE, Level.READ);
		assertFalse(permission);
	}

	@Test
	/**
	 * User has permission to only one row. Check if user has permission to all table or another table.
	 * 
	 * @result user has only one permission.
	 */
	public void testPermissionToRow() throws DataInsertionException {
		permissionServiceImpl.setUserPermission(user.getId(), Level.READ,
				animal1.getId(), Animal.TYPE);

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** check if user has greater level of permission */
		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal2.getId(), Level.READ);
		assertFalse(permission);

		/** check if user has permission to all table */
		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, null, Level.READ);
		assertFalse(permission);

		/** check if user has permission to another table */
		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, human1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Human.TYPE, null, Level.READ);
		assertFalse(permission);
	}

	@Test
	/**
	 * User has permission to animal table. Check if user has permissions to all rows in this table
	 * and if user has permission to any row of another table.
	 * 
	 * @result user permission to all rows in animal table.
	 */
	public void testOneTablePermission() throws DataInsertionException {
		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE, null,
				Animal.TYPE);

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal2.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, Level.READ_WRITE);
		assertTrue(permission);

		/** check if user has permission to another table */
		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Human.TYPE, human1.getId(), Level.READ_WRITE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Human.TYPE, null, Level.READ_WRITE);
		assertFalse(permission);
	}

	@Test
	/**
	 * User has permission to read and write. Check if user has permission to read and to delete.
	 * @result user has permission to read, but has no permission to delete.
	 * 
	 * Give user permission to delete. Check if user has permission to read an write.
	 * @result user has these permission.
	 */
	public void testLevelOfPermission() throws DataInsertionException {
		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertFalse(permission);

		/** give user permission to delete */
		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertTrue(permission);
	}

	@Test
	/**
	 * User has only one permission record to one object or has not record. 
	 * If user has permission to read and he set permission to write this permission should be updated.
	 * Method setUserPermission(User user, Level level, UUID objectId,String type) should ignore repeated permissions.
	 * In this test user get repeated permissions to one object.
	 * 
	 * @result method should work without exceptions.
	 */
	public void testSetUserPermission() throws DataInsertionException {
		permissionServiceImpl.setUserPermission(user, Level.READ,
				animal1.getId(), Animal.TYPE);

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** get greater permission */

		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

		/** get repeated permission */

		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

		/** get decrease permission */

		permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

		permissionServiceImpl.setUserPermission(user, Level.READ,
				animal1.getId(), Animal.TYPE);

	}

	@Test
	/**
	 * Try to set permission for not active user.
	 * 
	 * @result expect DataInsertionException
	 */
	public void testSetNotActiveUserPermission() throws DataInsertionException {
		user.setActive(false);
		userServiceImpl.update(user);

		expectedException.expect(DataInsertionException.class);

		permissionServiceImpl.setUserPermission(user, Level.READ,
				animal1.getId(), Animal.TYPE);
	}

	@Test
	public void test() throws DataInsertionException {
		user.setActive(false);
		userServiceImpl.update(user);

		user.setActive(true);
		userServiceImpl.update(user);

		permissionServiceImpl.setUserPermission(user, Level.READ,
				animal1.getId(), Animal.TYPE);

		user.setActive(false);
		userServiceImpl.update(user);

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertFalse(permission);
	}

}
