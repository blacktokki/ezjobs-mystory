<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:forEach var="item" items="${resumes}" varStatus="status">
	<div class="card">
		<div class="card-header" role="tab" id="heading${status.index}" style="font-size: 14px">
			<a class="text-info resume-link" href="/resume/write/${item[0]}">${item[1]}</a><br>
			<a class="text-info collapsed" data-toggle="collapse" href="#collapse${status.index}" aria-expanded="false" aria-controls="collapse${status.index}"></a>
		</div>
		<div id="collapse${status.index}" class="collapse" role="tabpanel"
			aria-labelledby="heading${status.index}" data-parent="#accordion1">
			<form>
				<div class="card-body">
					${item[2]}		
					<input type="hidden" name="id" value="${item[0]}"/>
				</div>
			</form>
		</div>
	</div>
</c:forEach>