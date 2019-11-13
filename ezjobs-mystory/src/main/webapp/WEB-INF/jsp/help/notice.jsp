<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->
<div class="jumbotron">
	<h1 class="display-4">공지사항</h1>
	<p class="lead">홈페이지 공지사항</p>
	<hr class="my-4">
	<ul class="nav nav-tabs">
		<li class="nav-item"><a class="nav-link" href="/help/faq">FAQ</a></li>
		<li class="nav-item"><a class="nav-link active"
			href="/help/notice">공지사항</a></li>
		<li class="nav-item"><a class="nav-link" href="/help/qna">1:1문의</a></li>
	</ul>
</div>
<p>
<div class="container">
	<table class="table">
		<thead class="text-center">
			<tr>
				<th scope="col" style="width: 10%">No.</th>
				<th scope="col" style="width: 60%">제목</th>
				<th scope="col" style="width: 10%">작성자</th>
				<th scope="col" style="width: 20%">날짜</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach var="item" items="${boards.content}">
				<tr>
					<td align="center">${item.id}</td>
					<td><a href="/help/noticecontent/${item.id}">${item.title}&nbsp;</a></td>
					<td>관리자</td>
					<td align="center">${item.editDate}</td>
				</tr>
			</c:forEach>
		</tbody>



	</table>

</div>

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