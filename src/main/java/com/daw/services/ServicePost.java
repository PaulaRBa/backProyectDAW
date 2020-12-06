package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.CreatePostDto;
import com.daw.dtos.PostDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Post;

public interface ServicePost {

	List<Post> findByEventDate(LocalDate eventDate);

	List<Post> findByEventName(String eventName);

	List<Post> findByTitle(String title);

	List<Post> findByPunctuaction(int punctuaction);

	<S extends Post> S save(S entity);

	Post savePostWithCategory(CreatePostDto postDto, Long categoryId, String alias) throws NotFoundException;

	Optional<Post> findById(Long id);

	Page<PostDto> findAll(int page, int byPage);

	Page<PostDto> findAllQuery(int page, int byPage, String query);

	void deleteById(Long id);

	void delete(Post entity);

	List<Post> findByCategoryId(long id);

}