<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:forEach var="item" items="${resumesWrited.content}" varStatus="status">
	<div class="card">
		<div class="card-header resume-card-header" role="tab" id="heading${status.index}" style="font-size: 14px">
			<a class="text-info resume-link" href="/resume/write/${item.id}">${item.question}</a> - ${item.tags}<br>

			<a class="text-info collapsed" data-toggle="collapse" href="#collapse${status.index}" aria-expanded="false" aria-controls="collapse${status.index}"></a>
		</div>
		<div id="collapse${status.index}" class="collapse" role="tabpanel"
			aria-labelledby="heading${status.index}" data-parent="#accordion1">
			<form>
				<div class="card-body">
					<%--
					<c:forEach var="item2" items="${resumesSplit[status.index]}">
						${item2}<br>
					</c:forEach>
					--%>
					${item.answer}
					<input type="hidden" name="id" value="${item.question}"/>
				</div>
			</form>
			<a class="resume-link-state" href="/resume/state/${item.id}">
				<button class="btn btn-info float-right">작성목록으로 이동</button>
			</a>
		</div>
	</div>
</c:forEach>