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
<form method="post" action="/user/login">
<% String context = request.getContextPath(); %>
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
    <td><input type="text" id="user_id" name="loginId" class="wdp_90"></td>
   </tr>
   <tr>
    <th scope="row">비밀번호</th>
    <td><input type="password" id="user_pwd" name="loginPw" class="wdp_90"></td>
   </tr>
  </tbody>
 
 </table>

 <div class="btn">
  <button type="submit" id="login_btn" class="btn btn-primary resume-submit">
  로그인
     </button>
 </div>
 
  <div>

  <button type="button" onclick="location.href='join'">회원가입</button> </div>
</form>

</body>
</html>
