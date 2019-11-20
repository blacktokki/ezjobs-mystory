<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<div class="container">
	<div class="jumbotron">
		<h1 class="display-5">회원번호 ${user.id}</h1>
		<p class="lead">${user.name}님의정보</p>
	</div>
</div>

<p>
<div class="container">
	<h2>정보정보</h2>
	<p>
	<div class="row">

		<div class="col-6">
			<table class="table table-bordered table-sm">
				<colgroup>
					<col width="200">
					<col width="*">
				</colgroup>
				<thead class="thead-light">
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
		</div>

		<div class="col-6"></div>

	</div>
	<div class="row">
		<div class="col-2">
			<a class="btn btn-secondary" href="/admin/user" role="button">회원목록</a>
		</div>
		<div class="col offset-1">
			<button type="button" class="btn btn-secondary" data-toggle="modal"
				data-target="#stop">정지</button>
			<button type="button" class="btn btn-secondary" data-toggle="modal"
				data-target="#unStop">정지 해제시키기</button>
			<button type="button" class="btn btn-danger" data-toggle="modal"
				data-target="#bye">탈퇴</button>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="stop" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">정말로 정지 시키시겠습니까?</h3>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">해당 회원은 활동이 불가능한 상태가 됩니다.</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-warning">정지 시키기</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="unStop" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">정지 해제 시키시겠습니까?</h3>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">해당 회원은 활동 가능 상태가 됩니다.</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-primary">정지 해제</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="bye" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title" id="exampleModalLabel">정말로 탈퇴 시키시겠습니까?</h3>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">해당 회원을 탈퇴 상태가 됩니다.</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger">탈퇴 시키기</button>
				</div>
			</div>
		</div>
	</div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>