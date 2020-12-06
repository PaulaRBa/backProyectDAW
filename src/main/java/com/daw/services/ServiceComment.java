package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.CommentDto;
import com.daw.dtos.CreateCommentDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;

public interface ServiceComment {

	List<Comment> findByCreateDate(LocalDate createDate);

	<S extends Comment> S save(S entity);
	
	Comment saveCommentWithPost(CreateCommentDto commentDto, Long postId, String alias) throws NotFoundException;

	Optional<Comment> findById(Long id);

	void deleteById(Long id);

	void delete(Comment entity);

	Page<CommentDto> findAll(int page);
	
	Iterable<Comment> findAll();
	
	Iterable<Comment> findByPostId(long id);
	
}