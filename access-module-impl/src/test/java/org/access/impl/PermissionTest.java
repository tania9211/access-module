package org.access.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.access.impl.entity.Permission;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.PermissionRepository;
import org.access.impl.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring_config.xml")
@Transactional
public class PermissionTest {
	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testPermissionFind() {
		final UUID testUUID = UUID.randomUUID();
		final String email = "tania@mail.ru";
		final String nickname = "kitty";
		final String type = "some type";

		UserImpl user = new UserImpl();
		//final long time = System.currentTimeMillis();
	//	user.setDateCreate(time);
	//	user.setDateModify(time);
	//	user.setDeleted(false);
		user.setEmail(email);
		user.setHash("2345");
		user.setSalt("4567");
		user.setVersion(2L);
		user.setNickname(nickname);

		userRepository.save(user);

		Permission permission = new Permission();
	//	final long time2 = System.currentTimeMillis();
//		user.setDateCreate(time2);
	//	user.setDateModify(time2);
		permission.setDeleted(false);
		permission.setLevel((byte) 2);
		permission.setObjectId(testUUID);
		permission.setType(type);
		permission.setUser(user);
		permission.setVersion(2L);

		permissionRepository.save(permission);

		List<Permission> resultPermission = permissionRepository
				.findByObjectId(testUUID);
		
	//	assertEquals(resultPermission.getType(), type);
	}
	
	@Test
	public void testLazyLoading() {
		final UUID testUUID = UUID.randomUUID();
		final String email = "tania@mail.ru";
		final String nickname = "kitty";
		final String type = "some type";

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

		Permission permission = new Permission();
	//	final long time3 = System.currentTimeMillis();
	//	user.setDateCreate(time3);
	//	user.setDateModify(time3);
		permission.setDeleted(false);
		permission.setLevel((byte) 2);
		permission.setObjectId(testUUID);
		permission.setType(type);
		permission.setUser(user);
		permission.setVersion(2L);

		permissionRepository.save(permission);

		List<Permission> resultPermission = permissionRepository
				.findByObjectId(testUUID);
	}
}
