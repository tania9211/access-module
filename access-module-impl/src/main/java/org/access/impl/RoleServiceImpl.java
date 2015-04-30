package org.access.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.access.api.RoleService;
import org.access.api.entity.Role;
import org.access.api.exceptions.DataInsertionException;
import org.access.impl.entity.RoleImpl;
import org.access.impl.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role create(String name, UUID creatorId)
			throws DataInsertionException {
		if (roleRepository.findByName(name) != null)
			throw new DataInsertionException();

		final RoleImpl roleImpl = new RoleImpl();
		final Date date = Calendar.getInstance().getTime();
		roleImpl.setDateCreate(date);
		roleImpl.setDateModify(date);
		roleImpl.setDeleted(false);
		roleImpl.setVersion(1L);

		roleImpl.setName(name);
		roleImpl.setCreatorId(creatorId);

		roleRepository.save(roleImpl);

		return roleImpl;
	}

	@Override
	public Role update(Role role) throws DataInsertionException {
		final RoleImpl roleImpl = (RoleImpl) role;
		if (roleRepository.findByName(roleImpl.getName()) != null)
			throw new DataInsertionException();
		
		return roleRepository.updateRole(roleImpl.getCreatorId(),
				roleImpl.getName(), roleImpl.getId());
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
