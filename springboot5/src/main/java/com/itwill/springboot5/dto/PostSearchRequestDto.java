package com.itwill.springboot5.dto;

import lombok.Data;

@Data
public class PostSearchRequestDto {
	
	private String category;
	private String keyword;
	private int p; //-> form에서 보내주는것은 없으나, 검색 결과 목록의 (0부터 시작 하는) 페이지 번호
	//-> Integer라고 하지 않은 이유가 있음. 기본생성자로 타입의 기본값으로 초기화 시킴.
	//-> p는 int타입이여서 0으로 초기화됨. Integer로 했으면 null로 초기화되기때문에...
	//-> 기본생성자로 초기화 시킨후 요청파라미터 이름으로 setter를 찾아서 값을 변경시킨다고 함.
	//-> 요청 파라미터 이름이 없으면 호출하지 않아서 p는 기본값 0이 된다고 함(디스패쳐서블릿이 하는것)
	//-> 첫 페이지는 p가 0이기 때문에
}
