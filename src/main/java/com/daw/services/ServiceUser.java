package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.CreateUserDto;
import com.daw.dtos.UserDto;
import com.daw.model.User;

public interface ServiceUser {

	List<User> findByName(String name);

	Optional<User> findByAlias(String alias);

	<S extends User> S save(S entity);

	Optional<User> findById(Long id);

	Page<UserDto> findAll(int page);

	void deleteById(Long id);

	void delete(User entity);

	void deleteAll();

	User createUser(CreateUserDto newUser);

}