package com.itwill.springboot5.web;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.springboot5.domain.Comment;
import com.itwill.springboot5.dto.CommentRegisterDto;
import com.itwill.springboot5.dto.CommentUpdateDto;
import com.itwill.springboot5.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment") //->이 컨트롤러의 모든 요청주소는 이걸로 시작함 
public class CommentRestController {

	private final CommentService commentSvc;
	
	@PreAuthorize("hasRole('USER')")
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
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/all/{postId}") //->@PathVariable(name = "postId") Long postId
	public ResponseEntity<Page<Comment>> getCommentList(@PathVariable(name = "postId") Long postId,
			@RequestParam(name="p",defaultValue = "0") int pageNo){
		//defaultValue = "0" : 리퀘스트 파라미터 없을 경우 pageNo값을 0으로 설정함(첫페이지는 pageNo가 0)
		log.info("getCommentList(postId={},pageNo={}",postId,pageNo);
		
		Page<Comment> data = commentSvc.readCommentList(postId, pageNo);
		
		return ResponseEntity.ok(data); //-> 응답 성공인 경우에 data(페이징 처리된 댓글 목록)를 리턴 
	}
	
	@PreAuthorize("hasRole('USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Long> deleteComment(@PathVariable(name = "id")Long id){
		//@PathVariable(name = "id")Long id : @DeleteMapping("/{id}")에 있는 PathVariable을 Long타입의 id에 넣어주세요 라는 의미
		log.info("deleteComment(id={})",id);
		
		commentSvc.delete(id); //서비스에서 삭제에 사용되는 sql메서드가 리턴타입이 void라서 리턴값 받지 않음.
		//-> 예외발생 없다면 삭제 성공.
		
		return ResponseEntity.ok(id); 
		//리턴타입을 ResponseEntity<Long>로 한 이유 : 삭제한 commentId(댓글아이디)를 응답으로 보내기 위해서.
		//삭제 성공시 alert(`#${id}번 댓글 삭제 성공`); 로 alert으로 확인해보려고 설정했기 때문에.
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/{id}")
	public ResponseEntity<Long> updateComment(@PathVariable(name = "id") Long id, @RequestBody CommentUpdateDto dto){
		//@RequestBody -> 에이작스 데이터를 전달 받을 때 사용.(요청 바디안에 있는걸 dto객체에 넣고싶을 때)
		//-> 쿼리 스트링안에서 값 읽어올때 @RequestParam 
		//PathVariable 값 읽어올때 @PathVariable
		
		log.info("updateComment(id={},dto={})",id,dto);
		
		commentSvc.update(dto);
		
		return ResponseEntity.ok(dto.getId()); // id를 넘겨도 됨. 업데이트한 댓글의 아이디를 응답으로 보냄
		//-> 업데이트한 댓글 자체를 넘길수도 있음 ResponseEntity<Comment>로 리턴타입으로 바꾸고 
		//commentSvc.update(dto);에서 Comment객체를 리턴 받도록 변경해서 리턴 보내면 된다고 함.
	}

}
