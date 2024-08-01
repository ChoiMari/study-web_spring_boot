package com.itwill.springboot5.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot5.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostQuerydslTest {
	
	@Autowired private PostRepository postRepo; //PostQuerydsl인터페이스를 상속 받고 있기 때문에 PostRepository 만 선언하면 된다고 함
	
	//@Test
	public void testSearchById() {
		Post entity = postRepo.searchById(2L);
		log.info("entity = {}", entity);
	}
	
	@Test
	public void test() {
		List<Post> result = null;
		//result = postRepo.searchByTitle("DUMM");
		
		result = postRepo.searchByContent("tes");
		
		result.forEach(System.out::println);
//		for(int i=0; i < 5; i++) {//-> DUMM로 검색했는데 너무 많이 나오니까(더미데이터 많음) 5개만 보려고..
//			log.info("{}",result.get(i));//result.get(i) result에서 인덱스로(get(i)) 값 꺼내서 로그로 출력함
//		}
		
	}
	

}
