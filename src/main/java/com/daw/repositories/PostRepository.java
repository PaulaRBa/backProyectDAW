package com.daw.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

	List<Post> findByEventDate(LocalDate eventDate);
	List<Post> findByEventName(String eventName);
	List<Post> findByTitle(String title);
	List<Post> findByPunctuaction (int punctuaction);
	List<Post> findByCategoryId(long id);
	
	Page<Post> findAll(Pageable p);
	
	//Consulta todos los posts ordenados por fecha de creación descendente
	Page<Post> findAllByOrderByCreateDateDesc(Pageable p);
	
	//Consulta todos los post que contengan la consulta buscada
	//OJO. Adviertase el uso de advanced like expressions in @Query
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:query% OR p.content LIKE %:query%")
	//@Query("SELECT p FROM Post p WHERE p.title LIKE ?1 OR p.content LIKE ?1")
	Page<Post> findAllQuery(String query, Pageable p);
	
}
