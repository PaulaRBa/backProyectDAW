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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.CommentDto;
import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreatePostDto;
import com.daw.dtos.PostDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;
import com.daw.model.Post;
import com.daw.services.ServiceComment;
import com.daw.services.ServicePost;

@RestController
@RequestMapping("/posts")
public class PostWSController {

	@Autowired
	private ServicePost postService;

	@Autowired
	private ServiceComment commentService;

	// Obtener todos los Post o, en caso de consulta, los que se correspondan con la
	// consulta buscada
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<PostDto>> getAllPosts(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "byPage", defaultValue = "4") int byPage,
			@RequestParam(value = "q", defaultValue = "") String query) {
		byPage = Math.min(5, byPage); // Para evitar saltarse la paginación
		Page<PostDto> result;
		if (query.isEmpty()) {
			result = postService.findAll(page, byPage);
		} else {
			// Adviertase el uso de advanced like expressions in @Query
			// Una opcion: result = postService.findAllQuery(page, byPage, "%" + query + "%");
			// Otra opcion:
			result = postService.findAllQuery(page, byPage, query);

		}
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public PostDto getOnePost(@PathVariable("id") Long id) {
		Post p = postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(p, PostDto.class);
	}

	// Comentarios de cada Post
	@GetMapping(value = "{id}/comments", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getCommentsOfOnePost(@PathVariable Long id) {
		Iterable<Comment> comments = commentService.findByPostId(id);
		List<CommentDto> commentsList = ConverterDto.getToDtoInstance().mapAll(comments, CommentDto.class);
		return ResponseEntity.ok(commentsList);
	}

	// Posts con asignación de Categoría
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createPostWithCategory(@Valid @RequestBody CreatePostDto newPost, BindingResult br,
			Principal principal) {
		if (!br.hasErrors()) {
			try {
				Post post = postService.savePostWithCategory(newPost, newPost.getCategoryId(), principal.getName());
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(ConverterDto.getToDtoInstance().map(post, PostDto.class));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	/* Lo necesito ???
	 * @PutMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE},
	 * consumes = {MediaType.APPLICATION_JSON_VALUE}) public PostDTO
	 * updatePost(@PathVariable("id") Long id, @RequestBody PostDTO updatePost) {
	 * return postService.findById(id) .map( p -> {
	 * p.setTitle(updatePost.getTitle()); p.setPostDate(updatePost.getPostDate());
	 * //?? p.setEventDate(updatePost.getEventDate());
	 * p.setEventName(updatePost.getEventName());
	 * p.setContent(updatePost.getContent());
	 * p.setPunctuaction(updatePost.getPunctuaction()); //?? La puntuación no se
	 * puede modificar??
	 * p.setCategory(categoryService.findById(updatePost.getCategoryId()).get());
	 * //?? p.setUser(userService.findByAlias(updatePost.getUserAlias()).get()); //
	 * ?? //ME FALTA LA IMAGEN postService.save(p); return
	 * ConverterDTO.getToDTOInstance().map(p, PostDTO.class); }) .orElseThrow(() ->
	 * new ResponseStatusException(HttpStatus.NOT_FOUND)); }
	 */

	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
		Post p = postService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		postService.delete(p);
		return ResponseEntity.noContent().build();
	}

}
