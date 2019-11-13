<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->
<!-- 기본적으로 로그인이 되어 있어야지 들어 올 수 있는 페이지입니다. -->
<div class="jumbotron">
	<h1 class="display-4">1 대 1 문의</h1>
	<p class="lead">내가 물었던 질문</p>
	<hr class="my-4">
	<ul class="nav">
		<ul class="nav nav-tabs">
			<li class="nav-item"><a class="nav-link" href="/help/faq">FAQ</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="/help/notice">공지사항</a></li>
			<li class="nav-item"><a class="nav-link active" href="/help/qna">1:1 문의</a></li>

		</ul>
</div>
<div class="container">
<!-- 우측정렬 하기 -->
<a class="btn btn-primary btn-sm" href="/help/qnawrite" role="button">문의하기</a>
<p>
	<table class="table">
		<thead class="text-center">
			<tr>
				<th scope="col" style="width: 10%">No.</th>
				<th scope="col" style="width: 60%">제목</th>
				<th scope="col" style="width: 20%">작성날짜</th>
				<th scope="col" style="width: 10%">대답여부</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="item" items="${boards.content}">
				<tr>
					<td align="center">${item.id}</td>
					<td><a href="/help/qnacontent/${item.id}">${item.title}&nbsp;</a></td>
					<td align="center">${item.editDate}</td>
					<td>${item.goodCnt }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 우측정렬 하기 -->
<a class="btn btn-primary btn-sm" href="#" role="button">문의하기</a>
</div>
<p>


<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center">
		<li class="page-item"><a class="page-link"
			href="?page=${pageNavNumber*5}" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<c:forEach var="item" begin="${pageNavNumber*5+1}"
			end="${(pageNavNumber+1)*5}">
			<li class="page-item"><a class="page-link" href="?page=${item}">${item}</a></li>
		</c:forEach>
		<li class="page-item"><a class="page-link"
			href="?page=${(pageNavNumber+1)*5+1}" aria-label="Next"> <span
				aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<p>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>	