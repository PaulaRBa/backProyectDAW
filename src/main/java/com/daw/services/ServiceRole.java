package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.RoleDto;
import com.daw.model.Role;

public interface ServiceRole {

	List<Role> findByName(String name);

	<S extends Role> S save(S entity);

	Optional<Role> findById(Long id);

	Page<RoleDto> findAll(int page);

	void deleteById(Long id);

	void delete(Role entity);

	void deleteAll();

}