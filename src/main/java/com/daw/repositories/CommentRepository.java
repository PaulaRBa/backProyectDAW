package com.daw.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByCreateDate(LocalDate createDate);
	
	Page<Comment> findAll(Pageable p);
	
	List<Comment> findByPostId(long id);
		
}
