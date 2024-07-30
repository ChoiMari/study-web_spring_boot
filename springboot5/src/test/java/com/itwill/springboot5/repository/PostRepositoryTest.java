package com.itwill.springboot5.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
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
	
//	@Test //->insert할 때 시간 자동으로 기록되는지 확인 위한 test. 
	public void testSave () {
		//1.  DB테이블에 저장할 엔터티 객체 생성
		Post entity = Post.builder().title("JPA 저장 테스트").content("흑흑... 슬프다.... Spring Boot + JPA 저장 테스트")
				.author("admin").build(); //->를 제외한 나머지 필드 null인 상태 (id필드는 자동 채워지는걸로 설정) 
		log.info("save 전 : {}", entity);
		//-> save 전 : Post(super=BaseTimeEntity(createdTime=null, modifiedTime=null), id=null, title=JPA 저장 테스트, content=흑흑... 슬프다.... Spring Boot + JPA 저장 테스트, author=admin)
		
		//2. insert 쿼리 실행 : 엔터티 객체의 @Id로 설정된 필드가 null인 경우 insert쿼리가 실행된다고 함.
		postRepo.save(entity); //->엔터티 객체를 아규먼트로 넘기면 insert쿼리 자동으로 만들어서 DB의 객체가 저장됨. 리턴값 저장 안함
		log.info("save 후: {}", entity);

	}
	
	//@Test
	public void testUpdate() {
		//PK(@Id 필드)로 엔터티 검색하기
		Post entity = postRepo.findById(1L).orElseThrow(); //postRepo.findById(1L) 리턴타입 . 값 존재 할수도 있고 없을수 있어서 옵션타입으로 주어서 
		//orElseThrow()메서드 1개 더씀. 값이 있는 경우에는 엔터티 타입으로 리턴. 값이 없다면 예외 발생시킴
		//-> id가 1인 데이터를 불러옴(가져옴)
		log.info("findById 결과 = {}", entity);
		entity.update("제목 update test3", "내용 JPA - update test3"); //-> 우리가 수업시간에 만든 메서드
		//-> 리턴값 저장하지 않아두 됨
		log.info("update 호출 후= {}",entity);//-> 여기까지는 제목, 내용만 바뀜.(수정 시간은 안 바뀜)
		
		entity = postRepo.save(entity);//-> 수정된 entity를 아규먼트로 넘김. 그럼 update쿼리 실행 : @Id필드가 null이 아닌 경우(레코드가 있는 경우)
		//똑같은 메서드인데 @Id 필드가 null이면 insert실행 있으면 UPDATE실행.
		//아이디 1번으로 select를 다시함
		//리턴값 저장하지 않아도 된다고 했는데 로그 출력이 잘 안되서 entity = 로 리턴값 저장함.
		//다른부분이 있으면 update됨. modifiedTime 자동으로 채워서 바꿈
		//-> @Id 필드가 null이 아닌 경우(레코드가 있는 경우)와
		// 엔터티 객체가 DB에 있는 레코드와 다른경우(저장된 데이터가 달라진 경우) update쿼리가 실행 된다.
		
		
		//-> 유니테스트 동안에만 동작방식 설명 들은거고 실제는 @트렌젝션 어쩌고로 변경한다고함
		//-> find먼저하고 업데이트 한다고? 이런식으로 한다고 함.
		log.info("save 호출 후= {}",entity);
		
	}
	
	//@Test 
	public void testDelete() {
		postRepo.deleteById(1L); //-> 예외만 발생하지 않으면 delete성공했구나 하고 알면 된다고 함.
		//JPA는 @Id로 select 쿼리를 먼저 실행한 후
		//엔터티가 존재하는 경우에 delete 쿼리를 실행함.
	}
	
	@Test
	public void makeDummyData() {
		List<Post> data = new ArrayList<>(); //-> 비어있는 리스트만듬
		for(int i = 1; i <= 50; i++) {
			Post post = Post.builder().title("Dummy Title #" + i)
					.content("Dummy Content #" + i).author("admin"+i).build();
			data.add(post);
		}
		postRepo.saveAll(data);
	}
	
}
