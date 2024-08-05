/**
 * /post/details.html에 포함 됨.
 * 댓글 생성, 목록 보기, 수정, 삭제
 */

 document.addEventListener('DOMContentLoaded',() => {
    
      let currentPageNo = 0; //현재 페이지 번호(현재 댓글 목록의). 현재 페이지 다보면 [더보기]
      //-> getAllComments() 댓글 목록 가져오는 함수에서 Ajax 요청을 보내고, 정상 응답이 오면 현재 페이지 번호가 바뀜.
      //-> currentPageNo값은 더보기 버튼 클릭 이벤트 리스너 실행시 사용됨
      let totalPages = 0; // 댓글 목록 전체 페이지 개수. 다음 댓글이 없으면 더보기버튼 동작 X
    //-> 스크립트 전체에서 사용하기 위해서 여기에서 선언함
    
    //alert('TEST'); //-> 포함 확인 위해
    
    //bootstrap 라이브러리의 collapse 객체를 생성함
    const bsCollapse = new bootstrap.Collapse('div#collapseComments', {toggle: false}); 
    //-> 생성자 호출(아규먼트로 태그#id값넣어줌,toggle: false 토글 상태가 false)
   
    //댓글 보기 버튼을 찾아서 클릭 이벤트 리스너 설정함
    const btnToggle = document.querySelector('button#btnToggle');
    btnToggle.addEventListener('click',() => {
        bsCollapse.toggle(); //-> 토글 메서드 호출. Collapse객체를 보기/감추기 반복 토글.(펼쳤다 접혔다)
        
        const toggle = btnToggle.getAttribute('data-toggle'); //-> 토글 값 읽음(getAttribute) (값변경은 setAttribute)
        
        if(toggle === 'collapse'){ //->data-toggle=? 값이 아니라 if 조건을(btnToggle.innerHTML값으로) 댓글 감추기, 댓글 보기로 바꿔도 된다고 함
            //=== : 데이터 타입까지 비교(비교대상 타입의 자동 형변환 하지 않고 비교함)
            btnToggle.innerHTML = '댓글 감추기';
            btnToggle.setAttribute('data-toggle','unfold'); //이게 있어야 반복이 됨
            
            //댓글 목록 불러오기
            getAllComments(0); //-> 처음엔 0페이지부터 보여주기때문에 
            //아규먼트로 넣어주어야할 pageNo(현재 페이지에 해당하는)값을 0으로 호출함
            
        } else {
            btnToggle.innerHTML = '댓글 보기';
            btnToggle.setAttribute('data-toggle','collapse'); //이게 있어야 반복이 됨
        }
        
        
    });

    //댓글 등록하는 버튼(id="btnRegisterComment")을 찾아서, 클릭 이벤트 리스너를 설정함
    const btnRegisterComment = document.querySelector('button#btnRegisterComment');
    btnRegisterComment.addEventListener('click', registerComment); // => {} 화살표 함수 대신 함수를 하나 만들어서 호출하는걸로 대체함
        
    // 댓글 더보기 버튼(id="btnMoreComments")을 찾아서, 클릭 이벤트 리스터를 설정
    const btnMoreComments = document.querySelector('button#btnMoreComments');
    btnMoreComments.addEventListener('click',() => getAllComments(currentPageNo + 1)); 
    //더보기 클릭시 이벤트 리스너 실행되어서  getAllComments(currentPageNo + 1) :  현재페이지 번호 +1 한 값을 아규먼트로 넣어서 호출함
    
    
    //----- 함수 정의(선언) ---------
    function registerComment() {
        //댓글 등록(insert) 위해 필요한 정보들 - 댓글 내용, 댓글 작성자, 몇번째 글의 post인지(postId)
        //댓글이 달린 포스트의 아이디값을.value 찾음
        const postId = document.querySelector('p#id').textContent;
        //html의 <p id="id" class="card-text ps-2 text-body-secondary" th:text="|글 번호 : *{id}|" style="font-style: italic;"></p> 
        //여기에서 찾음
        
        //댓글 내용 찾음 
        //<textarea class="form-control ms-4" id="commentText" rows="3" placeholder="댓글을 작성하세요"></textarea> 여기에서 찾음
        const ctext = document.querySelector('textarea#commentText').value;
        
        //댓글 작성자 찾음
        //<input class="mt-2 d-none" id="commentWriter" value="admin" readonly/> 여기에서 찾음
        const writer = document.querySelector('input#commentWriter').value;
        
        //댓글 내용이 비어있는지 검사함(댓글 내용을 문자열 앞뒤로 .trim() 공백 제거해서 검사함,댓글 입력창에 공백만 사용시 댓글 등록 불가)
        if(ctext.trim() === ''){
            alert('댓글 내용을 입력하세요.');
            return;
        }
        
        //Ajax 요청에서 보낼 데이터
        const data = {postId, ctext, writer}; //data : 자바스크립트 객체 선언
        
 //       const data = {
//            postId: postId,
//            ctext: ctext,
//            writer: writer
//        } //=> 이걸 간단히 사용한 것 const data = {postId, ctext, writer}; 프로퍼티 이름과 값이 같으면 이렇게 사용 가능하다고 함

//       const obj = { //obj는 객체 {}안에 (프로퍼티이름: 값)으로 구성함
//            age: 10, //age 객체의 프로퍼티(Properties.필드) 이름. 10은 프로퍼티에 저장하는 값
//            name: '홍길동'
//        }

        //Ajax(axios사용)로 요청보냄(POST방식) -> 응답/에러 처리 콜백 등록
        axios.post('/api/comment', data) //->/context-path/api/comment    
        .then((response) => {
            console.log(response.data);
            alert('댓글이 등록 되었습니다.');
            
            //댓글 내용 입력란을 빈 문자열로 만듬
            document.querySelector('textarea#commentText').value='';
            
            // 댓글 목록 갱신
            getAllComments(0);
            
        }) //->/api/comment post요청방식으로 매핑된 컨트롤러에서 정상 응답이 왔을 때 실행 됨.
        .catch((error) => console.log(error)); // 에러 응답이 왔을 때 실행 됨   
        
    }
    
    //댓글 목록 가져오는 함수(현재페이지번호 pageNo가 아규먼트로 들어감)
    function getAllComments(pageNo){ //자바스크립트 함수에서의 파라미터 선언은 변수이름만 선언하면 됨(변수타입은 쓰지않음.)
        //-> pageNo : getAllComments함수를 호출하는 곳에서 넣어주는 값. 페이지 번호.
        //댓글들이 달린 포스트 아이디를 찾음.
        const postId = document.querySelector('p#id').textContent;
        
        //Ajax 요청을 보낼 주소:
        const uri = `/api/comment/all/${postId}?p=${pageNo}`; //${postId} : 위에서 선언한 변수 postId값이 들어감
        //-> path variable(${postId}): 댓글이 달린 포스트 아이디. request param (요청파라미터p=${pageNo}): 페이지 번호
        
        //Ajax 요청을 보내고, 성공/실패 콜백 설정
        axios.get(uri)
        .then((response)=> {
            console.log(response);
            //댓글 목록을 HTML로 작성 - > 메서드 호출로 대체함
            currentPageNo = response.data.number; //currentPageNo 현재 페이지 번호값을 현재보고있는 페이지 정보값으로 변경함
            totalPages = response.data.totalPages; // 전체 페이지 수를 업데이트
            makeCommentElements(response.data.content, response.data.number); //response.data.number 이게 현재 페이지 정보
            //-> response.data.content 이게 댓글 목록 정보
            //TODO - 더보기 버튼 상태 결정
             // 현재 페이지가 마지막 페이지인지 확인하여 "더보기" 버튼의 상태를 결정
                if (currentPageNo + 1 >= totalPages) {
                    // 마지막 페이지라면 "더보기" 버튼을 숨김
                    btnMoreComments.style.display = 'none';
                } else {
                    // 마지막 페이지가 아니라면 "더보기" 버튼을 보임
                    btnMoreComments.style.display = 'block';
                }
           //TODO 끝
            
        })
        .catch((error) => console.log(error));
    }
    
    //댓글 목록을 HTML로 작성하는 메서드. 
    //함수 호출 시에 컨트롤러에서 리턴 보낸 응답(response.data.content)객체와 현재페이지 번호(pageNo)를 아규먼트로 전달받음
    function makeCommentElements(data, pageNo){
        // 댓글 목록을 추가할 div 요소
        const divComments = document.querySelector('div#divComments');
        
        let htmlStr = ''; //div에 삽입할 HTML코드(댓글 목록을 화면에 그려주는)
        //댓글 개수만큼 반복해야함
        for(let comment of data){
            //for(in) 배열에서 index(번호)를 줌
            //for(of) 배열에서 data를 줌
           // console.log(comment);
           htmlStr += `
           <div class="card card-body my-3">
                <div class="d-flex justify-content-between align-items-center">
                    <!-- 왼쪽에 위치한 요소들 (작성자, 수정 시간) -->
                    <div>
                        <span class="fw-bold ms-2">${comment.writer}</span>
                        <span class="text-secondary small-text">${comment.modifiedTime}</span>
                    </div>
                
                    <!-- 오른쪽에 위치한 버튼들 -->
                    <div class="ms-auto">
                        <button class="btnDelete btn btn-light btn-sm" data-id="${comment.id}">삭제</button>
                        <button class="btnUpdate btn btn-light btn-sm" data-id="${comment.id}">수정</button>
                    </div>
                </div>
             <div>
                <div class="my-3">
                    <span class="p-3">${comment.ctext}</span>
                </div>
             </div>
           </div>`;
        }
        
        if(pageNo === 0){ //0일때 첫페이지일때는 새로 그리기(기존 목록 보여준 걸 다 지우고 화면에 새로 그림)
          divComments.innerHTML = htmlStr;
        } else{ //첫 페이지 이후 부터는 덧붙이기(기존 댓글 목록 밑에 추가해서 화면에 그림)
            divComments.innerHTML += htmlStr;
        }
        
        //중요!!) 댓글 삭제, 수정 버튼들의 이벤트 리스너는 버튼들이 생겨난 이후에 등록(코드 위치 중요함)해야 함
        //모든 button,btnDelete 버튼들을 찾아서 클릭 이벤트 리스너를 등록.
        const btnDeletes = document.querySelectorAll('button.btnDelete'); //-> 클래스 이름으로 버튼들을 찾음
        btnDeletes.forEach((btn) => { //-> 버튼들을 1개씩 꺼내서 클릭 이벤드 리스너 등록함(클릭시 실행 코드는 함수 호출 deleteComment)
            btn.addEventListener('click', deleteComment);
        });
        
    }
    
    function deleteComment(event){ //필요시에 event(이벤트객체) 선언해서 사용함.
       // console.log(event);
      //  console.log(event.target); //-> event.target 필요함 commentId찾기 위해서.
      if(!confirm('삭제하시겠습니까?')){
        return; //-> 아니오 클릭시 이벤트 종료.
      }
      
      //네 라고 클릭시 실행되는 코드
      const id = event.target.getAttribute('data-id'); //.getAttribute: 속성값을 읽음. 삭제할 댓글 아이디 commentId
      const uri = `/api/comment/${id}`; // 삭제 Ajax 요청을 보낼 주소. ${id}: 위의 이벤트객체.타겟에서 찾은 댓글 id값
      axios.delete(uri)
      .then((response) => {
        console.log(response);
        //댓글 삭제 성공시 실행되는 코드
        alert(`#${id}번 댓글 삭제 성공`);
        getAllComments(0); // 댓글 목록 갱신(0페이지부터)
      }) //-> 성공 응답이 오면 할일
      .catch((error)=>console.log(error)); //-> 실패 응답시 할일
      
    }

 });
 