package org.access.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.access.api.RoleService;
import org.access.api.entity.Role;
import org.access.api.exception.DataInsertionException;
import org.access.impl.entity.RoleImpl;
import org.access.impl.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role create(String name, UUID creatorId) {

		final RoleImpl roleImpl = new RoleImpl();
		//final long time = System.currentTimeMillis();
	//	roleImpl.setDateCreate(time);
	//	roleImpl.setDateModify(time);
		roleImpl.setDeleted(false);
	//	roleImpl.setVersion(1L);

		roleImpl.setName(name);
		roleImpl.setCreatorId(creatorId);

		return roleRepository.save(roleImpl);
	}

	@Override
	public Role update(Role role) {
		return roleRepository.save((RoleImpl) role);
	}

	@Override
	public void delete(Role role) {
		roleRepository.delete((RoleImpl) role);
	}

	@Override
	public Role getByName(String roleName) {
		return (RoleImpl) roleRepository.findByName(roleName);
	}

	@Override
	public Role getById(UUID id) {
		return (RoleImpl) roleRepository.findById(id);
	}

	@Override
	public List<RoleImpl> list() {
		return roleRepository.findAll();
	}
}
