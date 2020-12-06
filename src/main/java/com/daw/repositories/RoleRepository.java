package com.daw.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	List<Role> findByName(String name);

	Page<Role> findAll(Pageable p);
	
}
