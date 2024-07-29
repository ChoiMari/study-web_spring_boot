package com.itwill.springboot5.web;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.springboot5.dto.PostCreateDto;
import com.itwill.springboot5.dto.PostListItemDto;
import com.itwill.springboot5.service.PostService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller @RequestMapping("/post") //-> /post로 시작하는 모든 요청을 처리하는 컨트롤러
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postSvc;
	
	@GetMapping("/list")
	public void list(Model model,@RequestParam(name = "p", defaultValue = "0") int pageNo) {
		log.info("list(pageNo={})",pageNo);
		//서비스 계층의 메서드를 호출 -> 뷰에 포스트 목록 전달
		List<PostListItemDto> list = postSvc.read();
		model.addAttribute("postList", list);
		
		//페이징 처리한 list를 뷰에 전달
		Page<PostListItemDto> pagingList = postSvc.pageRead(pageNo, Sort.by("id"));
		model.addAttribute("page", pagingList);
		
	}
	
	@GetMapping("/create")
	public void create() {
		log.info("create()");	
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute PostCreateDto dto) {
		log.info("create(dto={})",dto);
		postSvc.insert(dto);
		return "redirect:/post/list";
	}

}
