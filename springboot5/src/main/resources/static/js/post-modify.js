/**
 * /post/modify.html 파일에 포함
 * 포스트 업데이트, 삭제 버튼 이벤트 처리
 */
// 버튼 찾을건데 DOMContentLoaded HTML 로드가 끝나야 찾으니까.

/* document.addEventListener('DOMContentLoaded',() => {
   // alert('test'); //->  포함 되면 alert 창뜨는 것 test
   
   //삭제 버튼 찾기 ->  클릭 이벤트 리스너 설정(삭제 하시겠습니까? -> 삭제)
   const btnDelete = document.getElementById('btnDelete');   
   //-> 이것도 사용가능함 const btnDelete = document.querySelector('button#btnDelete');
   btnDelete.addEventListener('click',() =>{
       const check = confirm('삭제 하시겠습니까?');
       if(check){ //사용자가 컨펌창에서 확인 누르면 (true) 실행됨
            //삭제시킬 id번호 알아야함
            const id = document.getElementById('id').value; //-> id값이 id인 태그요소를 찾아서 .value 값 찾음
            location.href = `/post/delete?id=${id}`; //${id}는 윗쪽의 const id 변수를 의미함.
            alert('삭제 되었습니다.');
       }
   });
 */

 document.addEventListener('DOMContentLoaded', () => {
    // 삭제 버튼을 찾고, 클릭 이벤트 리스너를 설정.
    const btnDelete = document.querySelector('button#btnDelete');
    btnDelete.addEventListener('click', () => {
        const check = confirm('정말 삭제할까요?');
        if (check) {
            const id = document.querySelector('input#id').value;
            location.href = `/post/delete?id=${id}`; //get매핑
        }
    });
  
   //업데이트 버튼을 찾고 -> 클릭 이벤트 리스너를 설정(데이터 빈칸 체크 + 수정 하시겠습니까? -> 수정)
   const btnUpdate = document.getElementById('btnUpdate');   

   btnUpdate.addEventListener('click',() =>{
       
          const id = document.getElementById('id').value;
          const title = document.getElementById('title').value.trim(); 
           //->trim() :문자열 시작과 끝 부분의 공백 제거 (문자열 사이의 중간 공백은 제거하지 않음) //-> 공백 입력만으로 수정되지 않도록.
          const content = document.getElementById('content').value.trim();
        // 빈칸 check
        if(title === '' || content === ''){
            alert('빈칸을 채워주세요. 제목과 내용 필수입력');
            return; //-> 이벤트 종료
        }
        
         const check = confirm('수정 하시겠습니까?');
                
        if(check){
            //업데이트 요청 보내기
            //-> 폼 요소를 찾아서 -> submit
            const updateForm = document.querySelector('form#updateForm'); //-> Form 태그 id 찾음
            updateForm.submit();
            alert('수정 되었습니다.');
        }
   });
   
});



/**
 * /post/modify.html 파일에 포함.
 * 포스트 업데이트, 삭제 버튼 이벤트 처리.
 */
/* document.addEventListener('DOMContentLoaded', () => {
    // 삭제 버튼을 찾고, 클릭 이벤트 리스너를 설정.
    const btnDelete = document.querySelector('button#btnDelete');
    btnDelete.addEventListener('click', () => {
        const check = confirm('정말 삭제할까요?');
        if (check) {
            const id = document.querySelector('input#id').value;
            location.href = `/post/delete?id=${id}`;
        }
    });
    
    // 업데이트 버튼을 찾고, 클릭 이벤트 리스너를 설정.
    const btnUpdate = document.querySelector('button#btnUpdate');
    btnUpdate.addEventListener('click', () => {
        const title = document.querySelector('input#title').value.trim();
        const content = document.querySelector('textarea#content').value.trim();
        // trim(): 문자열 시작과 끝에 있는 모든 공백을 제거. "  abc def  ".trim() -> "abc def"
        
        if (title === '' || content === '') {
            alert('제목과 내용은 반드시 입력해야 합니다!');
            return;
        }
        
        const check = confirm('변경된 내용을 저장할까요?');
        if (check) {
            const updateForm = document.querySelector('form#updateForm');
            updateForm.submit();
        }
        
    });
    
});

 */