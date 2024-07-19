package com.itwill.springboot1.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot1.dto.Author;
import com.itwill.springboot1.dto.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j //로그 사용
@Controller //-> 컨트롤러 클래스에 붙임
public class HomeController {
	
	@GetMapping("/") //context path로 들어오는 GET방식 요청을 처리.
	public String home(Model model) {
	
		log.info("home()");
		
		//현재 시간
		LocalDateTime now = LocalDateTime.now(); //시스템 현재시간
		model.addAttribute("currentTime", now); //-> 모델은 뷰의 데이터 전달할때사용/모델의 아규먼트는 뷰에 전달하는 데이터
		//타임리프에서는 <h2 th:text="${currentTime}">현재 시간</h2> 이렇게 사용함. 여는 태그와 닫는 태그 사이에 값 안 넣고
		//태그 안에 넣음
		
		//저자 생성
		Author author = Author.builder()
				.firstName("찰스")
				.lastName("다윈")
				.build();
		
		Book book = Book.builder().id(1).title("-_-").author(author)
				.build();
		
		log.info("book : {}",book);
		model.addAttribute("book", book); //-> 뷰템플릿에 넘김
	
		
		return "index"; // -> 뷰의 이름을 리턴
		//->src/main/resources/templates/리턴값.html
		//접두사 src/main/resources/templates/
		//접미사 .html
	
	}

	
	@GetMapping("/book/list")
	public void bookList(Model model) {
		//return 타입이 void인 경우 뷰의 이름은 요청 주소와 같음. book폴더의 list.html을 찾아감
		log.info("bookList()");
		
		//도서 목록 더미 데이터를 저장하기 위한 리스트.
		ArrayList<Book> list = new ArrayList<>(); 
		
		// 더미 데이터 5개를 리스트에 저장.
		for(int i=1; i <=5; i++) {
			Book book = Book.builder()
					.id(i)
					.title("Title-" + i)
					.author(Author.builder().firstName("Name-" + i).lastName("LastName").build())
					.build();
			
			list.add(book);
		}
		
		//북을 1개 더 만듬 author가 없는 북을 1개 추가
		Book b = Book.builder().id(10).title("-_-").build(); //author => null이됨
		//리스트에 넣음 -> 6권
		list.add(b);
		
		//도서 목록을 뷰에 전달
		model.addAttribute("bookList", list);
		
		
	}
	
	@GetMapping("/book/details")
	public void bookDetails(Model model, @RequestParam(name= "id") int id) { //쿼리스트링 받는 방법  @RequestParam(name= "id") int id
		//요청 파라미터 id값을 찾고, 해당 id를 갖는 Book 객체를 만들고
		//모델에 Book 객체를 속성(attr 애트리뷰트)으로 저장. 뷰로 전달
		
		log.info("bookDetails(id={})",id);
		
		Book book = Book.builder()
				.id(id)
				.title("책제목..")
				.author(Author.builder().firstName("깔깔").lastName("김").build())
				.build();
		
		model.addAttribute("myBook",book);
	
	}
	
	
	//path variable을 포함하는 요청을 처리하는 메서드
	@GetMapping("/book/details/{id}") //-> 이거 쓸땐 메서드 이름 void로 하면 안됨. String으로 해야함. 
	//안그럼 id 값이 변하는 대로 HTML파일이 필요함.
	public String bookDetailsPathVariable(@PathVariable(name="id") int id, Model model) { 
		//@GetMapping("/book/details/{변하는값변수}")에 {변하는값변수}있는거와 똑같이 @PathVariable에 (name="변하는값변수") name값 써야함
		log.info("bookDetailsPathVariable(id={}",id);
		
		Book book = Book.builder()
				.id(id)
				.title("책 제목..-_-")
				.author(Author.builder().firstName("저자").lastName("책").build())
				.build();
		model.addAttribute("myBook", book);
		
		return "book/details";
		//templates밑에서 찾음. templates는 생략가능
		//뒤에는 .html 생략 가능
	}
	
	
}
