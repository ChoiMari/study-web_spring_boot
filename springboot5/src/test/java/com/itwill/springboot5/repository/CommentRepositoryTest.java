package com.itwill.springboot5.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.domain.Post;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CommentRepositoryTest {
	
	//CommentRepository의 CRUD 기능을 단위 테스트 해보기
	//예) postId로 찾기, 수정하기, 삭제하기 등
	//-> 남는 시간 : 팀원 분들끼리 대화 또는 선생님에게 질문
	//-> 내일 에이작스 이용 댓글 기능 구현.
	@Autowired private CommentRepository commentRepo;
	
	//@Test  //-> 리파지토리 의존성 주입 잘 되었는지 확인하는 test
	public void testDependencyInjection() {
		assertThat(commentRepo).isNotNull(); //의존성 주입받은 CommentRepository객체가 null이 아님을 주장(test성공시 주입 잘 받았음을 확인)
		log.info("commentRepo={}",commentRepo);
		//->commentRepo=org.springframework.data.jpa.repository.support.SimpleJpaRepository@72e76649
	}
	
	//@Test //-> findAll() : 테이블의 전체 행 검색 sql을 실행하는 메서드 test
	public void testFindAll() {
		List<Comment> list = commentRepo.findAll(); //findAll() : select * from 테이블을 실행하는 메서드(테이블의 행 전체 검색)
		assertThat(list.size()).isEqualTo(0); //-> 지금 DB 테이블에 넣은 데이터(행)이 없기 때문에 
		list.forEach(System.out::println);
	}
	
	//@Test //-> save() : insert 잘 시키는지 test (save() 메서드는 @Id 필드가 null 이면 insert실행하고 값이 있으면 UPDATE실행.)
	public void testSave() { 
		Comment entity = Comment.builder().post(Post.builder().id(2L).build())
							.ctext("댓글 내용 test").writer("admin Comment test").build();
		log.info("save 전 entity={}",entity);
		//->save 전 entity=Comment(id=null, ctext=댓글 내용 test, writer=admin Comment test)
		//-> post(조인컬럼)은 toString에서 제외시켰기 때문에 안나옴.
		log.info("postId={}",entity.getPost().getId());
		//postId=2
		
		commentRepo.save(entity);
		//-> insert into comments (created_time, ctext, modified_time, post_id, writer, id) values (?, ?, ?, ?, ?, default)
		//-> 생성시간, 수정시간은 자동으로 넣어줘서 insert시킴
		
		log.info("save 후 entity={}",entity);
		//save 후 entity=Comment(id=2, ctext=댓글 내용 test, writer=admin Comment test)
				
	}
	
	//@Test
	public void testUpdate() {
		Comment entity = commentRepo.findById(1L).orElseThrow();
		log.info("findById 결과:{}",entity);
		
		entity.update("댓글 업데이트 test 잘될까..??");
		
		log.info("update 호출 후: {}",entity);
		
		commentRepo.save(entity);
		
		log.info("save 호출 후 : {}",entity);

	}
	
	@Test 
	public void testDelete() {
		commentRepo.deleteById(2L); //-> 작동 원리 : 아규먼트로 넣은 Id값을 먼저 찾은 다음에
		//값이 있으면 delete sql을 실행함.
	}
	
	

}
