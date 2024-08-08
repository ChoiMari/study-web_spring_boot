package com.itwill.springboot5.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.springboot5.domain.Member;
import com.itwill.springboot5.domain.MemberRole;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberRepositoryTest {
	
	@Autowired //-> test할땐 생성자에 의한 의존성 주입은 사용하지 못함
	private MemberRepository memberRepo;
	
	@Autowired //->SecurityConfig 클래스(@Configuration로 선언한 클래스)에서 
	//@Bean으로 선언해 놓았기 때문에 스프링컨테이너가 주입해준다고 함.
	private PasswordEncoder passwordEncoder;
	
//	@Test
	public void testDependencyInjection() { //->@Autowired로 잘 주입 받았는지 확인하는 Test
		assertThat(memberRepo).isNotNull();
		log.info(memberRepo.toString()); //-> null이 아니면 toString()메서드 호출 가능함. null이면 nullPointerException
		//SimpleJpaRepository@268c4507
		assertThat(passwordEncoder).isNotNull();
		log.info(passwordEncoder.toString());
		//BCryptPasswordEncoder@18cb7c6
	}
	
//	@Test
	public void testSave() {
		// 엔터티 객체를 DB members 테이블에 저장.
//		Member m = Member.builder()
//				.username("test1")
//				.password(passwordEncoder.encode("1111"))
//				.email("test1@itwill.com")
//				.build();
		Member m = Member.builder()
				.username("test2")
				.password(passwordEncoder.encode("2222"))
				.email("test2@itwill.com")
				.build();
		
		m.addRole(MemberRole.USER); //-> 아규먼트로 enum에서 만들었던 상수 넣음
		m.addRole(MemberRole.ADMIN);
		log.info("save 호출 전 = {},{}", m, m.getRoles());
		//->save 호출 전 = Member(super=BaseTimeEntity(createdTime=null, modifiedTime=null), id=null, username=test2, 
		//password=$2a$10$ThaeySLXEvnlvRQwgoxXuekn2NvroCfxPpOiB6MWR0GYTx4MmhNHK, email=test2@itwill.com),[USER, ADMIN]
		//m.getRoles() : [USER, ADMIN]
		
		m = memberRepo.save(m);
		//-> members 테이블, member_roles 테이블(연관테이블)에 insert(연관 테이블 member_roles도 같이 insert시킴)
		log.info("save 호출 후 = {},{}", m, m.getRoles());
		//-> save 호출 후 = Member(super=BaseTimeEntity(createdTime=2024-08-07T16:12:07.294761100, modifiedTime=2024-08-07T16:12:07.294761100),
		//id=2, username=test2, password=$2a$10$ThaeySLXEvnlvRQwgoxXuekn2NvroCfxPpOiB6MWR0GYTx4MmhNHK, email=test2@itwill.com),[USER, ADMIN]
		
		//m.getRoles() : [USER, ADMIN]
	}
	
//	@Test 
//	@Transactional //->Member 클래스(엔터티클래스)에서 @ElementCollection(fetch = FetchType.LAZY)로 선언한게 있어서 사용함. (JUnit test에서는 필요함)
	public void testFindAll() {
		List<Member> list = memberRepo.findAll(); //findAll() : select * from members sql문을 실행하는 메서드
		//-> members 테이블과 member_roles 테이블에서 정보를 취합
		
		list.forEach((member) -> log.info("{},{}", member, member.getRoles()));
	}
	
	@Test 
	//findByUsername() 메서드 선언 앞에 @EntityGraph(attributePaths = "roles")라고 써서
	//@Transactional 없이도 getRoles()값도 잘 가져옴(JUnit test에 필요한 것)
	public void testFindByUsername() {
		Member test1 = memberRepo.findByUsername("test1").get(); //-> 값이 있으면 값을 리턴. 값이 없으면 예외를 던짐
		log.info("{},{}",test1,test1.getRoles());
		
		Member test2 = memberRepo.findByUsername("test2").get();
		log.info("{},{}",test2,test2.getRoles());
	}
	
	
}
