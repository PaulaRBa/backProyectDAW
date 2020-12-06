package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daw.dtos.ConverterDto;
import com.daw.dtos.RoleDto;
import com.daw.model.Role;
import com.daw.repositories.RoleRepository;

@Service
public class ServiceRoleImpl implements ServiceRole {

	@Autowired
	private RoleRepository roleRepository;

	
	public RoleRepository getRoleRepository() {
		return roleRepository;
	}

	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<Role> findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public <S extends Role> S save(S entity) {
		return roleRepository.save(entity);
	}

	@Override
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}

	@Override
	public Page<RoleDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(roleRepository.findAll(PageRequest.of(page, byPage)),
				RoleDto.class);
	}

	@Override
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	@Override
	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		roleRepository.deleteAll();
	}

}
