package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreatePostDto;
import com.daw.dtos.PostDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Category;
import com.daw.model.Post;
import com.daw.model.User;
import com.daw.repositories.CategoryRepository;
import com.daw.repositories.PostRepository;
import com.daw.repositories.UserRepository;

@Service
public class ServicePostImpl implements ServicePost {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;

	
	public PostRepository getPostRepository() {
		return postRepository;
	}
	public void setPostRepository(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	
	@Override
	public List<Post> findByEventDate(LocalDate eventDate) {
		return postRepository.findByEventDate(eventDate);
	}
	@Override
	public List<Post> findByEventName(String eventName) {
		return postRepository.findByEventName(eventName);
	}
	@Override
	public List<Post> findByTitle(String title) {
		return postRepository.findByTitle(title);
	}
	@Override
	public List<Post> findByPunctuaction(int punctuaction) {
		return postRepository.findByPunctuaction(punctuaction);
	}
	@Override
	public <S extends Post> S save(S entity) {
		return postRepository.save(entity);
	}
	@Override
	public Post savePostWithCategory(CreatePostDto postDto, Long categoryId, String alias) throws NotFoundException {
		User user = userRepository.findByAlias(alias).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		Post post = ConverterDto.getToModelInstance().map(postDto, Post.class);
		Category category = categoryRepository.findById(categoryId).orElse(null);
		if(category == null) {
			throw new NotFoundException();
		} else {
			post.setCategory(category);
			post.setUser(user);
			return postRepository.save(post);
		}
	}
	
	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}
	@Override
	public Page<PostDto> findAll(int page, int byPage) {
		return ConverterDto.getToDtoInstance().mapAll(postRepository.findAllByOrderByCreateDateDesc(PageRequest.of(page, byPage)), PostDto.class);
	}
	
	
	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}
	@Override
	public void delete(Post entity) {
		postRepository.delete(entity);
	}
	@Override
	public List<Post> findByCategoryId(long id) {
		return postRepository.findByCategoryId(id);
	}
	@Override
	public Page<PostDto> findAllQuery(int page, int byPage, String query) {
		return ConverterDto.getToDtoInstance().mapAll(postRepository.findAllQuery(query, PageRequest.of(page, byPage)), PostDto.class);
	}

		
}
