package com.itwill.springboot3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.springboot3.domain.Location;
import com.itwill.springboot3.repository.LocationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {
	
	private final LocationRepository locaRepo;
	
	public List<Location> read(){
		log.info("read()");
		
		return locaRepo.findAll();
	}
	
	public Location read(Integer id) {
		log.info("read(id={})",id);
		
		return locaRepo.findById(id).orElseGet(() -> null);
	}
}
