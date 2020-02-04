<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<div class="card card-title" style="padding: 10px">
	<div class="clearfix">
		<div class="float-left">
			<form class="search-form form-inline" method="get">
				<div class="input-group">
					<div class="input-group-prepend">
						<label class="input-group-text" for="inGroup01">조건 검색</label>
					</div>
					<select class="custom-select" id="inGroup01" name="op">
						<option>분류</option>
						<option value="tagSearch" <c:if test="${op eq 'tagSearch'}">selected</c:if>>태그 명</option>
						<option value="question" <c:if test="${op eq 'question'}">selected</c:if>>자기소개서 문항</option>
						<option value="company" <c:if test="${op eq 'company'}">selected</c:if>>회사명</option>
						<sec:authorize access="hasAuthority('ROLE_ADMIN')">
							<option value="userId" <c:if test="${op eq 'userId'}">selected</c:if>>작성자</option>
						</sec:authorize>
						<option value="state" <c:if test="${op eq 'state'}">selected</c:if>>작성상태</option>
					</select>
					<input name="keyword" type="text" class="form-control" placeholder="검색어" aria-label="Recipient's username" value="${keyword}" aria-describedby="button-addon2">
					<div class="input-group-append">
						<button class="btn btn-outline-secondary" type="submit" id="button-addon2">검색</button>
					</div>
				</div>
				<input type="hidden" name="page" value="${ page + 1}">
			</form>
		</div>
		<%@ include file="/WEB-INF/jspf/pageSize.jspf"%>
	</div>
</div>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:parseNumber value="${now.time / (1000*60*60*24)}" integerOnly="true" var="strDate"></fmt:parseNumber>
<c:forEach var="item" items="${resumes.content}" varStatus="status">
	<div class="card">
		<div class="card-header resume-card-header" role="tab" id="heading${status.index}" style="font-size: 14px">
			<a class="text-info resume-link" href="/resume/write/${item.id}">${item.company}<br>${item.question}</a><br>
			${item.tags}
			<c:if test="${not empty item.closeDate}">
				<fmt:parseNumber value="${item.closeDate.time / (1000*60*60*24)}" integerOnly="true" var="endDate"></fmt:parseNumber>
				D${strDate-endDate} 
			</c:if>
			<br>
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
				<button type="button" data-toggle="modal" data-target="#delete${item.id}" data-whatever="@mdo" style="border: 0; background: 0;">
					<i class="fa fa-times-circle" style="color: #FF8585"></i>삭제하기
				</button>
			</form>
			<!-- 삭제 모달 -->
				<div class="modal delete-modal" id="delete${item.id}" tabindex="-1"
					role="dialog">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title">자기소개서 삭제</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<form class="delete-form">
								<div class="modal-body">
									<p>정말로 "[${item.company}]${item.question }" 를 삭제 하시겠습니까?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">취소</button>
									<button type="submit" class="btn btn-danger">삭제</button>
									<input type="hidden" name="size" value="${size}">
									<input type="hidden" name="id" value="${item.id}">
									<input type="hidden" name="_method" value="delete">
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							</form>
						</div>
					</div>
				</div>
			<!-- 삭제 모달 끝 -->
		</div>
	</div>
</c:forEach>

<%@ include file="/WEB-INF/jspf/pageNavbar.jspf"%>