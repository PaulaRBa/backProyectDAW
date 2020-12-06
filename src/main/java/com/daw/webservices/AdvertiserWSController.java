package com.daw.webservices;

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

import com.daw.dtos.AdvertiserDto;
import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateAdvertiserDto;
import com.daw.model.Advertiser;
import com.daw.services.ServiceAdvertiser;

@RestController
@RequestMapping("/advertisers")
public class AdvertiserWSController {

	@Autowired
	private ServiceAdvertiser advertiserService;
	
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<AdvertiserDto>> getAllAdvertisers(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<AdvertiserDto> result = advertiserService.findAll(page);
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}

	@GetMapping(value="{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public AdvertiserDto getOneAdvertiser(@PathVariable("id") Long id) {
		Advertiser a = advertiserService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(a, AdvertiserDto.class);
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createAdvertiser(@Valid @RequestBody CreateAdvertiserDto newAdvertiser, BindingResult br) {
		if(!br.hasErrors()) {
			Advertiser a = advertiserService.save(ConverterDto.getToModelInstance().map(newAdvertiser, Advertiser.class));
			return ResponseEntity.status(HttpStatus.CREATED).body(ConverterDto.getToDtoInstance().map(a, AdvertiserDto.class));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}		
	}
	
	@PutMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> updateAdvertiser(@PathVariable("id") Long id, @Valid @RequestBody CreateAdvertiserDto updateAdvertiser, BindingResult br) {
		if(!br.hasErrors()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(ConverterDto.getToDtoInstance().map(advertiserService.update(id, ConverterDto.getToModelInstance().map(updateAdvertiser, Advertiser.class)), AdvertiserDto.class));		
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteAdvertiser(@PathVariable("id") Long id) {
		Advertiser a = advertiserService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		advertiserService.delete(a);
		return ResponseEntity.noContent().build();
	}

}
