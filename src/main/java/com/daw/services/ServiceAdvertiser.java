package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.daw.dtos.AdvertiserDto;
import com.daw.model.Advertiser;

public interface ServiceAdvertiser {

	List<Advertiser> findByName(String name);

	<S extends Advertiser> S save(S entity);

	Optional<Advertiser> findById(Long id);

	Page<AdvertiserDto> findAll(int page);

	void deleteById(Long id);

	void delete(Advertiser entity);

	Advertiser update(Long advertiserId, Advertiser updateAdvertiser);

	void deleteAll();

}