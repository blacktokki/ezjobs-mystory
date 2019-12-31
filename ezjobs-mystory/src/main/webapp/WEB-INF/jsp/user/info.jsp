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

 <form method="post" action="/user/info">
 	<input type="hidden" name="_method" value="PUT">
 	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">

  <caption>회원 정보 수정</caption>
  <tbody>
    <tr>
    <tr>
    <th scope="row">비밀번호</th>
       <td><input type="password" id="user_loginPw" name="loginPw" class="wdp_90" value="${user.loginPw}"></td>
   </tr>
   <tr>
    <th scope="row">비밀번호 확인</th>
    	<td><input type="password" id="user_rePw" name="rePw" class="wdp_90" value="${user.loginPw}"></td>
   </tr>
    <tr>
    <th scope="row">이름</th>
    <td><input type="text" id="user_name" name="name" class="wdp_90" value="${user.name}"> </td>
   </tr>
    <tr>
    <th scope="row">이메일</th>
    <td><input type="text" id="user_email" name="email" class="wdp_90" value="${user.email}"></td>
   </tr>
   <!--
    <tr>
    <th scope="row">relId</th>
    <td><input type="text" id="user_relID" name="RELID" class="wdp_90"></td>
   </tr>
    <tr>
    <th scope="row">relLoginId</th>
    <td><input type="text" id="user_relLoginId" name="RELLOGINID" class="wdp_90"></td>
   </tr>
  -->
  <tr>
    <th scope="row">성별</th>
    <td>
    	<div class="container">
	        <input type="radio" id="user_sex" name="sex" value="남" class="wdp_90" <c:if test="${user.sex eq '남'}">checked</c:if>>남
	        <input type="radio" id="user_sex" name="sex" value="여" class="wdp_90" <c:if test="${user.sex eq '여'}">checked</c:if>>여
        </div>
    </td>
   </tr>
   <tr>
    <th scope="row">학력</th>
    <td>
    	<div class="container">
	    	<select id="user_grad" name="grad">
	          <option value="중졸" <c:if test="${user.grad eq '중졸'}">selected</c:if>>중졸</option>
	          <option value="고졸" <c:if test="${user.grad eq '고졸'}">selected</c:if>>고졸</option>
	          <option value="초대졸" <c:if test="${user.grad eq '초대졸'}">selected</c:if>>초대졸</option>
	          <option value="대졸" <c:if test="${user.grad eq '대졸'}">selected</c:if>>대졸</option>
	          <option value="대학원졸" <c:if test="${user.grad eq '대학원졸'}">selected</c:if>>대학원졸</option>
	       </select>
       </div>
    </td>
   </tr>
  
  </tbody>
  <tfoot>
   <tr>
    <td colspan="3">
     <button type="submit" class="btn btn-primary resume-submit">수정</button>
   <button type="button" onclick="location.href='out'" class="btn btn-primary resume-submit">탈퇴</button>
    </td>
   </tr>
  </tfoot>

 </table>
</form>

<script>
$(function(){
    $("form").validate({
    	rules: {
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
