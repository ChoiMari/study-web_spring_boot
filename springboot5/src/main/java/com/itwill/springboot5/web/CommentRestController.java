package com.itwill.springboot5.web;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.dto.CommentRegisterDto;
import com.itwill.springboot5.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment") //->이 컨트롤러의 모든 요청주소는 이걸로 시작함 
public class CommentRestController {

	private final CommentService commentSvc;
	
	@PostMapping //-> axios로 부터 dto전달 받아서 DB에 insert시킴
	public ResponseEntity<Comment> registerCommnet(@RequestBody CommentRegisterDto dto){
		//리턴 타입으로 쓴 ResponseEntity<Comment> : 생성된 Comment 객체를 리턴한다는 뜻
		//@RequestBody
		//-> 서버 측에서 자바 객체로 매핑하기 위해 사용됩니다. 
		//-> HTTP 요청의 본문(body)을 자바 객체로 변환할 때 사용하는 애너테이션입니다.
		//1개씩 받고 싶으면 -> @RequestParam
		//요청주소에 변수 들어있는걸 받으려면 -> @PathVariable
		log.info("registerCommnet(dto={})",dto);
		
		//서비스 계층의 메서드 호출(댓글 등록 서비스 실행)
		Comment entity = commentSvc.create(dto);
		log.info("save 결과 entity={}",entity);
	
		return ResponseEntity.ok(entity);
	}
	
	@GetMapping("/all/{postId}") //->@PathVariable(name = "postId") Long postId
	public ResponseEntity<Page<Comment>> getCommentList(@PathVariable(name = "postId") Long postId,
			@RequestParam(name="p",defaultValue = "0") int pageNo){
		//defaultValue = "0" : 리퀘스트 파라미터 없을 경우 pageNo값을 0으로 설정함(첫페이지는 pageNo가 0)
		log.info("getCommentList(postId={},pageNo={}",postId,pageNo);
		
		Page<Comment> data = commentSvc.readCommentList(postId, pageNo);
		
		return ResponseEntity.ok(data); //-> 응답 성공인 경우에 data(페이징 처리된 댓글 목록)를 리턴 
	}


}
