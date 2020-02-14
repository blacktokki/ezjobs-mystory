<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<div class="container">
	<div class="jumbotron"
		style="background-image: url(/image/admin_tag_banner.png);">
		<h1 class="display-6">태그 관리</h1>
		<p class="lead">태그를 관리 해봅시다</p>
	</div>

	<h2>모두의 태그들</h2>

	<!-- 겁색창 -->
	<div class="card card-title" style="padding: 10px">
		<div class="clearfix">
			<div class="float-left">
				<form method="post" action="#">
					<div class="input-group">
						<input name="keyword" type="text" class="form-control"
							placeholder="검색 할 태그 명" aria-label="Recipient's username"
							aria-describedby="button-addon2">
						<div class="input-group-append">
							<button class="btn btn-outline-secondary" type="submit"
								id="button-addon2">검색</button>
						</div>
					</div>
					<input type="hidden" name="size" value="${size }">
					<input type="hidden" name="page" value="${ page + 1}">
				</form>
			</div>

			<div>
				<button type="button" class="btn btn-outline-secondary">선택
					삭제</button>
			</div>

			<%@ include file="/WEB-INF/jspf/pageSize.jspf"%>
		</div>


	</div>
	<!-- 검색창 끝 -->

	<div class="row">

		<c:forEach var="i" begin="0" end="1">
			<div class="col-6">
				<div class="pagination justify-content-center">
					<table class="table table-sm table-hover">
						<colgroup>
							<col width="20">
							<col width="80">
							<col width="*">
							<col width="45">
							<col width="45">
						</colgroup>

						<!-- 테이블 제목 -->
						<thead class="thead-light text-center">
							<tr>
								<th scope="col">
									<c:set var="name" value="${ i }" />
									<c:if test="${name eq 0}">
										<input type="checkbox" name="checkAll" id="th_checkAll"
											onclick='checkAll();' />
									</c:if>
								</th>
								<th scope="col">유형</th>
								<th scope="col">태그명</th>
								<th scope="col">수정</th>
								<th scope="col">삭제</th>
							</tr>
						</thead>

						<!-- 테이블 내용 채우기 -->
						<tbody class="text-center">
							<c:forEach var="item" items="${tags.content}"
								begin="${size/2*i}" end="${(size/2*(i+1)) -1}">
								<c:set var="id" value="${ item.type };${item.name}" />

								<!-- 각 행들 -->
								<tr>
									<td><input type="checkbox" name="checkRow"
										value="${id}" /></td>
									<td>${item.type}</td>
									<td align="left">${item.name}</td>
									<td><button type="button" data-toggle="modal"
											data-target="#upDate00" data-whatever="@mdo"
											style="border: 0; background: 0;">
											<i class="fa fa-pencil"></i>
										</button></td>

									<td><button type="button" data-toggle="modal"
											data-target="#delete00" data-whatever="@mdo"
											style="border: 0; background: 0;">
											<i class="fa fa-times-circle" style="color: #FF8585"></i>
										</button></td>
								</tr>
								<!-- 각 행들 끝 -->

								<!-- 수정 모달 -->
								<div class="modal fade" id="upDate00" tabindex="-1"
									role="dialog" aria-labelledby="exampleModalLabel"
									aria-hidden="true">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title" id="exampleModalLabel">태그 수정하기</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<form method="post" action="#">
												<div class="modal-body">
													<div class="card-body">
														<h5 class="card-title">원래 태그 이름</h5>
														<p class="card-text">${item.name}</p>
													</div>
													<div class="card-body">
														<h5 class="card-title">원하는 태그 이름</h5>
														<div class="form-group">
															<textarea class="form-control" id="message-text"
																name="upTag"></textarea>
														</div>
													</div>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-dismiss="modal">취소</button>
													<button type="submit" class="btn btn-primary">수정하기</button>
												</div>
												<input type="hidden" name="size" value="${size}">
												<input type="hidden" name="page" value="${page}">
												<input type="hidden" name="upTagId" value="${id}">
											</form>
										</div>
									</div>
								</div>
								<!-- 수정 모달 끝-->


								<!-- 삭제 모달 -->
								<div class="modal" id="delete00" tabindex="-1"
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
													<p>정말로 "00. ${item.name }" 태그를 삭제 하시겠습니까?</p>
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-dismiss="modal">취소</button>
													<button type="submit" class="btn btn-danger">삭제</button>
													<input type="hidden" name="size" value="${size}">
													<input type="hidden" name="page" value="${page}">
													<input type="hidden" name="delTagId" value="${id}">
												</div>
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
		</c:forEach>

	</div>

	<%@ include file="/WEB-INF/jspf/pageNavbar.jspf"%>
</div>

<script src="/js/admin.js"></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
