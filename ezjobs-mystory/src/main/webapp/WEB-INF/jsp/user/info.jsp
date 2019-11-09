<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
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
 <form method="post" action="/user/info">
 <table class="board_list">

  <caption>회원 정보 수정</caption>
  <tbody>
      
 <!-- <tr>
    <th scope="row">회원번호</th>
    <td><input type="text" id="user_id" name="id" class="wdp_90"></td>
    <td></td>
  </tr> 
   -->
    <tr>
    <tr>
    <th scope="row">비밀번호</th>
    <td><input type="password" id="user_loginPw" name="loginPw" class="wdp_90"></td>
    <td></td>
   </tr>
    <tr>
    <th scope="row">이름</th>
    <td><input type="text" id="user_name" name="name" class="wdp_90"></td>
    <td></td>
   </tr>
    <tr>
    <th scope="row">이메일</th>
    <td><input type="text" id="user_email" name="email" class="wdp_90"></td>
    <td></td>
   </tr>
   <!-- 
  <tr>
    <th scope="row">editDate</th>
    <td><input type="text" id="user_editDate" name="regist_date" class="wdp_90"></td>
    <td></td>
   </tr>
 -->
  <tr>
    <th scope="row">방문자</th>
    <td><input type="text" id="user_visitCnt" name="cisit_cnt" class="wdp_90"></td>
    <td></td>
   </tr>
   <!--
    <tr>
    <th scope="row">relId</th>
    <td><input type="text" id="user_relID" name="RELID" class="wdp_90"></td>
    <td></td>
   </tr>
    <tr>
    <th scope="row">relLoginId</th>
    <td><input type="text" id="user_relLoginId" name="RELLOGINID" class="wdp_90"></td>
    <td></td>
   </tr>
  -->
 <tr>
    <th scope="row">성별</th>
    <td><input type="text" id="user_sex" name="sex" class="wdp_90"></td>
    <td></td>
   </tr>
     <tr>
    <th scope="row">학력</th>
    <td><input type="text" id="user_grad" name="grad" class="wdp_90"></td>
    <td></td>
   </tr>
  
  </tbody>
  <tfoot>
   <tr>
    <td colspan="3">
     <button type="submit" class="btn btn-primary resume-submit">수정</button>
    </td>
   </tr>
  </tfoot>

 </table>
</form>

</body>
</html>