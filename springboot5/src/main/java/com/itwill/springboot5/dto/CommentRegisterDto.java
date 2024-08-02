package com.itwill.springboot5.dto;

import lombok.Data;

@Data
public class CommentRegisterDto {
	
	private Long postId;
	private String ctext;
	private String writer; //-> 에이작스의 프로퍼티 이름과 같으면 알아서 찾아준다고 함
	
	//postId 때문에 편의 메서드 toEntity 안 만든다고 함

}
