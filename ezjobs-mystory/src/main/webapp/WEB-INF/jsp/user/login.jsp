<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<style>
.board_list {width: 500px; margin:0 auto;}
.board_list tfoot {text-align: center;}
.btn_area {height: 35px; text-align: center;}
</style>


<form id="frm" method="post" action="/user/login">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">
  <tbody>
   <tr>
    <th scope="row">아이디</th>
    <td><input type="text" id="user_id" name="loginId" class="wdp_90 alert alert-dark"></td>
   </tr>
   <tr>
    <th scope="row">비밀번호</th>
    <td><input type="password" id="user_pwd" name="loginPw" class="wdp_90 alert alert-dark"></td>
   </tr>
   <tr>
   	<th scope="row">자동입력<br>방지문자</th>
   		<td>
   			<img src="/captcha/index" id="imgCaptcha" style="cursor: pointer"><br>
   			<input type="text" id="user_captcha" name="captcha" class="wdp_90 alert alert-dark">
   		</td>
   </tr>
   <tr>
    <td colspan='2'> 
    <button type="submit" id="login_btn" class="btn btn-secondary">로그인</button>
    <button type="button" onclick="location.href='join'" class="btn btn-secondary">회원가입</button>
    <button type="button" onclick="location.href='/user/password/new'" class="btn btn-secondary">비밀번호 찾기</button>
    </td>
   </tr>
   <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
   <tr>
	<td colspan='2'>
		<font color="red">
			로그인 실패<br>
			Reason:${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</font>
		<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
	</td>
   </tr>
   </c:if>
   <tr>
	<td colspan='2'>
		<c:forEach var="url" items="${urls}" varStatus="status">
		    <button type="button" onclick="location.href='${url.value}'" class="btn btn-primary">${url.key}로 로그인</button>
		</c:forEach>
	</td>
   </tr>
  </tbody>
 
 </table>
  
</form>
<script>
$("#imgCaptcha").click(function(){
    $(this).attr("src", "/captcha/index");
});
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
