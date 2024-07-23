package com.itwill.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Location;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class LocationRepositoryTest {
	
	@Autowired
	private LocationRepository locaRepo;
	
	//@Test
	public void testDependencyInjection() {
		assertThat(locaRepo).isNotNull();
		
		log.info("locaRepo={}",locaRepo);
	}
	
	//@Test
	public void testFindAll() {
		long count = locaRepo.count();
		assertThat(count).isEqualTo(23L);
		
		List<Location> list = locaRepo.findAll();
		log.info("locations={}",list.get(0));
	}
	
	@Test
	@Transactional //-> lazy로 해놨으면 꼭 이거 있어야 test가 됨.
	public void testFindById() {
		Location location = locaRepo.findById(2200).orElseThrow();
		log.info("location={}",location);
		log.info("countries={}",location.getCountry());
	}
	

}
