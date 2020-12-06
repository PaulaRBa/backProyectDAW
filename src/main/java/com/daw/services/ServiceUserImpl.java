package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateUserDto;
import com.daw.dtos.UserDto;
import com.daw.model.Role;
import com.daw.model.User;
import com.daw.model.UserRole;
import com.daw.repositories.RoleRepository;
import com.daw.repositories.UserRepository;

@Service
public class ServiceUserImpl implements UserDetailsService, ServiceUser {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String alias) throws UsernameNotFoundException {
		User user = userRepository.findByAlias(alias)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return user;
	}

	@Override
	public User createUser(CreateUserDto userDto) {
		User user = ConverterDto.getToModelInstance().map(userDto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findByName("USER").get(0); // Establezco por defecto el ROLE_USER
		user.getRoles().add(new UserRole(user, role));
		return userRepository.save(user);
	}

	@Override
	public Page<UserDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(userRepository.findAll(PageRequest.of(page, byPage)),
				UserDto.class);
	}

	@Override
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	@Override
	public Optional<User> findByAlias(String alias) {
		return userRepository.findByAlias(alias);
	}

	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

}
