package com.daw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.daw.dtos.AdvertiserDto;
import com.daw.dtos.ConverterDto;
import com.daw.model.Advertiser;
import com.daw.repositories.AdvertiserRepository;

@Service
public class ServiceAdvertiserImpl implements ServiceAdvertiser {

	@Autowired
	private AdvertiserRepository advertiserRepository;

	
	public AdvertiserRepository getAdvertiserRepository() {
		return advertiserRepository;
	}
	public void setAdvertiserRepository(AdvertiserRepository advertiserRepository) {
		this.advertiserRepository = advertiserRepository;
	}

	
	@Override
	public List<Advertiser> findByName(String name) {
		return advertiserRepository.findByName(name);
	}

	@Override
	public <S extends Advertiser> S save(S entity) {
		return advertiserRepository.save(entity);
	}

	@Override
	public Optional<Advertiser> findById(Long id) {
		return advertiserRepository.findById(id);
	}

	@Override
	public Page<AdvertiserDto> findAll(int page) {
		final int byPage = 5;
		return ConverterDto.getToDtoInstance().mapAll(advertiserRepository.findAll(PageRequest.of(page, byPage)),
				AdvertiserDto.class);
	}

	@Override
	public void deleteById(Long id) {
		advertiserRepository.deleteById(id);
	}

	@Override
	public void delete(Advertiser entity) {
		advertiserRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		advertiserRepository.deleteAll();
	}

	@Transactional
	@Override
	public Advertiser update(Long advertiserId, Advertiser updateAdvertiser) {
		return advertiserRepository.findById(advertiserId)
			.map(a -> {
				a.setName(updateAdvertiser.getName());
				a.setEmail(updateAdvertiser.getEmail());
				a.setAccountNumber(updateAdvertiser.getAccountNumber());
				return ConverterDto.getToDtoInstance().map(a, Advertiser.class);})
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}
