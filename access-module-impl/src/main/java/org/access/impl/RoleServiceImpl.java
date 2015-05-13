package org.access.impl;

import java.util.List;
import java.util.UUID;

import org.access.api.RoleService;
import org.access.impl.entity.Role;
import org.access.impl.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role create(String name, UUID creatorId) {

		final Role roleImpl = new Role();
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
		return roleRepository.save((Role) role);
	}

	@Override
	public void delete(Role role) {
		roleRepository.delete((Role) role);
	}

	@Override
	public Role getByName(String roleName) {
		return (Role) roleRepository.findByName(roleName);
	}

	@Override
	public Role getById(UUID id) {
		return (Role) roleRepository.findById(id);
	}

	@Override
	public List<Role> list() {
		return roleRepository.findAll();
	}
}
