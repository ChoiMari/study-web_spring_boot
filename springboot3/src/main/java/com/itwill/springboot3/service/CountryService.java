package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Country;
import com.itwill.springboot3.repository.CountryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryService {
	
	private final CountryRepository ctyRepo;
	
	public List<Country> read(){
		log.info("read()");
		
		return ctyRepo.findAll();
	}
	
	public Country read(String id) {
		log.info("read(id={})",id);
		
		return ctyRepo.findById(id).orElseGet(() -> null);
	}
	
}
