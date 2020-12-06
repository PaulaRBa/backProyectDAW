package com.daw.webservices;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.CommentDto;
import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateCommentDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;
import com.daw.services.ServiceComment;
import com.daw.services.ServicePost;
import com.daw.services.ServiceUser;

@RestController
@RequestMapping("/comments")
public class CommentWSController {

	@Autowired
	private ServiceComment commentService;
	@Autowired
	private ServiceUser userService;
	@Autowired
	private ServicePost postService;

	// Comentarios paginados
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<CommentDto>> getAllComments(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<CommentDto> result = commentService.findAll(page);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	// Comentarios sin paginar
	@GetMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getCommentsList() {
		Iterable<Comment> comments = commentService.findAll();
		List<CommentDto> commentsList = ConverterDto.getToDtoInstance().mapAll(comments, CommentDto.class);
		if (commentsList.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(commentsList);
		}
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public CommentDto getOneComment(@PathVariable("id") Long id) {
		Comment c = commentService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(c, CommentDto.class);
	}

	// Comentario asociado a un Post
	@PostMapping(value = "{postId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createCommentWithPost(@Valid @PathVariable("postId") Long postId,
			@RequestBody CreateCommentDto newComment, BindingResult br, Principal principal) {
		if (!br.hasErrors()) {
			try {
				Comment comment = commentService.saveCommentWithPost(newComment, postId, principal.getName());
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(ConverterDto.getToDtoInstance().map(comment, CommentDto.class));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	@PutMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public CommentDto updateComment(@PathVariable("id") long id, @RequestBody CommentDto updateComment) {
		return commentService.findById(id).map(c -> {
			c.setCreateDate(updateComment.getCreateDate());
			c.setContent(updateComment.getContent());
			c.setUser(userService.findByAlias(updateComment.getUserAlias()).get());
			c.setPost(postService.findById(updateComment.getPostId()).get());
			commentService.save(c);
			return ConverterDto.getToDtoInstance().map(c, CommentDto.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
		Comment c = commentService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		commentService.delete(c);
		return ResponseEntity.noContent().build();
	}

}
