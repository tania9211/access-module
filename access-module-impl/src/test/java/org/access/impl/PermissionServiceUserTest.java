package org.access.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.access.api.Level;
import org.access.api.exceptions.DataInsertionException;
import org.access.impl.entity.RoleImpl;
import org.access.impl.entity.UserImpl;
import org.access.impl.entity.test.Animal;
import org.access.impl.entity.test.Human;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.access.impl.repository.test.AnimalRepository;
import org.access.impl.repository.test.HumanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = "classpath:spring_config.xml")
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

	private UserImpl user;
	private RoleImpl role;

	private Animal animal1;
	private Animal animal2;

	private Human human1;

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
	 * User has no permission. Check user permissions to one row and to all table.
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
	 * @result user has only one permission.
	 */
	public void testPermissionToRow() {
		try {
			permissionServiceImpl.setUserPermission(user.getId(), Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

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
	 * @result user permission to all rows in animal table.
	 */
	public void testOneTablePermission() {
		try {
			permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
					null, Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

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
	 * Give user permission to delete. Check if user has permission to read an write.
	 * @result user has these permission.
	 */
	public void testLevelOfPermission() {
		try {
			permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

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
		try {
			permissionServiceImpl.setUserPermission(user,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

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
	 * @result method should work without exceptions.
	 */
	public void testSetUserPermission() {
		try {
			permissionServiceImpl.setUserPermission(user, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** get greater permission */
		try {
			permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		try {
			permissionServiceImpl.setUserPermission(user,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		/** get repeated permission */
		try {
			permissionServiceImpl.setUserPermission(user,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		/** get decrease permission */
		try {
			permissionServiceImpl.setUserPermission(user, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		try {
			permissionServiceImpl.setUserPermission(user, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Check permissions for not active user.
	 * @result not active user has no permissions.
	 */
	public void testInActiveUser() {
		user.setActive(false);

		try {
			permissionServiceImpl.setUserPermission(user, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkUserPermission(
				user.getId(), Animal.TYPE, animal1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, null, Level.READ);
		assertFalse(permission);

		user.setActive(true);

		try {
			permissionServiceImpl.setUserPermission(user, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		user.setActive(false);

		permission = permissionServiceImpl.checkUserPermission(user.getId(),
				Animal.TYPE, animal1.getId(), Level.READ);
		assertFalse(permission);
	}

}
