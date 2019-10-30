<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

	<!-- 점보트론 스타일 -->
	<style>
.jumbotron {
	background-image: url(/image/jumbotronBG.jpg);
	background-size: cover;
	background-position: 0% 40%;
	text-shadow: black -0.4em 0.4em 0.4em;
	color: white;
}
</style>
	<div class="container">
		<div class="jumbotron">
			<h1 class="display-4">자유게시판</h1>
			<p class="lead">회원님들의 이야기를 자유롭게 적어주세요.</p>
			<hr class="my-4">
			<p>자유게시판 ㅎㅎ</p>
		</div>
	</div>
	<p>
	<div class="container">
		<h2>게시판</h2>
		<p>
			<a class="btn btn-secondary btn-sm" href="/board/write" role="button">글쓰기</a>
		<div class="pagination justify-content-center">
			<table class="table">
				<!-- 테이블 제목 -->
				<thead>
					<tr bgcolor="#EDEDED">
						<th width="60">번호</th>
						<th>제목</th>
						<th width="150">작성자</th>
						<th width="270">작성일</th>
					</tr>
				</thead>
				<!-- 티에블 내용 채우기 -->
				<tbody>
							
				<c:forEach var="item" items="${content.boards}">
					<tr>
						<td align="center">${item.id}</td>
						<td><a href="/board/content/${item.id}">${item.title}&nbsp;</a></td>
						<td>${item.userId}&nbsp;</td>
						<td align="center">${item.editDate}</td>
					</tr>
				</c:forEach>
				</tbody>

			</table>
		</div>
		<a class="btn btn-secondary btn-sm" href="/board/write" role="button">글쓰기</a>

		<div class="pagination justify-content-center">
			<form method="post" action="nop.jsp">
				<table>
					<tr>
						<td><select name="searchOption">
								<option>글제목</option>
								<option>내용</option>
								<option>작성자</option>
						</select></td>
						<td><input type="text" size="40" name="searchText" /></td>
						<td><input type="submit" value="검색" /></td>
					</tr>
				</table>
				<p>
			</form>
		</div>

		<nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<li class="page-item"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
	</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
