package com.itwill.springboot5.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot5.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class PostRepositoryTest {

	//PostRepository 의존성 주입 @Test
	// 테스트는 생성자에 의한 의존성 주입못한다고함
	@Autowired private PostRepository postRepo;
	
//	@Test // 의존성 주입 잘 받았는지 test
	public void testDependencyInjection() {
		assertThat(postRepo).isNotNull();//-> postRepo 객체가 null이 아님을 주장해서 null이 아니면 테스트 성공.
		//->assertThat import이걸로 해야함 : import static org.assertj.core.api.Assertions.assertThat;
		log.info("postRepo={}",postRepo);
		//->postRepo=org.springframework.data.jpa.repository.support.SimpleJpaRepository@183e64a8 이런 식으로 나와야 함
	}
	
	//@Test //-> 전체 검색 test. 전체 검색해주는 sql 메서드 . findAll()
	public void testFindAll() {
		List<Post> list = postRepo.findAll();
		assertThat(list.size()).isEqualTo(0);//-> 지금 테이블 만들고 insert시킨게 하나도 없기 때문에 테이블의 행은 0
		//-> list의 size가 0과 같음을 주장
		
		//람다 표현식 사용해서 1개씩 출력(list의 원소 1개씩(DB테이블의 행 1줄씩 출력)
		list.forEach(System.out::println);//(x) -> System.out.println(x);
		
	}
	
	@Test //->insert할 때 시간 자동으로 기록되는지 확인 위한 test. 
	public void testSave () {
		//1.  DB테이블에 저장할 엔터티 객체 생성
		Post entity = Post.builder().title("JPA 저장 테스트").content("흑흑... 슬프다.... Spring Boot + JPA 저장 테스트")
				.author("admin").build(); //->를 제외한 나머지 필드 null인 상태 (id필드는 자동 채워지는걸로 설정) 
		log.info("save 전 : {}", entity);
		//-> save 전 : Post(super=BaseTimeEntity(createdTime=null, modifiedTime=null), id=null, title=JPA 저장 테스트, content=흑흑... 슬프다.... Spring Boot + JPA 저장 테스트, author=admin)
		
		//2. insert 쿼리 실행
		postRepo.save(entity); //-> 리턴값 저장 안함
		log.info("save 후: {}", entity);
		
		
	}	
}
