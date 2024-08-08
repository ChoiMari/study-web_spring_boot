package com.itwill.springboot5.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot5.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	

	//select m.*, r.* 
	//	from members m left join member_roles r on m.id = r.member_id where m.username = ?; 
	//-> 회원 가입전 아이디 중복체크에 이용, 로그인할 때도 이용가능하다고 함 
	@EntityGraph(attributePaths = "roles") //->"roles" : Member클래스의 필드이름을 씀
	//Member 엔티티의 roles 필드를 함께 조회하도록 지시
	//attributePaths 속성은 조회할 때 함께 로드할 엔티티의 필드를 지정
	//@EntityGraph를 사용하면, LAZY 로딩으로 설정된 연관 데이터를 특정 쿼리에서만 즉시 로딩(EAGER)하도록 변경할 수 있습니다.
	//지정된 연관 데이터는 한 번의 SQL 쿼리로 함께 조회됩니다.
	Optional<Member> findByUsername(String username); 
	//-> 데이터가 있으면 1건만 나오고 없으면 0건인 경우에 Optional<엔터티>타입으로 사용하라고 함
	
}