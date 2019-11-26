<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<div>
	<div class="jumbotron text-center">
		<div id="carouselExampleInterval" class="carousel slide"
			data-ride="carousel">
			<div class="carousel-inner">
				<div class="carousel-item active" data-interval="10000">
					<img src="/image/business1.jpg" height="300px"
						class="d-block w-100" alt="첫번째 이미지">
				</div>
				<div class="carousel-item" data-interval="10000">
					<img src="/image/business2.jpg" height="300px"
						class="d-block w-100" alt="두번째 이미지">
				</div>
				<div class="carousel-item" data-interval="10000">
					<img src="/image/business3.jpg" height="300px"
						class="d-block w-100" alt="세번째 이미지">
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleInterval"
				role="button" data-slide="prev"> <span
				class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="carousel-control-next" href="#carouselExampleInterval"
				role="button" data-slide="next"> <span
				class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>

	</div>
	<!-- 제일 위 -->

	<div class="jumbotron">
		<div class="container">
			<form method="post" action="#">
				<div align="right">
					<button class="btn btn-primary" type="submit">검색하기</button>
				</div>
				<p>
					<!-- 태그 처리해야하는 부분을 넣어야합니다. -->
				<div class="form-row">
					<div class="col-6">
						<input type="text" class="form-control" placeholder="태그 검색">
					</div>
					<div class="col-6">
						<input type="text" class="form-control" placeholder="일반 검색">
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 중간 위 -->
	<div class="jumbotron">
		<div class="container" style="background-color: white">
			<div>
				<table class="table">
					<!-- 테이블 제목 -->
					<thead>
						<tr>
							<th colspan="2">인기게시글</th>
						</tr>
					</thead>
					<!-- 티에블 내용 채우기 -->
					<tbody>

						<c:forEach var="item" items="${boardscommunity.content}">
							<tr>
								<td><a href="/board/content/${item.id}">${item.title}&nbsp;</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
			<lable><strong>공지사항</strong></lable>
				<div id="carouselExampleInterval" class="carousel slide"
					data-ride="carousel">
					<div class="carousel-inner">
						<c:forEach var="item" items="${boards.content}" varStatus="status">
							<c:choose>
								<c:when test="${status.count == 1 }">
									<div class="carousel-item active" data-interval="3000">
										<a href="/help/noticecontent/${item.id}">${item.title}&nbsp;</a>
									</div>
								</c:when>
								<c:otherwise>
									<div class="carousel-item" data-interval="3000">
										<a href="/help/noticecontent/${item.id}">${item.title}&nbsp;</a>
									</div>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</div>

			</div>

		</div>
	</div>
	<!-- 중간 하 -->
	<div class="jumbotron">
		<div class="container">
			<table class="table">
				<tbody>
					<tr>
						<td class="table-light">
						<div>
						<div style="float:left">
						<h3><strong>방대한 자료</strong></h3>
						<p><h5>합격한 자소서 수집</h5></p>
						<p>자료를 문항 단위별 분리<br> 선정된 키워드를 기준으로 독특한 소개서 분리</p>
						</div>
						<div style="float:right">
						<img src="/image/content1.jpg" width="200px" height="200px" alt="자료를 불러오는 중입니다">
						</div>
						</div>
						</td>
					</tr>
					<tr>
						<td class="table-light">
						<div>
						<div style="float:left">
						<h3><strong>작성 도구</strong></h3>
						<p><h5>자소서 작성 시 편리</h5></p>
						<p>어두를 적으면 어두로 시작하는 문장, 문단 목록 추천<br>
						 자소서의 문장,문단 위치를 마우스만으로도 손쉽게 이동 가능<br>
						 이미 완성된 자소서들과의 유사도 검사 가능</p>
						</div>
						<div style="float:right">
						<img src="/image/content2.jpg" width="200px" height="200px" alt="자료를 불러오는 중입니다">
						</div>
						</div>
						</td>
					</tr>
					<tr>
						<td class="table-light">
						<div>
						<div style="float:left">
						<h3><strong>커뮤니티 구축</strong></h3>
						<p><h5>정보 공유를 위한 게시판</h5></p>
						<p>회원과 비회원 모두 사용 가능한
						<br>게시판을 통한 회사, 자소서 등의 여러가지 정보를 공유</p>
						</div>
						<div style="float:right">
						<img src="/image/content3.jpg" width="200px" height="200px" alt="자료를 불러오는 중입니다">
						</div>
						</div>
						</td>
					</tr>
					</tbody>
			</table>
		</div>
	</div>
	<!-- 맨 아래 -->

</div>
<!-- 전체 div -->

<%@ include file="/WEB-INF/jspf/footer.jspf"%>