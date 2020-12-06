package com.daw.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class CategoryDto {

	private Long id;
	@NotEmpty
	@Pattern (regexp = "(^#?([a-f0-9]{6})$)")
	private String color;
	@NotEmpty
	private String name;
	private String description;
	
	
	public CategoryDto() {
		super();
	}
	public CategoryDto(Long id, String color, String name, String description) {
		super();
		this.id = id;
		this.color = color;
		this.name = name;
		this.description = description;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
