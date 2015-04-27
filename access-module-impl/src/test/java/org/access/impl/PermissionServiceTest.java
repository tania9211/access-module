package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.access.impl.entity.Role;
import org.access.impl.entity.User;
import org.access.impl.entity.test.Animal;
import org.access.impl.repository.RoleRepository;
import org.access.impl.repository.UserRepository;
import org.access.impl.repository.test.AnimalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
public class PermissionServiceTest {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private AnimalRepository animalRepository;

	private User user;
	private Role role;

	private User secondUser;
	private Role secondRole;
	private User thirdUser;

	private Animal catAnimal;
	private Animal dogAnimal;

	@Before
	public void init() {
		user = new User();
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

		secondUser = new User();
		date = Calendar.getInstance().getTime();
		secondUser.setDateCreate(date);
		secondUser.setDateModify(date);
		secondUser.setDeleted(false);
		secondUser.setEmail("user2@mail.ru");
		secondUser.setHash("2345");
		secondUser.setSalt("4567");
		secondUser.setVersion(2L);
		secondUser.setNickname("petir34");

		userRepository.save(secondUser);

		thirdUser = new User();
		date = Calendar.getInstance().getTime();
		thirdUser.setDateCreate(date);
		thirdUser.setDateModify(date);
		thirdUser.setDeleted(false);
		thirdUser.setEmail("userKatty@mail.ru");
		thirdUser.setHash("2345");
		thirdUser.setSalt("4567");
		thirdUser.setVersion(2L);
		thirdUser.setNickname("katty45");

		userRepository.save(thirdUser);

		role = new Role();
		role.setCreatorId(user.getId());
		date = Calendar.getInstance().getTime();
		role.setDateCreate(date);
		role.setDateModify(date);
		role.setDeleted(false);
		role.setName("admin");
		role.setVersion(1L);

		roleRepository.save(role);

		catAnimal = new Animal();
		catAnimal.setBreed("cat");
		catAnimal.setName("Kitty");

		animalRepository.save(catAnimal);

		dogAnimal = new Animal();
		dogAnimal.setBreed("dog");
		dogAnimal.setName("Tarzan");

		animalRepository.save(dogAnimal);

		secondRole = new Role();
		secondRole.setCreatorId(secondUser.getId());
		date = Calendar.getInstance().getTime();
		secondRole.setDateCreate(date);
		secondRole.setDateModify(date);
		secondRole.setDeleted(false);
		secondRole.setName("standart");
		secondRole.setVersion(1L);

		roleRepository.save(secondRole);
	}

	@Test
	public void testUserPermission() {
		permissionService.setUserPermission(user, (byte) 1, catAnimal.getId(),
				catAnimal.toString());

		// give permission to all table
		permissionService.setUserPermission(user, (byte) 1, null,
				catAnimal.toString());

		// add animals after set permissions
		final Animal pigAminal = new Animal();
		pigAminal.setBreed("pig");
		pigAminal.setName("Becon");

		animalRepository.save(pigAminal);

		final boolean catPermission = permissionService.checkUserPermission(
				user, catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		final boolean animalPermisiion = permissionService.checkUserPermission(
				user, catAnimal.toString(), null);
		assertTrue(animalPermisiion);

		final boolean dogPermission = permissionService.checkUserPermission(
				user, dogAnimal.toString(), dogAnimal.getId());
		assertTrue(dogPermission);

		final boolean pigPermission = permissionService.checkUserPermission(
				user, pigAminal.toString(), pigAminal.getId());
		assertTrue(pigPermission);
	}

	@Test
	public void testSecondUser() {
		permissionService.setUserPermission(secondUser, (byte) 1,
				catAnimal.getId(), catAnimal.toString());

		final boolean catPermission = permissionService.checkUserPermission(
				secondUser, catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		boolean dogPermission = permissionService.checkUserPermission(
				secondUser, dogAnimal.toString(), dogAnimal.getId());
		assertFalse(dogPermission);

		permissionService.setUserPermission(secondUser, (byte) 1,
				dogAnimal.getId(), dogAnimal.toString());

		dogPermission = permissionService.checkUserPermission(secondUser,
				dogAnimal.toString(), dogAnimal.getId());
		assertTrue(dogPermission);
	}

	@Test
	public void testRolePermission() {
		permissionService.setRolePermission(role, (byte) 1, catAnimal.getId(),
				catAnimal.toString());

		// write the same permission
		permissionService.setRolePermission(role, (byte) 1, catAnimal.getId(),
				catAnimal.toString());

		// give permission to all table
		permissionService.setRolePermission(role, (byte) 1, null,
				catAnimal.toString());

		// add animals after set permissions
		final Animal pigAminal = new Animal();
		pigAminal.setBreed("pig");
		pigAminal.setName("Becon");

		animalRepository.save(pigAminal);

		final boolean catPermission = permissionService.checkRolePermission(
				role, catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		final boolean animalPermisiion = permissionService.checkRolePermission(
				role, catAnimal.toString(), null);
		assertTrue(animalPermisiion);

		final boolean dogPermission = permissionService.checkRolePermission(
				role, dogAnimal.toString(), dogAnimal.getId());
		assertTrue(dogPermission);

		final boolean pigPermission = permissionService.checkRolePermission(
				role, pigAminal.toString(), pigAminal.getId());
		assertTrue(pigPermission);
	}

	@Test
	public void testSecondRolePermission() {
		permissionService.setRolePermission(secondRole, (byte) 1,
				catAnimal.getId(), catAnimal.toString());

		final boolean catPermission = permissionService.checkRolePermission(
				secondRole, catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		boolean dogPermission = permissionService.checkRolePermission(
				secondRole, dogAnimal.toString(), dogAnimal.getId());
		assertFalse(dogPermission);

		permissionService.setRolePermission(secondRole, (byte) 1,
				dogAnimal.getId(), dogAnimal.toString());

		dogPermission = permissionService.checkRolePermission(secondRole,
				dogAnimal.toString(), dogAnimal.getId());
		assertTrue(dogPermission);
	}

	@Test
	public void testAddSamePermission() {
		boolean catPermission = permissionService.checkUserPermission(
				thirdUser, catAnimal.toString(), catAnimal.getId());
		assertFalse(catPermission);

		permissionService.setUserPermission(thirdUser, (byte) 1,
				catAnimal.getId(), catAnimal.toString());

		catPermission = permissionService.checkUserPermission(thirdUser,
				catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		permissionService.setUserPermission(thirdUser, (byte) 1,
				catAnimal.getId(), catAnimal.toString());

		catPermission = permissionService.checkUserPermission(thirdUser,
				catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		permissionService.setUserPermission(thirdUser, (byte) 1, null,
				catAnimal.toString());

		catPermission = permissionService.checkUserPermission(thirdUser,
				catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);

		permissionService.setUserPermission(thirdUser, (byte) 1,
				catAnimal.getId(), catAnimal.toString());

		catPermission = permissionService.checkUserPermission(thirdUser,
				catAnimal.toString(), catAnimal.getId());
		assertTrue(catPermission);
	}
}
