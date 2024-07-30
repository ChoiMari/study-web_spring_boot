package com.itwill.springboot5.dto;

import java.time.LocalDateTime;

import com.itwill.springboot5.domain.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE) //-> 빌드패턴 이용하려고 사용함 private로 감춤.
@NoArgsConstructor
@Builder
public class PostDetailsDto {
	private Long id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
	
	//편의 메서드
		public static PostDetailsDto fromEntity(Post entity) {
			// Post타입 객체가 있을 때 : Post타입의 객체 --> PostDetailsDto로 변환
			//-> 그래서 static으로 선언함.
			return PostDetailsDto.builder().id(entity.getId()).title(entity.getTitle())
					.content(entity.getContent()).author(entity.getAuthor())
					.createdTime(entity.getCreatedTime()).modifiedTime(entity.getModifiedTime()).build();
		}

}
