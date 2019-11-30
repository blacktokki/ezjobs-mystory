<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<div class="container">
	<div class="jumbotron">
		<h1 class="display-6">자기소개서 관리</h1>
		<p class="lead">자기소개서를 관리 해봅시다</p>
	</div>

	<h2>모두의 자기소개서</h2>

	<!-- 겁색창 -->
	<div class="card card-title" style="padding: 10px">
		<div class="row">
			<div class="col-6">
				<form method="post" action="#">
					<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="inGroup01">검색 옵션</label>
						</div>
						<select class="custom-select" id="inGroup01" name="op">
							<option value="tagSearch">태그 명</option>
							<option value="question">자기소개서 문항</option>
							<option value="answer">자기소개서 내용</option>
							<option value="companry">회사명</option>
							<option value="userId">작성자</option>
						</select> <input name="sch" type="text" class="form-control"
							placeholder="검색어" aria-label="Recipient's username"
							aria-describedby="button-addon2">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"
								id="button-addon2">검색</button>
						</div>
					</div>
					<input type="hidden" name="showNum" value="${showNum }"> <input
						type="hidden" name="page" value="${ pageNavNumber + 1}">
				</form>
			</div>
			<div class="col offset-3">
				<div class="input-group">
					<div class="input-group-prepend">
						<label class="input-group-text" for="inGroup02">페이지 당</label>
					</div>
					<form method="get" action="#">
						<input type="hidden" name="page" value="${ pageNavNumber + 1}">
						<select class="custom-select" id="inGroup02" name="showNum"
							onchange="this.form.submit()">
							<option selected>현재 ${showNum} 개</option>
							<option value=10>10 개 보기</option>
							<option value=20>20 개 보기</option>
							<option value=30>30 개 보기</option>
							<option value=50>50 개 보기</option>
							<option value=100>100 개 보기</option>
						</select>
					</form>
				</div>

			</div>
		</div>
	</div>
	<!-- 검색창 끝 -->

	<div class="pagination justify-content-center">
		<table class="table table-sm table-hover">
			<colgroup>
				<col width="20">
				<col width="50">
				<col width="80">
				<col width="*">
				<col width="300">
				<col width="100">
				<col width="150">
				<col width="45">
				<col width="45">
			</colgroup>

			<!-- 테이블 제목 -->
			<thead class="thead-light text-center">
				<tr>
					<th><input type="checkbox" name="checkAll" id="th_checkAll"
						onclick='checkAll();' /></th>
					<th scope="col">#</th>
					<th scope="col">태그</th>
					<th scope="col">자기소개서 문항</th>
					<th scope="col">자기소개서 내용</th>
					<th scope="col">회사명</th>
					<th scope="col">작성자</th>
					<th scope="col">수정</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>

			<!-- 테이블 내용 채우기 -->
			<tbody class="text-center">
				<!-- 각 행들 -->
				
				<tr>
					<td><input type="checkbox" name="tmp" value="null"/></td>
						<td>1</td>
						<td align="left">학업 취업</td>
						<td align="left">동아대학교 테스트 자기소개서</td>
						<td align="left">안녕하세요. 저는 테스트 내용입니다.</td>
						<td>동아대학교</td>
						<td>test78697</td>
						<td><button style="border: 0; background: 0;">
								<i class="fa fa-pencil"></i>
							</button></td>
						<td><button style="border: 0; background: 0;">
								<i class="fa fa-times-circle" style="color: #FF8585"></i>
							</button></td>
				</tr>
				
				
				<c:forEach var="item" items="${resumes.content}">
					<tr>
						<td><input type="checkbox" name="checkRow" value="${item.id}" /></td>
						<td>${item.id}</td>
						<td align="left">${item.tags}</td>
						<td align="left">${item.question}</td>
						<td align="left">${item.answer}</td>
						<td>${item.company}</td>
						<td>${item.userId}</td>
						<td><button>
								<i class="fa fa-pencil"></i>
							</button></td>

						<td><button type="button" data-toggle="modal"
								data-target="#delete${item.id}" data-whatever="@mdo"
								style="border: 0; background: 0;">
								<i class="fa fa-times-circle" style="color: #FF8585"></i>
							</button></td>
					</tr>
					<!-- 각 행들 끝 -->

					<!-- 삭제 모달 -->
					<div class="modal" id="delete${item.id}" tabindex="-1"
						role="dialog">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title">태그 삭제</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="post" action="#">
									<div class="modal-body">
										<p>정말로 "${item.id }. ${item.name }" 태그를 삭제 하시겠습니까?</p>
									</div>
									<div class="modal-footer">
										<button type="cancel" class="btn btn-secondary"
											data-dismiss="modal">취소</button>
										<button type="submit" class="btn btn-danger">삭제</button>
										<input type="hidden" name="showNum" value="${showNum}">
										<input type="hidden" name="delTagId" value="${item.id}">
								</form>
							</div>
						</div>
					</div>
					<!-- 삭제 모달 끝 -->
				</c:forEach>
				
			</tbody>
		</table>
	</div>

</div>

<!-- 페이징 바 -->
<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center">
		<li class="page-item"><a class="page-link"
			href="?page=${pageNavNumber*5}" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<c:forEach var="item" begin="${pageNavNumber*5+1}"
			end="${(pageNavNumber+1)*5}">
			<li class="page-item"><a class="page-link"
				href="?page=${item}&showNum=${showNum}">${item}</a></li>
		</c:forEach>
		<li class="page-item"><a class="page-link"
			href="?page=${(pageNavNumber+1)*5+1}" aria-label="Next"> <span
				aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>
</div>
<!-- 페이징 바 끝-->


<script>
	function checkAll() {
		if ($("#th_checkAll").is(":checked")) {
			$("input[name=checkRow]").prop("checked", true);
		} else {
			$("input[name=checkRow]").prop("checked", false);
		}
	}
</script>


<%@ include file="/WEB-INF/jspf/footer.jspf"%>
