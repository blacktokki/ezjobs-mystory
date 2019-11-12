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
		<li class="nav-item"><a class="nav-link" href="/help/qna">Q&A</a></li>
		<li class="nav-item"><a class="nav-link active" href="/help/notice">공지사항</a></li>
		<li class="nav-item"><a class="nav-link" href="/help/faq">1:1문의</a></li>
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
			<!-- 이제 반복문 써서 글 여러개로 불리기 할 것 -->
			<tr>
				<th scope="row" class="text-center">1</th>
				<td>Mark</td>
				<td class="text-center">Otto</td>
				<td class="text-center">@mdo</td>
			</tr>
		</tbody>
	</table>

</div>

<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center">
		<li class="page-item"><a class="page-link" href="#"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
		<li class="page-item"><a class="page-link" href="#">1</a></li>
		<li class="page-item"><a class="page-link" href="#">2</a></li>
		<li class="page-item"><a class="page-link" href="#">3</a></li>
		<li class="page-item"><a class="page-link" href="#">4</a></li>
		<li class="page-item"><a class="page-link" href="#">5</a></li>
		<li class="page-item"><a class="page-link" href="#"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<p>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>