package com.daw.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.daw.dtos.ConverterDto;
import com.daw.dtos.CreateReportDto;
import com.daw.dtos.ReportDto;
import com.daw.exceptions.NotFoundException;
import com.daw.model.Comment;
import com.daw.model.Report;
import com.daw.model.User;
import com.daw.repositories.CommentRepository;
import com.daw.repositories.ReportRepository;
import com.daw.repositories.UserRepository;


@Service
public class ServiceReportImpl implements ServiceReport {

	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;

	
	public ReportRepository getReportRepository() {
		return reportRepository;
	}
	public void setReportRepository(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}
	
	
	@Override
	public List<Report> findByCreateDate(LocalDate createDate) {
		return reportRepository.findByCreateDate(createDate);
	}
	@Override
	public List<Report> findByUser(User user) {
		return reportRepository.findByUser(user);
	}
	@Override
	public <S extends Comment> S save(S entity) {
		return commentRepository.save(entity);
	}
	@Override
	public Report saveReportWithComment(CreateReportDto reportDto, Long commentId, String alias) throws NotFoundException {
		User user = userRepository.findByAlias(alias).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
		Report report =	ConverterDto.getToModelInstance().map(reportDto, Report.class);
		Comment comment = commentRepository.findById(commentId).orElse(null);
		if (comment == null) {
			throw new NotFoundException();
		}
		report.setComment(comment);
		report.setUser(user);
		return reportRepository.save(report);
	}

	@Override
	public Optional<Report> findById(Long id) {
		return reportRepository.findById(id);
	}
	@Override
	public Page<ReportDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(reportRepository.findAll(PageRequest.of(page, byPage)), ReportDto.class);
	}
	@Override
	public void deleteById(Long id) {
		reportRepository.deleteById(id);
	}
	@Override
	public void delete(Report entity) {
		reportRepository.delete(entity);
	}
	@Override
	public void deleteAll() {
		reportRepository.deleteAll();
	}
	
	
}
