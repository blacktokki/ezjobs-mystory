<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<style>
.board_list {width: 500px; margin:0 auto;}
.board_list tfoot {text-align: center;}
.btn_area {height: 35px; text-align: center;}
</style>


<form id="frm" method="post" action="/user/password">
 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <table class="board_list">
  <tbody>
   <tr>
    <th scope="row">아이디</th>
    <td><input type="text" id="user_id" name="id" class="wdp_90 alert alert-dark"></td>
   </tr>
   <tr>
    <th scope="row">이메일</th>
    <td><input type="email" id="user_email" name="email" class="wdp_90 alert alert-dark"></td>
   </tr>
   <tr>
    <td colspan='2'> 
    <button type="submit" id="login_btn" class="btn btn-secondary resume-submit">보내기</button>
    <button type="button" onclick="location.href='/user/login'" class="btn btn-secondary resume-submit">취소</button>
    </td>
   </tr>
  </tbody>
 </table>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
