package com.daw.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Report;
import com.daw.model.User;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long>{

	List<Report> findByCreateDate(LocalDate createDate);
	List<Report> findByUser(User user);
	Page<Report> findAll(Pageable p );
	
}
