package com.daw.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>{

	Optional<Category> findByName(String name);
	
	Page<Category> findAll(Pageable p);
	
}
