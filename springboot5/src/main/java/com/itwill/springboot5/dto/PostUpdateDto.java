package com.itwill.springboot5.dto;



import lombok.Data;

@Data
public class PostUpdateDto {
	private Long id;
	private String title;
	private String content;

	
//	public Post toEntity() { //->dto타입을 엔터티(Post)타입으로 변환. 필요없고 Post에 있는 update메서드 사용해서 update시킴.
//		return Post.builder()
//				.id(id)
//				.title(title)
//				.content(content)
//				.build();
//	}

}
