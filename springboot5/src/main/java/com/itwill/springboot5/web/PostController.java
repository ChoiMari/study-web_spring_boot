package com.itwill.springboot5.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot5.domain.Post;
import com.itwill.springboot5.dto.PostCreateDto;
import com.itwill.springboot5.dto.PostDetailsDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.dto.PostSearchRequestDto;
import com.itwill.springboot5.dto.PostUpdateDto;
import com.itwill.springboot5.service.PostService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller @RequestMapping("/post") //-> /post로 시작하는 모든 요청을 처리하는 컨트롤러
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postSvc;
	
	@GetMapping("/list") //-> 로그인 인증 필요 X . 목록보기
	public void list(Model model,@RequestParam(name = "p", defaultValue = "0") int pageNo) {
		//뷰에서 th:href="@{|?p=${page.number - 1}| 이렇게 설정했음. 리퀘스트 파라미터 이름 p. 
		//리퀘스트 파라미터 p값이 없을땐(defaultValue = "0") 0으로 줌
		log.info("list(pageNo={})",pageNo);
//		//서비스 계층의 메서드를 호출 -> 뷰에 포스트 목록 전달
//		List<PostListItemDto> list = postSvc.read();
//		model.addAttribute("postList", list);
		
		//페이징 처리한 list를 뷰에 전달
		Page<PostListItemDto> pagingList = postSvc.read(pageNo, Sort.by("id").descending()); 
		//Sort.by("id") id를 기준으로 정렬하는데 .descending()을 붙여서 내림차순(desc)로 정렬함(큰 값이 맨위로 시작하는)
		model.addAttribute("page", pagingList);
		
		//fragment의 pagination에서 사용하기 위한 현재 요청 주소 정보 뷰로 보냄
		model.addAttribute("baseUrl", "/post/list"); ///post/list 현재 요청주소를 baseUrl이라는 이름으로 뷰로 보냄
		//뷰에서는 ${baseUrl}로 사용가능
		
	}
	
	@PreAuthorize("hasRole('USER')") //새 글 작성 페이지에 접근 권한 설정함. 로그인 여부 검사 + USER권한 검사
	//아규먼트로는 호출하는 메서드 이름을 문자열로 써주면 된다고 함. 
	//->@PreAuthorize("isAuthenticated()") : 권한(role)에 상관 없이 아이디와 비밀번호로만 인증.(로그인만 성공하면 요청주소에 접근 가능함)
	// 권한까지 부여하려면@PreAuthorize("hasRole('USER')")  -> ''작은 따음표로 권한을 아규먼트로 주어야한다고 함
	//-> role(권한)이 일치하는 아이디/비밀번호 인증.
	@GetMapping("/create")
	public void create() {
		log.info("GET 요청 방식 create()");	
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/create")
	public String create(PostCreateDto dto) { //오버로딩 //(@ModelAttribute PostCreateDto dto)라고 안써도 읽음.
		//뷰에서 설정한 name속성값이 dto의 필드이름과 같아서 리퀘스트 파라미터 이름으로 setter메서드를 찾는다고. 
		log.info("POST 요청방식 create(dto={})",dto);
		//서비스 계층의 메서드를 호출해서 작성한 포스트를 DB에 저장(insert)
		//-> 서비스에서 dto를 엔터티 타입으로 변경해서 DB에 저장.
		postSvc.create(dto);
		
		return "redirect:/post/list"; //-> 글 작성 후 포스트 목록보기 페이지로 리다이렉트.
	}
	
	//상세보기 , 수정하기 페이지 요청 처리 주소
	@PreAuthorize("hasRole('USER')") //-> 요청주소 "/details","/modify 두 개의 매핑에 대해서 로그인+ 권한 일치 인증해야 페이지 접근가능 적용함
	@GetMapping({"/details","/modify"}) //-> 하나의 컨트롤러가 2개의 요청주소 처리(get방식)
	public void details(Model model, @RequestParam(name = "id") Long id) {
		log.info("GET details(id={})",id);
		//PostDetailsDto dto = postSvc.readById(id);
		Post entity = postSvc.readById(id);
		model.addAttribute("post", entity);
	} //-> 뷰의 이름은 요청주소가 /details 로 들어오면 post폴더의 details.html이 되고
	// 요청주소가 /modify 로 들어오면 post 폴더의 modify.html이 됨.
	
	//상세보기에서 삭제버튼 클릭시 요청 처리 주소
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") Long id) { // 오버로딩
		log.info("delete(id={})",id);
		postSvc.delete(id);
		
		return "redirect:/post/list";
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/update")
	public String modify(PostUpdateDto dto) {
		log.info("PostUpdateDto(dto={})",dto);
		postSvc.update(dto);
		
		return "redirect:/post/details?id=" + dto.getId();
	
	}
	
	
	@GetMapping("/search")
	public String search(PostSearchRequestDto dto, Model model) {
		log.info("search(dto={})",dto);
		
		Page<PostListItemDto> result = postSvc.search(dto, Sort.by("id").descending());
		model.addAttribute("page", result);
		
		//fragment의 pagination에서 사용하기 위한 현재 요청 주소 정보 뷰로 보냄
		model.addAttribute("baseUrl", "/post/search"); 
		// -> post/search 현재 요청주소를 baseUrl이라는 이름으로 뷰로 보냄
		//뷰에서는 ${baseUrl}로 사용가능
		
		return "post/list"; 
	}

}
