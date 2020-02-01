<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>



<div class="container">
	<div class="jumbotron"
		style="background-image: url(/image/admin_user_banner.png);">
		<h1 class="display-6">게시판관리(아카이브)</h1>
		<p class="lead">게시글들을 관리해주세요</p>
	</div>

	<h2>우리의 게시글들</h2>

	<!-- 겁색창 -->
	<div class="card card-title" style="padding: 10px">
		<div class="row">
			<div class="col-6">
				<form action="/admin/board">
					<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="inGroup01">검색 옵션</label>
						</div>
						<select class="custom-select" id="inGroup01" name="op">
							<option value="userId" <c:if test="${op eq 'userId'}">selected</c:if>>유저아이디</option>
							<option value="title" <c:if test="${op eq 'title'}">selected</c:if>>제목</option>
						</select> <input name="keyword" type="text" class="form-control"
							placeholder="검색어" aria-label="Recipient's username" <c:if test="${keyword ne 'null'}">value="${keyword}"</c:if>
							aria-describedby="button-addon2">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"
								id="button-addon2">검색</button>
						</div>
					</div>
					<input type="hidden" name="size" value="${size}">
				</form>
			</div>
			<%@ include file="/WEB-INF/jspf/pageSize.jspf"%>
		</div>
	</div>


	<!-- 검색창 끝 -->

	<div class="pagination justify-content-center">
		<table class="table table-sm table-hover">
			<colgroup>
				<col width="30">
				<col width="30">
				<col width="120">
				<col width="120">
				<col width="*">
				<col width="120">
				<col width="200">
			</colgroup>

			<!-- 테이블 제목 -->
			<thead class="thead-light">
				<tr>
					<th scope="col"><input type="checkbox" name="checkAll"
						id="th_checkAll" onclick='checkAll();' /></th>
					<th scope="col"></th>
					<th scope="col">게시글번호</th>
					<th scope="col">분류</th>
					<th scope="col">제목</th>
					<th scope="col">유저 아이디</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>

			<!-- 테이블 내용 채우기 -->
			<tbody>
				<c:forEach var="item" items="${boards.content}">
					<tr>
						<td><input type="checkbox" name="checkRow" value="${item.id}" /></td>

						<!-- collapse 부분 -->
						<td>
							<button class="btn" style="padding: 0" type="button"
								data-toggle="collapse" data-target="#collapse${item.id}"
								aria-expanded="false" aria-controls="collapseExample">
								<i class="fa fa-angle-down"></i>
							</button>
						</td>
						<td scope="row">${item.id}</td>
						<td>${item.boardType}</td>
						<td>${item.title}</td>
						<td>${item.userId}</td>
						<td>${item.editDate}</td>
					</tr>


					<!-- 정보 출력 -->
					<tr>
						<td colspan="7" style="padding: 0">
							<div class="collapse" id="collapse${item.id}">
								<div class="card text-center">

									<div class="row" style="color: #AAAAAA; font-weight: 600;">
										<div class="col">추천수</div>
										<div class="col">비추천수</div>
										<div class="col">삭제일</div>
									</div>

									<div class="row">
										<div class="col">${item.goodCnt}</div>
										<div class="col">${item.badCnt}</div>
										<div class="col">${item.removeDate}</div>
									</div>
									<div class="text-left">
										${item.text}
									</div>
								</div>
							</div>
						</td>

					</tr>

				</c:forEach>
			</tbody>
		</table>
	</div>
	<%@ include file="/WEB-INF/jspf/pageNavbar.jspf"%>	
</div>


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
