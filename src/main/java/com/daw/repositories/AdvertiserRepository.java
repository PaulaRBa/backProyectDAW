package com.daw.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.daw.model.Advertiser;

@Repository
public interface AdvertiserRepository extends CrudRepository<Advertiser, Long> {
	
	List<Advertiser> findByName(String name);
	
	Page<Advertiser> findAll(Pageable p);

}