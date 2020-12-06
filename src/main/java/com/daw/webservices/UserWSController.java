package com.daw.webservices;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateUserDto;
import com.daw.dtos.UserDto;
import com.daw.exceptions.NewUserWithDifferentPasswordsException;
import com.daw.exceptions.NewUserWithUniqueAliasException;
import com.daw.model.User;
import com.daw.services.ServiceUser;

@RestController
@RequestMapping("/users")
public class UserWSController {

	@Autowired
	private ServiceUser userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<UserDto> result = userService.findAll(page);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping(value = "/login", produces = { MediaType.APPLICATION_JSON_VALUE })
	public UserDto getUserProfile(Principal ppal) { // La Interfaz Principal me permite acceder al usuario logueado
		String loggedUserName = ppal.getName();
		User user = userService.findByAlias(loggedUserName)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (user.getUsername().equals(loggedUserName)) {
			return ConverterDto.getToDtoInstance().map(user, UserDto.class);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public UserDto getOneUser(@PathVariable("id") Long id, Principal p) {
		String loggedUsername = p.getName();
		User user = userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if (user.getUsername().equals(loggedUsername)) {
			return ConverterDto.getToDtoInstance().map(user, UserDto.class);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createNewUser(@Valid @RequestBody CreateUserDto newUser, BindingResult br) {
		if (!userService.findByAlias(newUser.getAlias()).isPresent()) {
			if (newUser.getPassword().contentEquals(newUser.getConfirm())) {
				if (!br.hasErrors()) {
					User user = userService.createUser(newUser);
					return ResponseEntity.status(HttpStatus.CREATED)
							.body(ConverterDto.getToDtoInstance().map(user, UserDto.class));
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
				}

			} else {
				throw new NewUserWithDifferentPasswordsException();
			}

		} else {
			throw new NewUserWithUniqueAliasException();
		}
	}

	@PutMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateUser(@Valid @PathVariable("id") Long id, @RequestBody UserDto updateUser,
			BindingResult br) {
		if (!br.hasErrors()) {
			return userService.findById(id).map(user -> {
				user.setName(updateUser.getName());
				user.setSurnames(updateUser.getSurnames());
				user.setAlias(updateUser.getAlias());
				user.setEmail(updateUser.getEmail());
				user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
				user.setProfilePicture(updateUser.getProfilePicture());
				user.setRegistrationDate(updateUser.getRegistrationDate());
				userService.save(user);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(ConverterDto.getToDtoInstance().map(user, UserDto.class));

			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		User user = userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		userService.delete(user);
		return ResponseEntity.noContent().build();
	}

}
