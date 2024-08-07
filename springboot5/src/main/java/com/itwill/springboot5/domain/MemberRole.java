package com.itwill.springboot5.domain;

public enum MemberRole {
	USER("ROLE_USER"), //-> public static final MemberRole USER = new MemberRole("ROLE_USER");와 같음
	//그냥 USER로 선언 했을 경우에는 public static final MemberRole USER = new MemberRole();와 같음(기본생성자 호출)
	//enum에서 나열한 상수들은 앞에 public static final MemberRole와 뒤에 = new MemberRole();가 생략 되어 있는 것.

	ADMIN("ROLE_ADMIN");//-> public static final MemberRole USER = new MemberRole("ROLE_ADMIN");와 같음
	//->("ROLE_ADMIN")의 의미 : 아규먼트가 있는 생성자 호출. 문자열(ROLE_ADMIN이라는 문자열) 1개를 가지고 있는 객체
	
	//이외의 권한 종류를 더 만들고 싶으면 여기서 나열해서 사용하면 된다고 함.
	
	private String authority;
	
	//개발자가 아규먼트가 있는 생성자를 만들었기 때문에 기본 생성자를 자동으로 만들어주지 않음. 그래서 나열한 상수들에서 오류가 생김.
	//-> 그래서 나열한 상수들을 아규먼트를 넘기는 생성자 호출로 바꿈
	
	//주의) enum의 생성자는 항상 private. private 수식어가 생략되어 있음. 
	MemberRole(String authority) { //앞에 private 생략 됨. enum의 생성자는 외부에서 호출 불가능(=외부에서 객체 생성 불가) 
		this.authority = authority;
	}
	
	public String getAuthority() {
		return this.authority;
	}
	
}
