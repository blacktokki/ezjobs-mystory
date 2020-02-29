<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<style>
.board_list {width: 500px; margin:0 auto;}
.board_list tfoot {text-align: center;}
.btn_area {height: 35px; text-align: center;}
</style>


<form id="frm" method="post" action="/user/password">
 <input type="hidden" name="_method" value="PUT">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">
  <tbody>
   <tr>
      <th scope="row">현재 비밀번호</th>
	  <td><input type="password" id="user_pwd" name="loginPw" class="wdp_90"></td>
   </tr>
   <tr>
    <th scope="row">새 비밀번호</th>
    <td><input type="password" id="user_newPw" name="newPw" class="wdp_90"></td>
   </tr>
   <tr>
    <th scope="row">새 비밀번호 확인</th>
    	<td><input type="password" id="user_rePw" name="rePw" class="wdp_90"></td>
   </tr>
   <tr>
    <td colspan='2'> 
    <button type="submit" id="login_btn" class="btn btn-secondary resume-submit">변경</button>
    <button type="button" onclick="location.href='/user/info'" class="btn btn-secondary resume-submit">취소</button>
    </td>
   </tr>
   <c:if test="${not empty changePwException}">
   <tr>
	<td colspan='2'>
		<font color="red">
			요청 실패<br>
			Reason:${sessionScope["changePwException"]}
		</font>
		<c:remove scope="session" var="changePwException"/>
	</td>
   </tr>
   </c:if>
  </tbody>
 </table>
</form>
<script>
window.addEventListener('DOMContentLoaded', function() {
	$("#imgCaptcha").click(function(){
	    $(this).attr("src", "/captcha/index");
	});
	
	$("form").validate({
		rules: {
	        loginPw: {
	            required : true,
	            minlength : 8,
	            maxlength : 15,
	        },
	        newPw: {
	            required : true,
	            minlength : 8,
	            maxlength : 15,
	        },
	        rePw: {
	            required : true,
	            minlength : 8,
	            maxlength : 15,
	            equalTo : user_newPw
	        }
	    },
	    //규칙체크 실패시 출력될 메시지
	    messages : {
	        loginPw: {
	            required : "필수로입력하세요",
	            minlength : "최소 {0}글자이상이어야 합니다",
	            maxlength : "최대 {0}글자이하이어야 합니다",
	        },
	        newPw: {
	            required : "필수로입력하세요",
	            minlength : "최소 {0}글자이상이어야 합니다",
	            maxlength : "최대 {0}글자이하이어야 합니다",
	        },
	        rePw: {
	            required : "필수로입력하세요",
	            minlength : "최소 {0}글자이상이어야 합니다",
	            maxlength : "최대 {0}글자이하이어야 합니다",
	            equalTo : "비밀번호가 일치하지 않습니다."
	        }
	    }
	});
});
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
