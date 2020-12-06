package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daw.dtos.CommentDto;
import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateCommentDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;
import com.daw.model.Post;
import com.daw.model.User;
import com.daw.repositories.CommentRepository;
import com.daw.repositories.PostRepository;
import com.daw.repositories.UserRepository;

@Service
public class ServiceCommentImpl implements ServiceComment {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

		
	public CommentRepository getCommentRepository() {
		return commentRepository;
	}
	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	
	@Override
	public List<Comment> findByCreateDate(LocalDate createDate) {
		return commentRepository.findByCreateDate(createDate);
	}
	@Override
	public <S extends Comment> S save(S entity) {
		return commentRepository.save(entity);
	}
	
	@Override
	public Comment saveCommentWithPost(CreateCommentDto commentDto, Long postId, String alias) throws NotFoundException {
		User user = userRepository.findByAlias(alias).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		Comment comment = ConverterDto.getToModelInstance().map(commentDto, Comment.class);
		Post post = postRepository.findById(postId).orElse(null);
		if (post == null) {
			throw new NotFoundException();
		}
		comment.setPost(post);
		comment.setUser(user);
		return commentRepository.save(comment);
	}
	
	@Override
	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}
	@Override
	public Page<CommentDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(commentRepository.findAll(PageRequest.of(page, byPage)), CommentDto.class);
	}
	@Override
	public Iterable<Comment> findAll() {
		return commentRepository.findAll();
	}	
	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}
	@Override
	public void delete(Comment entity) {
		commentRepository.delete(entity);
	}
	
	@Override
	public Iterable<Comment> findByPostId(long id) {
		return commentRepository.findByPostId(id);
	}

}
