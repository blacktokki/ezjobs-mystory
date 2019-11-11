<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<div class="container">
	<div class="jumbotron">
		<h1 class="display-4">회원번호 ${user.id}</h1>
		<p class="lead">${user.name}님의정보</p>
		<hr class="my-4">
	</div>
</div>

<p>
<div class="container">
	<h2>와글와글</h2>
	<p>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">항목</th>
				<th scope="col">내용</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th scope="row">회원번호</th>
				<td>${user.id}</td>
			</tr>
			<tr>
				<th scope="row">아이디</th>
				<td>${user.loginId}</td>
			</tr>
			<tr>
				<th scope="row">비밀번호</th>
				<td>${user.loginPw}</td>
			</tr>
			<tr>
				<th scope="row">이름</th>
				<td>${user.name}</td>
			</tr>
			<tr>
				<th scope="row">이메일</th>
				<td>${user.email}</td>
			</tr>
			<tr>
				<th scope="row">가입일</th>
				<td>${user.registDate}</td>
			</tr>
			<tr>
				<th scope="row">방문횟수</th>
				<td>${user.visitCnt}</td>
			</tr>
			<tr>
				<th scope="row">연동서비스명</th>
				<td>${user.relId}</td>
			</tr>
			<tr>
				<th scope="row">연동계정</th>
				<td>${user.relLoginId}</td>
			</tr>
			<tr>
				<th scope="row">성별</th>
				<td>${user.sex}</td>
			</tr>
			<tr>
				<th scope="row">학력</th>
				<td>${user.grad}</td>
			</tr>
		</tbody>
	</table>

	<p>

		<a class="btn btn-secondary btn-sm" href="/admin/user" role="button">회원목록</a>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>