<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<!-- 점보트론 스타일 -->
<style>
.jumbotron {
	background-image: url(/image/jumbotronBG.jpg);
	background-size: cover;
	background-position: 0% 40%;
	text-shadow: black -0.4em 0.4em 0.4em;
	color: white;
}

.row {
	margin-bottom: 10px;
}

[class|="col"] {
	background: #ffffff;
	border: 1px solid #eaeaed;
	height: 40px;
	font-size: .8em;
	line-height: 40px;
	text-align: left;
	color: black;
	font-weight: 700;
}
</style>
<div class="container">
	<div class="jumbotron">
		<h1 class="display-4">게시글 ${board.id} 번</h1>
		<p class="lead">${board.userId}님이적어준내용이에요.</p>
		<hr class="my-4">
		<p>글읽는중 ㅎㅎ</p>
	</div>
</div>

<p>
<div class="container">
	<h2>와글와글</h2>
	<p>
	<div class="row">
		<div class="col-2">제목</div>
		<div class="col-10">${board.title}</div>
	</div>
	<div class="row">
		<div class="col-2">작성자</div>
		<div class="col">${board.userId}</div>
		<div class="col-2">작성일</div>
		<div class="col">${board.editDate}</div>
	</div>

	<div class="border">
		${board.text}
	</div>
	<p>
	<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal.id" var="currentUserName"/>
	<c:if test="${currentUserName == board.userId}">
		<a class="btn btn-secondary btn-sm" href="/board/community"
			role="button">목록</a>
		<a class="btn btn-secondary btn-sm" href="/board/write/${board.id}"
			role="button">수정</a>
		<button type="button" data-toggle="modal"
			data-target="#delete" data-whatever="@mdo"
			style="border: 0; background: 0;">
			<i class="fa fa-times-circle" style="color: #FF8585"></i>삭제하기
		</button>
	</c:if>
	</sec:authorize>
	<div class="modal" id="delete" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">게시글 삭제</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<form method="post" action="/board/archive/${board.id}">
						<div class="modal-body">
							<p>정말로 "${board.id}. ${board.title}" 게시글을 삭제 하시겠습니까?</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">삭제</button>
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
				</div>
			</div>
		</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>