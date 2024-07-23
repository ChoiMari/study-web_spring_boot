package com.itwill.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.itwill.springboot3.domain.Country;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CountryRepositoryTest {

	@Autowired
	private CountryRepository ctyRepo;
	
	//@Test
	public void testDependencyInjection() {
		assertThat(ctyRepo).isNotNull();
		log.info("ctyRepo={}",ctyRepo);
	}
	
	//@Test
	public void testFindAll() {
		long count = ctyRepo.count();
		assertThat(count).isEqualTo(25L);
		
		List<Country> list = ctyRepo.findAll();
		log.info("countries[0]={}",list.get(0));
	}
	
	@Transactional
	@Test
	public void testFindById() {
		Country country = ctyRepo.findById("BE").orElseThrow();
		log.info("country={}",country);
		log.info("country.getRegion={}",country.getRegion());
	}
	
}
