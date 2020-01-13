<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>



<div class="container">
	<div class="jumbotron"
		style="background-image: url(/image/admin_user_banner.png);">
		<h1 class="display-6">회원관리</h1>
		<p class="lead">회원들을 관리해주세요</p>
	</div>

	<h2>우리의 회원들</h2>

	<!-- 겁색창 -->
	<div class="card card-title" style="padding: 10px">
		<div class="row">
			<div class="col-6">
				<form action="/admin/user">
					<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="inGroup01">검색 옵션</label>
						</div>
						<select class="custom-select" id="inGroup01" name="op">
							<option value="loginSearch" <c:if test="${op eq 'loginSearch'}">selected</c:if>>유저아이디</option>
						</select> <input name="keyword" type="text" class="form-control"
							placeholder="검색어" aria-label="Recipient's username" value=" ${keyword}"
							aria-describedby="button-addon2">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"
								id="button-addon2">검색</button>
						</div>
					</div>
					<input type="hidden" name="size" value="${size}">
					<input type="hidden" name="page" value="${ pageNavNumber + 1}">
				</form>
			</div>
			<div class="col offset-3">
				<div class="input-group">
						<div class="input-group-prepend">
							<label class="input-group-text" for="inGroup02">페이지 당</label>
						</div>
					<form method="get">
						<input type="hidden" name="page" value="${ pageNavNumber + 1}">
						<select class="custom-select" id="inGroup02" name="size"
							onchange="this.form.submit()">
							<option selected>현재 ${size} 명</option>
							<option value=10>10 명 보기</option>
							<option value=20>20 명 보기</option>
							<option value=30>30 명 보기</option>
							<option value=50>50 명 보기</option>
							<option value=100>100 명 보기</option>
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
				<col width="30">
				<col width="30">
				<col width="120">
				<col width="*">
				<col width="50">
				<col width="200">
			</colgroup>

			<!-- 테이블 제목 -->
			<thead class="thead-light">
				<tr>
					<th scope="col"><input type="checkbox" name="checkAll"
						id="th_checkAll" onclick='checkAll();' /></th>
					<th scope="col"></th>
					<th scope="col">이름</th>
					<th scope="col">유저 아이디</th>
					<th scope="col"><i class="fa fa-search"></i></th>
					<th scope="col">가입일</th>
				</tr>
			</thead>

			<!-- 테이블 내용 채우기 -->
			<tbody>
				<c:forEach var="item" items="${users.content}">
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
						<td>${item.name}</td>

						<!-- 드롭다운 부분 -->
						<td>
							<div class="dropdown">
								<button class="btn dropdown-toggle" style="padding: 0"
									type="button" id="dropdownMenu${item.id}"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">${item.id}</button>
								<div class="dropdown-menu"
									aria-labelledby="dropdownMenu${item.id}">
									<a class="dropdown-item" href="#">${item.name}님의 자기소개서</a> <a
										class="dropdown-item" href="#">${item.name}님의 게시글</a> <a
										class="dropdown-item" href="#">${item.name}님 이용 정지</a>
								</div>
							</div>

						</td>
						<td><a href="/admin/personal/${item.id}">
								<button class="btn" style="padding: 0" type="button">
									<i class="fa fa-search"></i>
								</button>
						</a></td>
						<td>${item.registDate}</td>
					</tr>


					<!-- 정보 출력 -->
					<tr>
						<td colspan="7" style="padding: 0">
							<div class="collapse" id="collapse${item.id}">
								<div class="card text-center">

									<div class="row" style="color: #AAAAAA; font-weight: 600;">
										<div class="col">이메일</div>
										<div class="col">성별</div>
										<div class="col">학력</div>
										<div class="col">방문일</div>
										<div class="col">연동서비스</div>
									</div>

									<div class="row">
										<div class="col">${item.email}</div>
										<div class="col">${item.sex}</div>
										<div class="col">${item.grad}</div>
										<div class="col">${item.visitDate}</div>
										<div class="col">${item.loginRel}</div>
									</div>
								</div>
							</div>
						</td>

					</tr>

				</c:forEach>
			</tbody>
		</table>
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
					href="?page=${item}&size=${size}">${item}</a></li>
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
