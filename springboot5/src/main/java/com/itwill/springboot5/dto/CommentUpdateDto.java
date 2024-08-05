package com.itwill.springboot5.dto;

import lombok.Data;

@Data
public class CommentUpdateDto { //컨트롤러 아규먼트의 파라미터 타입으로 이용하면 됨.
	private Long id;
	private String ctext;
}

