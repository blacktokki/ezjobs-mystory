<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:forEach var="item" items="${resumeGroup}" varStatus="status">
	<div class="card">
		<div class="card-header p-0" id="heading${status.index}">
			<h2 class="mb-0">
				<button class="btn btn-link" type="button" data-toggle="collapse"
					data-target="#collapse${status.index}" aria-expanded="true"
					aria-controls="collapse${status.index}">
						${item[0]}-${item[1]}
				</button>
			</h2>
		</div>
	
		<div id="collapse${status.index}" class="collapse"
			aria-labelledby="heading${status.index}" data-parent="#resume-group">
				<%@ include file="/WEB-INF/jsp/resume/list.jsp"%>
		</div>
	</div>
</c:forEach>