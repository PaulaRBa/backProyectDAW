package com.daw.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.CategoryDto;
import com.daw.dtos.ConverterDto;
import com.daw.model.Category;
import com.daw.repositories.CategoryRepository;

@Service
public class ServiceCategoryImpl implements ServiceCategory {

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryRepository getCategoryRepository() {
		return categoryRepository;
	}
	public void setCategoryRepository(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	
	@Override
	public Optional<Category> findByName(String name) {
		return categoryRepository.findByName(name);
	}
	
	@Override
	public <S extends Category> S save(S entity) {
		return categoryRepository.save(entity);
	}
	
	@Override
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}
	
	@Override
	public Page<CategoryDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(categoryRepository.findAll(PageRequest.of(page, byPage)), CategoryDto.class);
	}
	
	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}
	
	@Override
	public void delete(Category entity) {
		categoryRepository.delete(entity);
	}
	
	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}
	
	@Override
	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	@Transactional
	@Override
	public Category update(Long id, Category updateCategory) {
		return categoryRepository.findById(id)
			.map(c -> {
				c.setColor(updateCategory.getColor());
				c.setName(updateCategory.getName());
				c.setDescription(updateCategory.getDescription());
				return ConverterDto.getToDtoInstance().map(c, Category.class);})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	
}
