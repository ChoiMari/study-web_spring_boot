package com.itwill.springboot5.dto;


import java.time.LocalDateTime;

import com.itwill.springboot5.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//포스트 목록 보기를 할 때 사용할 Dto
//컨트롤러와 서비스 사이에서 이용.
//PostService에서 검색한 결과를 PostController로 리턴할 때 사용할 클래스
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE) //-> 빌드패턴 이용하려고 사용함 private로 감춤.
@NoArgsConstructor
@Builder
public class PostListItemDto {
	
	//필요한 필드들만 나열
	private Long id; 
	private String title;
	private String author;
	private LocalDateTime modifiedTime;

	//편의 메서드
	public static PostListItemDto fromEntity(Post entity) {
		// Post타입 객체가 있을 때 : Post타입의 객체 --> PostListItemDto로 변환
		//-> 그래서 static으로 선언함.
		return PostListItemDto.builder().id(entity.getId()).title(entity.getTitle())
		.author(entity.getAuthor()).modifiedTime(entity.getModifiedTime()).build();
	}
}
