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

 <form method="post" action="/user/join">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">
  <caption></caption>
  <tbody>
    <tr>
    <th scope="row">아이디</th>
    <td><input type="text" id="user_id" name="id" placeholder="6자 이상 15자 이하" class="wdp_90"></td>
   </tr>
    <tr>
    <th scope="row">비밀번호</th>
    	<td><input type="password" id="user_loginPw" name="loginPw" placeholder="8자 이상 15자 이하" class="wdp_90"></td>
   </tr>
   <tr>
    <th scope="row">비밀번호 확인</th>
    	<td><input type="password" id="user_rePw" name="rePw" placeholder="8자 이상 15자 이하" class="wdp_90"></td>
   </tr>
    <tr>
    <th scope="row">이름</th>
    <td><input type="text" id="user_name" name="name" class="wdp_90"></td>
   </tr>
    <tr>
    <th scope="row">이메일</th>
    <td><input type="text" id="user_email" name="email" class="wdp_90"></td>
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
     <button type="submit" class="btn btn-secondary resume-submit">회원가입</button>

  <tbody>
      
  
  </tbody>


 </table>
</form>
<script>
$(function(){
    $("form").validate({
    	rules: {
            id: {
                required : true,
                minlength : 6,
                maxlength : 15,
                remote: "./check_id"
            },
            loginPw: {
                required : true,
                minlength : 8,
                maxlength : 15,
            },
            rePw: {
                required : true,
                minlength : 8,
                maxlength : 15,
                equalTo : user_loginPw
            },
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
        	id: {
                required : "필수로입력하세요",
                minlength : "최소 {0}글자이상이어야 합니다",
                maxlength : "최대 {0}글자이하이어야 합니다",
                remote : "존재하는 아이디입니다"
            },
            loginPw: {
                required : "필수로입력하세요",
                minlength : "최소 {0}글자이상이어야 합니다",
                maxlength : "최대 {0}글자이하이어야 합니다",
            },
            rePw: {
                required : "필수로입력하세요",
                minlength : "최소 {0}글자이상이어야 합니다",
                maxlength : "최대 {0}글자이하이어야 합니다",
                equalTo : "비밀번호가 일치하지 않습니다."
            },
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


