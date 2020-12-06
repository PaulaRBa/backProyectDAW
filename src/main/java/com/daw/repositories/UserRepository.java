package com.daw.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByName(String name);
	Page<User> findAll(Pageable p);	
	Optional<User> findByAlias(String alias);
}
