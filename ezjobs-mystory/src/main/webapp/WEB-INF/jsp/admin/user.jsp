<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>


<div class="jumbotron">
	<h1 class="display-4">회원관리</h1>
	<p class="lead">회원들을 관리해주세요</p>
	<hr class="my-4">
</div>
<p>

<div class="container">
	<h2>우리의 회원들</h2>
	<div class="pagination justify-content-center">
		<table class="table">
			<!-- 테이블 제목 -->
			<thead>
				<tr bgcolor="#EDEDED">
					<th width="120">회원번호</th>
					<th>아이디</th>
					<th width="270">가입일자</th>
					<th width="120">방문수</th>
				</tr>
			</thead>
			
			<!-- 티에블 내용 채우기 -->
			<tbody>
				<c:forEach var="item" items="${users.content}">
					<tr>
						<td align="center">${item.id}</td>
						<td><a href="/admin/personal/${item.id}">${item.loginId}&nbsp;</a></td>
						<td>${item.registDate}&nbsp;</td>
						<td align="center">${item.visitCnt}</td>
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

</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
