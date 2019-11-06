<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<style>
.board_list {width: 500px; margin: 0 auto;}
.board_list tfoot {text-align: center;}
.btn_area {height: 35px; text-align: center;}
</style>
</head>
<body>
<form id="frm">
 <table class="board_list">
  <colgroup>
  </colgroup>
  <caption>로그인</caption>
  <thead>
  </thead>
  <tbody>
   <tr>
    <th scope="row">아이디</th>
    <td><input type="text" id="user_id" name="ID" class="wdp_90"></td>
   </tr>
   <tr>
    <th scope="row">비밀번호</th>
    <td><input type="password" id="user_pwd" name="PASSWORD" class="wdp_90"></td>
   </tr>
  </tbody>
 
 </table>

 <div class="btn">
  <button id="login_btn">
  로그인
     </button>
 </div>

  <div class="btn">
  <button id="Join_btn">
  회원가입
  </button>
 </div>
</form>

<script>

$(document).ready(function() {
 $("#Login_btn").unbind("click").click(function(e) {
  e.preventDefault();
  fn_login();
 }); 
});
 
function fn_login() {
 if($("#user_id").val().length < 1)
 {
  alert("아이디를 입력해주세요.");
 }
 else if($("#user_pwd").val().length < 1)
 {
  alert("비밀번호를 입력해주세요.");
 }
 else
 {
  var comSubmit = new ComSubmit("frm");
  comSubmit.setUrl("<c:url value='/user/loginTry.do' />");
  comSubmit.submit();
 }
}
</script>

</body>
</html>
