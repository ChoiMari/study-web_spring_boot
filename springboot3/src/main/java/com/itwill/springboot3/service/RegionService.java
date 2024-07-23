package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Region;
import com.itwill.springboot3.repository.RegionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegionService {
	private final RegionRepository regiRepo;
	
	public List<Region> read(){
		
		log.info("read()");
		
		return regiRepo.findAll();
	}
	
	public Region read(Integer id) {
		log.info("read(id={})",id);
		
		return regiRepo.findById(id).orElseGet(() -> null);
	}
}
