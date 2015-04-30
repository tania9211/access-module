package org.access.impl;

import java.util.Calendar;
import java.util.Date;

import org.access.impl.entity.RoleImpl;
import org.access.impl.entity.UserImpl;
import org.access.impl.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	public void setRole(UserImpl user, String roleName) {
		RoleImpl role = new RoleImpl();

		role.setCreatorId(user.getId());
		final Date date = Calendar.getInstance().getTime();
		role.setDateCreate(date);
		role.setDateModify(date);
		role.setDeleted(false);
		role.setVersion(1L);

		role.setName(roleName);

		roleRepository.save(role);
	}

	public RoleImpl getRole() {
		return null;
	}

	public void deleteRole(RoleImpl role) {
		roleRepository.delete(role);
	}

	public void updateRole(RoleImpl role) { //?
		roleRepository.save(role);
	}
}
