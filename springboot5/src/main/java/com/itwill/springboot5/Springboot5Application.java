package com.itwill.springboot5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.EntityListeners;

/**
 * insert/update 쿼리를 실행할 때 시간을 자동으로 저장하기 위해서 해야될 3가지.
 * 엔터티의 생성/수정 시간을 자동으로 기록하기 위해서 JPA Auditing 기능을 활성화 시킴.
 * (1) 메인 클래스(main 메서드를 가지고 있는) class Springboot5Application 에서
 * @EnableJpaAuditing 애너테이션을 설정하고
 * (2) 날짜/시간(LocalDate/LocalDateTime) 필드를 갖는 엔터티(BaseTimeEntity)에는
 * 클래스 위에
 * @EntityListeners(AuditingEntityListener.class) 애너테이션을 설정.
 * (3) 날짜/시간 필드(createdTime/modifiedTime)에 생성하는 경우에는 @CreatedDate , 수정하는 경우에는 @LastModifiedDate 
 * 애너테이션을 설정하면 된다.  
 */
@EnableJpaAuditing//->JPA Auditing 기능을 활성화한다는 뜻.
//-> AuditingEntityListener를 사용할 수 있게 설정 하겠다 라는 뜻.

@SpringBootApplication //-> 되게 중요하다고 이거 없음 실행 안된다고 함
public class Springboot5Application {

	public static void main(String[] args) {
		SpringApplication.run(Springboot5Application.class, args);
	}

}
