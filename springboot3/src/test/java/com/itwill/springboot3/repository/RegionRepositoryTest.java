package com.itwill.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot3.domain.Region;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class RegionRepositoryTest {
	
	@Autowired
	private RegionRepository reRepo;
	
	//@Test
	public void testDependencyInjection() {
		assertThat(reRepo).isNotNull();
		log.info("reRepo={}",reRepo);
	}
	
	@Test
	public void testFindAll() {
		long count = reRepo.count();
		assertThat(count).isEqualTo(4L);
		
		List<Region> list = reRepo.findAll();
		//assertThat(list.size()).isEqualTo(4);
		log.info("regions[0]= {}",list.get(0));
		
	}

}
