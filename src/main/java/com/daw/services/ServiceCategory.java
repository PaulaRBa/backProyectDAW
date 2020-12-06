package com.daw.services;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.CategoryDto;
import com.daw.model.Category;

public interface ServiceCategory {

	Optional<Category> findByName(String name);

	<S extends Category> S save(S entity);

	Optional<Category> findById(Long id);

	Page<CategoryDto> findAll(int page);
	
	Iterable<Category> findAll();
	
	Category update(Long id, Category updateCategory);

	void deleteById(Long id);

	void delete(Category entity);
	
	void deleteAll();


}