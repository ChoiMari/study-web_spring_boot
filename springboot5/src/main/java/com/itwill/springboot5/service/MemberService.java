package com.itwill.springboot5.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.springboot5.domain.Member;
import com.itwill.springboot5.domain.MemberRole;
import com.itwill.springboot5.dto.MemberSignUpDto;
import com.itwill.springboot5.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
//Spring Security에서 로그인/로그아웃 처리에서 사용할 수 있도록(implements UserDetailsService 추가함)
//하기 위해서 UserDetailsService(사용자상세정보서비스인터페이스)를 구현함
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Transactional //-> 써주어야 되는 이유 : 데이터베이스 작업의 일관성과 무결성을 보장하기 위해서 사용함
	//단일 트랜잭션으로 처리되어야 하기 때문에 @Transactional을 사용
	//트랜잭션(Transaction): 트랜잭션은 데이터베이스에서 일련의 작업들이 완벽하게 수행되거나, 전혀 수행되지 않도록 보장하는 메커니즘입니다. 
	//트랜잭션 내에서 여러 데이터베이스 작업이 실행되며, 이 작업들이 모두 성공해야 최종적으로 커밋(commit)되고, 그렇지 않으면 롤백(rollback)됩니다.
	//-> select같은 경우에 readOnly=true 꼭 써주라고 함(읽기 전용으로 만듬)
	public Member create(MemberSignUpDto dto) {
		log.info("create(dto={})",dto);
		//비밀번호 암호화 서비스에서 할거면 save메서드 호출 전에  
//		String encodedPassword = passwordEncoder.encode(dto.getPassword());
//	    dto.setPassword(encodedPassword);하면 된다고 함  //dto.setPassword(passwordEncoder.encode(dto.getPassword())); 와 같음
	
		Member member = memberRepo.save(dto.toEntity(passwordEncoder).addRole(MemberRole.USER)); //-> 아규먼트 엔터티(Member) 타입으로 주어야 함 
		//->dto.toEntity() : 리턴값 Member(dto타입을 Member타입으로 변환해서 리턴해 줌)
		//addRole(MemberRole.USER) user권한 줌.
		//MemberRole.USER enum으로 선언한 상수값 아규먼트로 넣어줌(enum에서 선언한 상수 static이여서 객체생성없이 그냥 .으로 사용가능함)
		//save() -> (1) insert into members , (2) insert into member_rolse 이렇게 2개의 테이블에 insert시킨다고 함
		//->toEntity(passwordEncoder) : dto클래스한테 passwordEncoder 넘겨줌. PasswordEncoder주입은 서비스계층에서 하고..
		
		
		return member;
	}

	@Override //implements UserDetailsService해서 구현한 메서드(오버라이드) //-> 스프링 시큐리티에서 호출해서 이용한다고 함.(로그인 과정에서)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//DB테이블(members)에 (이 메서드의 아규먼트로 넣어주는) username과 일치하는 사용자가 있으면
		//UserDetails타입의 객체를 리턴하고, 그렇지 않으면 UsernameNotFoundException을 던짐. 
		//근데 우리는 UserDetails(인터페이스)를 구현하고 있는 클래스 만든 적이 없음
		//-> 옵션) Member(엔터티 클래스)가 implements UserDetails(UserDetails를 구현한다) ->(UserDetails에 선언되어있는) 추상메서드구현(오버라이드)
		//-> 인터페이스에서 D(디폴트 메서드)는 바디가 구현되어져 있는 메서드. 특별한 경우에만 오버라이드하면 된다고 함
		log.info("loadUserByUsername(username={})",username); //->로그인 양식에 적은 username값이 출력됨
		Optional<Member> entity = memberRepo.findByUsername(username);
		if(entity.isPresent()) {//Optional객체가 가지고 있는 메서드
			//isPresent() : DB에 사용자가 있으면 true리턴함
			//isEmpty() : 값이 없으면 true. 이거 써도 된다고 함.
			return entity.get();
		} else {
			throw new UsernameNotFoundException( username + "과 일치하는 레코드(사용자 정보)가 없습니다.");
			//->이 메서드를 호출 한 곳에서 예외 처리 한다고 함.
		}
	
	}
	
}
