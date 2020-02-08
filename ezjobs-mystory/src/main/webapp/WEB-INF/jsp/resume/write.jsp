<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="card" id="resume-card${resume.id}">
	<div class="card-header" role="tab" id="heading-i" style="font-size: 14px">
		<a class="text-info collapsed" data-toggle="collapse" href="#collapse-i" aria-expanded="false" aria-controls="collapse-i">
			${resume.question}
		 </a>
	</div>
	<div id="collapse-i" class="collapse" role="tabpanel" aria-labelledby="heading-i" data-parent="#accordion2">
		<form>
			<div class="card-body">

				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon1">자기소개서 문항</span>
					</div>
					<input type="text" id=" write-question-i"
						class="form-control write-question" name="question"
						value="${resume.question}">
				</div>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon2">회사명</span>
					</div>
					<input type="text" class="form-control" name="company"
						value="${resume.company}">
						
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon2">마감일</span>
					</div>
					<input type="text" class="form-control write-date" name="closeDate"
						value="${resume.closeDate}">
				</div>

				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon3">태그</span>
					</div>
					<input name="tags" class="tags" value="${resume.tags}" />
				</div>
				<button type="button" class="button badge button-secondary tag-append-prompt">문항유형</button>
				<button type="button" class="button badge button-secondary tag-append-prompt">직무</button>
				<button type="button" class="button badge button-secondary tag-append">시작문장</button>
				<button type="button" class="button badge button-secondary tag-append">마지막문장</button>
				<div class="input-group">
					<div class="input-group-prepend">
						<span class="input-group-text">내용</span>
					</div>
					<textarea class="form-control write-answer" id=" write-answer-i" name="answer" rows="20">${resume.answer}</textarea>
				</div>
				<button type="button" class="btn btn-primary resume-review" data-toggle="modal" data-target="#review-modal">검토하기</button>
				<button type="submit" class="btn btn-primary" name="_method" value="post" >다른 이름으로 저장하기</button>
				<button type="submit" class="btn btn-primary resume-method" name="_method" value="${method}" >저장하기</button>
				<button type="button" class="btn btn-primary resume-export">내보내기</button>
				<input type="hidden" class="resume-id" name="id" value="${resume.id}" /> 
				<input type="hidden" class="resume-method" name="_method" value="" /> 
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</div>
		</form>
	</div>
</div>


