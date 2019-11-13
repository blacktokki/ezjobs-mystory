<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->
<div class="jumbotron">
	<h1 class="display-4">F A Q</h1>
	<p class="lead">자주 묻는 질문들</p>
	<hr class="my-4">
	<ul class="nav">
		<ul class="nav nav-tabs">
			<li class="nav-item"><a class="nav-link active" href="/help/faq">FAQ</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="/help/notice">공지사항</a></li>
			<li class="nav-item"><a class="nav-link" href="/help/qna">1:1
					문의</a></li>

		</ul>
</div>
<p>
	<!-- 이것도 DB를 써서 반복문을 사용할 부분. -->
<div class="accordion container" id="accordionExample">
	<c:forEach var="item" items="${boards.content}">
		<div class="card">
			<div class="card-header" id="heading${item.id}">
				<h2 class="mb-0">
					<button class="btn btn-link" type="button" data-toggle="collapse"
						data-target="#collapse${item.id}" aria-expanded="true"
						aria-controls="collapseOne">${item.title}</button>
				</h2>
			</div>

			<div id="collapse${item.id}" class="collapse"
				aria-labelledby="heading${item.id}" data-parent="#accordionExample">
				<div class="card-body">${item.text}</div>
			</div>
		</div>
	</c:forEach>
</div>

<p>

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>