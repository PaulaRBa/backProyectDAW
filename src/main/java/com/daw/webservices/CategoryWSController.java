package com.daw.webservices;

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

import com.daw.dtos.CategoryDto;
import com.daw.dtos.ConverterDto;
import com.daw.dtos.PostDto;
import com.daw.model.Category;
import com.daw.model.Post;
import com.daw.services.ServiceCategory;
import com.daw.services.ServicePost;

@RestController
@RequestMapping("/categories")
public class CategoryWSController {

	@Autowired
	private ServiceCategory categoryService;
	@Autowired
	private ServicePost postService;

	// Categorias paginadas
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<CategoryDto>> getAllCategories(
			@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<CategoryDto> result = categoryService.findAll(page);
		if (result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	// Categorias sin paginar (las encesito para mostrar todas las categorías en la
	// barra de navegación)
	@GetMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getCategoriesList() {
		Iterable<Category> categories = categoryService.findAll();
		List<CategoryDto> categoriesList = ConverterDto.getToDtoInstance().mapAll(categories, CategoryDto.class);
		if (categoriesList.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(categoriesList);
		}
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public CategoryDto getOneCategory(@PathVariable("id") Long id) {
		Category c = categoryService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(c, CategoryDto.class);
	}

	// Posts de una Categoría
	@GetMapping(value = "{id}/posts", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<PostDto> getOneCategoryPosts(@PathVariable("id") Long id) {
		List<Post> p = postService.findByCategoryId(id);
		return ConverterDto.getToDtoInstance().mapAll(p, PostDto.class);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto newCategory, BindingResult br) {
		if (!br.hasErrors()) {
			Category c = categoryService.save(ConverterDto.getToModelInstance().map(newCategory, Category.class));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(ConverterDto.getToDtoInstance().map(c, CategoryDto.class));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	@PutMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryDto updateCategory,
			BindingResult br) {
		if (!br.hasErrors()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(ConverterDto.getToDtoInstance()
							.map(categoryService.update(id,
									ConverterDto.getToModelInstance().map(updateCategory, Category.class)),
									CategoryDto.class));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
		Category c = categoryService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		categoryService.delete(c);
		return ResponseEntity.noContent().build();
	}

}
