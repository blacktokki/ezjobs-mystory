<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div>
	<!-- write head -->
	<li class="nav-item d-flex">
		<a class="nav-link breadcrumb-item text-center" id="nav-resume-tab${resume.id}" data-toggle="tab" href="#nav-resume${resume.id}"
			role="tab" aria-controls="nav-resume${resume.id}" aria-selected="false">
			${resume.question}
		</a>
		<button type="button" class="nav-link close resume-close" aria-label="Close">
  			<span aria-hidden="true">&times;</span>
		</button>
	</li>
	
	<!-- write body -->
	<div class="tab-pane fade" id="nav-resume${resume.id}"
		role="tabpanel" aria-labelledby="nav-resume-tab${resume.id}">
		<form>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon1">자기소개서 문항</span>
				</div>
				<input type="text" class="form-control" name="question" value="${resume.question}">
			</div>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon2">회사명</span>
				</div>
				<input type="text" class="form-control" name="company" value="${resume.company}">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon2">마감일</span>
				</div>
				<input type="text" class="form-control" name="closeDate" value="${resume.closeDate}">
			</div>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="basic-addon3">태그</span>
				</div>
				<input name="tags" id="tags${resume.id}" value="
					<c:forEach var="tag" items="${resume.tags}">
						<c:if test="${tag.type ne '키워드'}">${tag.type}:</c:if>${tag.name},
					</c:forEach>
				"/>
			</div>
			<button type="button" class="button badge button-secondary tag-append-prompt">문항유형</button>
			<button type="button" class="button badge button-secondary tag-append-prompt">직무</button>
			<button type="button" class="button badge button-secondary tag-append">시작문장</button>
			<button type="button" class="button badge button-secondary tag-append">마지막문장</button>
			<div class="input-group">
				<div class="input-group-prepend">
					<span class="input-group-text">내용</span>
				</div>
				<textarea class="form-control" name="answer" id="answer${resume.id}" rows="20">${resume.answer}</textarea>
			</div>
			<button type="button" class="btn btn-primary resume-review" data-toggle="modal" data-target="#review-modal">검토하기</button>
			<button type="submit" class="btn btn-primary" name="_method" value="post" >다른 이름으로 저장하기</button>
			<button type="submit" class="btn btn-primary resume-method" name="_method" value="${method}" >저장하기</button>
			<button type="button" class="btn btn-primary resume-export">내보내기</button>
			
			<input type="hidden"  name="id" value="${resume.id}" /> 
			<input type="hidden"  name="_method" value="" /> 
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>
