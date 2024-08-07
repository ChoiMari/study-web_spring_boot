package com.itwill.springboot5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MemberTest {

	
	@Test
	public void testEqualsAndHashCode() {
		Member member1 = Member.builder()
				.id(1L).username("admin").password("1111")
				.email("admin@itwill.com").build();
		
		log.info("member1 = {}",member1);
		
		Member member2 = Member.builder()
				.id(2L).username("admin").password("2222")
				.email("admin2222@itwill.com").build();
		
		log.info("member2 = {}",member2);
		
		assertThat(member1).isEqualTo(member2); //-> 주장 성공 함.
		//-> member1.equls(member2) 리턴 값이 true인지 확인 함
		// 성공한 이유 : username으로만 비교하도록 설정했기 때문에.
		//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false) 로 클래스 앞에 설정해 놓고 
		//username에만 @EqualsAndHashCode.Include 를 사용했기 때문에
		
		//assertThat(member1).isNotEqualTo(member2); : member1 과 member2가 다름을 주장
		
	}
}
