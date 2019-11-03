<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>

<style>
.board_list {width: 500px; margin: 0 auto;}
.board_list tfoot {text-align: center;}
.signUp_agree {text-align: center;}
.signUp_agree_textarea {text-align: center;}
.signUp_agree_textarea textarea {resize: none;}
</style>
</head>
<body>
<form id="frm">
 <table class="board_list">
  <caption>회원가입</caption>
  <thead>
   <tr>
    <td colspan="3" class="signUp_agree">약관동의</td>
   </tr>
   <tr>
    <td colspan="3" class="signUp_agree_textarea"><textarea cols="100" rows="20" readonly="readonly">회원가입을 하신다 함은...</textarea></td>
   </tr>
   <tr>
    <td colspan="3" class="signUp_agree_checkbox"><input type="checkbox" id="agree_checkbox">약관에 동의</td>
   </tr>
  </thead>
  <tbody>
   <tr>
    <th scope="row">이름</th>
    <td><input type="text" id="user_name" name="NAME" class="wdp_90"></td>
    <td></td>
   </tr>
   <tr>
    <th scope="row">전화번호</th>
    <td><input type="text" id="user_tel" name="TEL" class="wdp_90"></td>
    <td></td>
   </tr>
   <tr>
    <th scope="row">아이디</th>
    <td><input type="text" id="user_id" name="ID" class="wdp_90"></td>
    <td><a href="#" id="user_id_checkBtn" class="btn">중복확인</a></td>
   </tr>
   <tr>
    <th scope="row">비밀번호</th>
    <td><input type="password" id="user_pwd" name="PASSWORD" class="wdp_90"></td>
    <td></td>
   </tr>
  </tbody>
  <tfoot>
   <tr>
    <td colspan="3">
     <a href="#" class="btn" id="signUpBtn">회원가입</a>
     <a href="#" class="btn" id="homeBtn">취소</a>
    </td>
   </tr>
  </tfoot>
 </table>
</form>

</body>
</html>


