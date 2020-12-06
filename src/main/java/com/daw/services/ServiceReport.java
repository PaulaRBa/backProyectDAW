package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.CreateReportDto;
import com.daw.dtos.ReportDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;
import com.daw.model.Report;
import com.daw.model.User;

public interface ServiceReport {

	List<Report> findByCreateDate(LocalDate createDate);

	List<Report> findByUser(User user);

	<S extends Comment> S save(S entity);
	
	Report saveReportWithComment(CreateReportDto reportDto, Long commentId, String alias) throws NotFoundException;

	Optional<Report> findById(Long id);

	Page<ReportDto> findAll(int page);

	void deleteById(Long id);

	void delete(Report entity);

	void deleteAll();

}