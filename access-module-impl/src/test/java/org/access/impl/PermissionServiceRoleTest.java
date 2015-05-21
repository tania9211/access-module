package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.access.ApplicationConfig;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = ApplicationConfig.class)
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

	@Autowired
	private PermissionRepository permissionRepository;

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
		user.setNickname("kitty99");
		user.setActive(true);

		userRepository.save(user);

		role = new Role();
		role.setCreatorId(user.getId());
		role.setName("admin");

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
	 * Set role permission and check it.
	 */
	public void testSetPermission() throws DataInsertionException {
		Permission permission = permissionServiceImpl.setRolePermission(
				role.getName(), Level.READ, animal1.getId(), Animal.TYPE);

		/** find role permission by id */
		Permission permission2 = permissionRepository.findById(permission
				.getId());
		assertNotNull(permission2);

		/** compare permissions fields */
		assertEquals(permission.getId(), permission2.getId());
		assertEquals(permission.getRole().getId(), permission2.getRole()
				.getId());
		assertEquals(permission.getObjectId(), permission2.getObjectId());
	}

	@Test
	/**
	 * Set role permission and try to find it by different parameters.
	 * 
	 * @result permission should be found by different parameters.
	 */
	public void testFindPermission() throws DataInsertionException {
		Permission permission = permissionServiceImpl.setRolePermission(
				role.getName(), Level.READ, animal1.getId(), Animal.TYPE);

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
	 * Role has no permission. Check role permissions to one row and to all table.
	 * 
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
	 * 
	 * @result user has only one permission.
	 */
	public void testPermissionToRow() throws DataInsertionException {
		permissionServiceImpl.setRolePermission(role.getName(), Level.READ,
				animal1.getId(), Animal.TYPE);

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
	 * 
	 * @result role permission to all rows in animal table.
	 */
	public void testOneTablePermission() throws DataInsertionException {
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE, null,
				Animal.TYPE);

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
	 * 
	 * @result role has permission to read, but has no permission to delete.
	 * 
	 * Give role permission to delete. Check if role has permission to read an write.
	 * 
	 * @result role has these permission.
	 */
	public void testLevelOfPermission() throws DataInsertionException {
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

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
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

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
	 * 
	 * @result method should work without exceptions.
	 */
	public void testSetUserPermission() throws DataInsertionException {
		permissionServiceImpl.setRolePermission(role, Level.READ,
				animal1.getId(), Animal.TYPE);

		boolean permission = permissionServiceImpl.checkRolePermission(
				role.getName(), Animal.TYPE, animal1.getId(), Level.READ);
		assertTrue(permission);

		/** get greater permission */
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

		/** get repeated permission */
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE_DELETE,
				animal1.getId(), Animal.TYPE);

		/** get decrease permission */
		permissionServiceImpl.setRolePermission(role, Level.READ_WRITE,
				animal1.getId(), Animal.TYPE);

		permissionServiceImpl.setRolePermission(role, Level.READ,
				animal1.getId(), Animal.TYPE);

	}
}
