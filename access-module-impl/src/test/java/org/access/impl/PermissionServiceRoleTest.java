package org.access.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.access.api.Level;
import org.access.api.exception.DataInsertionException;
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
@ContextConfiguration(locations = "classpath:test_spring_config.xml")
public class PermissionServiceRoleTest {
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
	//	long time = System.currentTimeMillis();
	//	user.setDateCreate(time);
	//	user.setDateModify(time);
		user.setDeleted(false);
		user.setEmail("tania@mail.ru");
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname("kitty99");
		user.setActive(true);

		userRepository.save(user);

		role = new RoleImpl();
		role.setCreatorId(user.getId());
	//	time = System.currentTimeMillis();
	//	user.setDateCreate(time);
//		user.setDateModify(time);
		role.setDeleted(false);
		role.setName("admin");
		role.setVersion(1L);

		roleRepository.save(role);

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
	 * Role has no permission. Check role permissions to one row and to all table.
	 * @result role has no permission
	 */
	public void testRoleHasNoPermission() {

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Human.TYPE, human1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Human.TYPE, Level.READ);
		assertFalse(permission);
	}

	@Test
	/**
	 * Role has permission to only one row. Check if role has permission to all table or another table.
	 * @result user has only one permission.
	 */
	public void testPermissionToRow() {
		try {
			permissionServiceImpl.setRolePermission(role.getName(), Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** check if role has greater level of permission */
		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal2.getId(), Level.READ);
		assertFalse(permission);

		/** check if role has permission to all table */
		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, null, Level.READ);
		assertFalse(permission);

		/** check if role has permission to another table */
		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, human1.getId(), Level.READ);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Human.TYPE, null, Level.READ);
		assertFalse(permission);
	}

	@Test
	/**
	 * Role has permission to animal table. Check if role has permissions to all rows in this table
	 * and if role has permission to any row of another table.
	 * @result role permission to all rows in animal table.
	 */
	public void testOneTablePermission() {

		try {
			permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
					null, Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal2.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, Level.READ_WRITE);
		assertTrue(permission);

		/** check if role has permission to another table */
		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Human.TYPE, human1.getId(), Level.READ_WRITE);
		assertFalse(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Human.TYPE, null, Level.READ_WRITE);
		assertFalse(permission);
	}

	@Test
	/**
	 * Role has permission to read and write. Check if role has permission to read and to delete.
	 * @result role has permission to read, but has no permission to delete.
	 * Give role permission to delete. Check if role has permission to read an write.
	 * @result role has these permission.
	 */
	public void testLevelOfPermission() {
		try {
			permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertFalse(permission);

		/** give role permission to delete */
		try {
			permissionServiceImpl.setRolePermission(role,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e) {
			e.printStackTrace();
		}

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE);
		assertTrue(permission);

		permission = permissionServiceImpl.checkRolePermission(role.getName(),
				Animal.TYPE, animal1.getId(), Level.READ_WRITE_DELETE);
		assertTrue(permission);
	}

	@Test
	/**
	 * Role has only one permission record to one object or has not record. 
	 * If role has permission to read and he set permission to write this permission should be updated.
	 * Method setRolePermission(Role role, Level level, UUID objectId,String type) should ignore repeated permissions.
	 * In this test role get repeated permissions to one object.
	 * @result method should work without exceptions.
	 */
	public void testSetUserPermission() {
		try {
			permissionServiceImpl.setRolePermission(role, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** get greater permission */
		try {
			permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		try {
			permissionServiceImpl.setRolePermission(role,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		/** get repeated permission */
		try {
			permissionServiceImpl.setRolePermission(role,
					Level.READ_WRITE_DELETE, animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		/** get decrease permission */
		try {
			permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}

		try {
			permissionServiceImpl.setRolePermission(role, Level.READ,
					animal1.getId(), Animal.TYPE);
		} catch (DataInsertionException e1) {
			e1.printStackTrace();
		}
	}

}
