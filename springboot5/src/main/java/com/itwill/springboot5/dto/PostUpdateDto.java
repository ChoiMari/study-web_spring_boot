package com.itwill.springboot5.dto;

import com.itwill.springboot5.domain.Post;

import lombok.Data;

@Data
public class PostUpdateDto {
	private Long id;
	private String title;
	private String content;

	
//	public Post toEntity() { //->dto타입을 엔터티(Post)타입으로 변환
//		return Post.builder()
//				.id(id)
//				.title(title)
//				.content(content)
//				.build();
//	}

}
