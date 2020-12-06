package com.daw.dtos;

import javax.validation.constraints.NotEmpty;

public class RoleDto {

	private Long id;
	@NotEmpty
	private String name;
	
	
	public RoleDto() {
		super();
	}
	public RoleDto(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
