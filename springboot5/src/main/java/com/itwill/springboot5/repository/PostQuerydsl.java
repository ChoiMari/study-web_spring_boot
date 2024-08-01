package com.itwill.springboot5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.springboot5.domain.Post;

/**
 * Querydsl 사용하기 위해 한 작업:
 * 1. build.gradle 파일에 의존성(dependencies{}안에)과 빌드 옵션을 추가함.
 * 2. 프로젝트 이름에서 마우스 우클릭 -> Gradle -> Refresh Gradle Project (빌드 수정시에 한번씩 해주라고 하심)
 * 3. window -> show view -> other -> Gradle폴더의 Gradle Task, Gradle Executions 두 개를 Open
 * 4. Gradle Tasks 창에서 프로젝트 선택 -> build -> clean 마우스 우클릭 -> Run Gradle Task 클릭
 * 5. Gradle Tasks 창에서 프로젝트 선택 -> build -> build 마우스 우클릭 -> Run Gradle Task 클릭
 * 6. Project Explorer의 프로젝트 이름에서 우클릭 -> Gradle -> Refresh Gradle Project
 * => 그럼 build/generated/querydsl 폴더에 
 * 		com.itwill.springboot5.domain패키지와 기존의 class 이름에서 Q로 시작하는 클래스들이 자동으로 생성됨
 * 7. 인터페이스(예: PostQuerydsl) 작성 (인터페이스에서 선언한 메서드 구현은 개발자가 직접해야 한다.)
 * 8. 인터페이스를 구현하는 클래스(PostQuerydslImpl)를 작성함 ->(작성 시에 주의 사항 있음)
 * 	- (1) QuerydslRepositorySupport 상속(PostQuerydslImpl extends QuerydslRepositorySupport)
 * 		상위 클래스가(부모클래스가) 기본 생성자가 없기 때문에, 아규먼트를 갖는 super(arg)를 명시(아규먼트는 엔터티 클래스(엔터티클래스이름.class)를 넣어준다)
 * 	- (2) PostQuerydsl 인터페이스에서 선언한 메서드를 구현(PostQuerydslImpl implements PostQuerydsl)
 *   	PostQuerydsl 인터페이스에서 선언한 추상 메서드의 body를 구현한다(PostQuerydsl를 구현(implements)하겠다고 한 클래스에서)
 * 9. JpaRepository를 상속받는 인터페이스(PostRepository)에서 PostQuerydsl 인터페이스를 상속 하도록 작성
 * 		인터페이스가 클래스는 상속 못함. 인터페이스는 인터페이스를 상속 가능.(extends JpaRepository<Post, Long>, PostQuerydsl)
 */



public interface PostQuerydsl {
	Post searchById(Long id);

}
