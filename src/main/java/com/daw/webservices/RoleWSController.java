package com.daw.webservices;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.daw.dtos.RoleDto;
import com.daw.model.Role;
import com.daw.services.ServiceRole;

@RestController
@RequestMapping("/roles")
public class RoleWSController {

	@Autowired
	private ServiceRole roleService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<RoleDto>> getAllRoles(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<RoleDto> result = roleService.findAll(page);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public RoleDto getOneRole(@PathVariable("id") Long id) {
		Role r = roleService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(r, RoleDto.class);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createRole(@Valid @RequestBody RoleDto newRole, BindingResult br) {
		if (!br.hasErrors()) {
			Role r = roleService.save(ConverterDto.getToModelInstance().map(newRole, Role.class));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(ConverterDto.getToDtoInstance().map(r, RoleDto.class));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	@PutMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public RoleDto updateRole(@PathVariable("id") Long id, @RequestBody RoleDto updateRole) {
		return roleService.findById(id).map(r -> {
			r.setName(updateRole.getName());
			roleService.save(r);
			return ConverterDto.getToDtoInstance().map(r, RoleDto.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteRole(@PathVariable("id") Long id) {
		Role r = roleService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		roleService.delete(r);
		return ResponseEntity.noContent().build();
	}
}
