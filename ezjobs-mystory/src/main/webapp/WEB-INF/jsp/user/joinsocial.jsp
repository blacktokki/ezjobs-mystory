<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<style>
.board_list {width: 500px; margin: 0 auto;}
.board_list tfoot {text-align: center;}
.signUp_agree {text-align: center;}
.signUp_agree_textarea {text-align: center;}
.signUp_agree_textarea textarea {resize: none;}
</style>
 <form method="post" action="/user/join/social">
 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">

  <caption>회원 정보 수정</caption>
  <tbody>
    <tr>
    <th scope="row">이름</th>
    <td><input type="text" id="user_name" name="name" class="wdp_90" value="${user.name}"> </td>
   </tr>
    <tr>
    <th scope="row">이메일</th>
    <td><input type="text" id="user_email" name="email" class="wdp_90" value="${user.email}"></td>
   </tr>
  <tr>
    <th scope="row">성별</th>
    <td>
    	<div class="container">
	        <input type="radio" id="user_sex" name="sex" value="남" class="wdp_90">남
	        <input type="radio" id="user_sex" name="sex" value="여" class="wdp_90">여
        </div>
    </td>
   </tr>
   <tr>
    <th scope="row">학력</th>
    <td>
    	<div class="container">
	    	<select id="user_grad" name="grad">
	          <option value="">선택</option>
	          <option value="중졸">중졸</option>
	          <option value="고졸">고졸</option>
	          <option value="초대졸">초대졸</option>
	          <option value="대졸">대졸</option>
	          <option value="대학원졸">대학원졸</option>
	       </select>
       </div>
    </td>
   </tr>
  
  </tbody>
  <tfoot>
   <tr>
    <td colspan="3">
     <button type="submit" class="btn btn-primary resume-submit">저장</button>
   <button type="button" onclick="location.href='/user/logout'" class="btn btn-primary resume-submit">취소</button>
    </td>
   </tr>
  </tfoot>

 </table>
</form>

<script>
window.addEventListener('DOMContentLoaded', function() {
    $("form").validate({
    	rules: {
            name: {
                required : true,
                minlength : 2
            },
            email: {
                required : true,
                minlength : 2,
                email : true
            },
            sex:{
            	required : true
            },
            grad:{
            	required : true
            }
        },
        //규칙체크 실패시 출력될 메시지
        messages : {
            name: {
                required : "필수로입력하세요",
                minlength : "최소 {0}글자이상이어야 합니다"
            },
            email: {
                required : "필수로입력하세요",
                minlength : "최소 {0}글자이상이어야 합니다",
                email : "메일규칙에 어긋납니다"
            }
        },
        errorPlacement: function(error, element){
            if ( element.is(":radio")) 
            {
                error.appendTo( element.parents('.container'));
            }
            else 
            { // This is the default behavior 
                error.insertAfter( element );
            }
         }
    });
});
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
