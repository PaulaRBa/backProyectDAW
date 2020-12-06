package com.daw.webservices;

import java.security.Principal;

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

import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateReportDto;
import com.daw.dtos.ReportDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Report;
import com.daw.services.ServiceReport;

@RestController
@RequestMapping("/reports")
public class ReportWSController {

	@Autowired
	private ServiceReport reportService;
	
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Page<ReportDto>> getAllReports(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<ReportDto> result = reportService.findAll(page);
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ReportDto getOneReport(@PathVariable("id") Long id) {
		Report r = reportService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return ConverterDto.getToDtoInstance().map(r, ReportDto.class);
	}	

	//Crear Denuncia asociada a un Comentario
	@PostMapping(value = "{commentId}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> createReportWithComment(@Valid @PathVariable("commentId") Long commentId, @RequestBody CreateReportDto newReport, BindingResult br, Principal principal) {
		if(!br.hasErrors()) {
			try {
				Report report =  reportService.saveReportWithComment(newReport, commentId, principal.getName());
				return ResponseEntity.status(HttpStatus.CREATED).body(ConverterDto.getToDtoInstance().map(report, ReportDto.class));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(br.getAllErrors());
		}
	}	
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<?> deleteReport(@PathVariable("id") Long id) {
		Report r = reportService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		reportService.delete(r);
		return ResponseEntity.noContent().build();
	}
}
