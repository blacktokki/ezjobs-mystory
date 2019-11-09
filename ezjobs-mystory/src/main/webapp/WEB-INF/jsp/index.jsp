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
					<img src="#" class="d-block w-100" alt="...">
				</div>
				<div class="carousel-item" data-interval="10000">
					<img src="#" class="d-block w-100" alt="...">
				</div>
				<div class="carousel-item" data-interval="10000">
					<img src="#" class="d-block w-100" alt="...">
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

			<div class="input-group mb-3">
				<input type="text" class="form-control"
					placeholder="Recipient's username"
					aria-label="Recipient's username" aria-describedby="basic-addon2">
				<div class="input-group-append">
					<span class="input-group-btn" id="basic-addon2"><button
							type="button" class="btn btn-primary">검색</button></span>
				</div>
			</div>
			<!-- 검색창 파트입니다. -->
			<input class="btn btn-success" type="reset" value="Reset">
			<div class="btn-group" role="group"
				aria-label="Button group with nested dropdown">
				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button"
						class="btn btn-success dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">기간</button>
					<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
						<a class="dropdown-item" href="#">Dropdown link</a> <a
							class="dropdown-item" href="#">Dropdown link</a>
					</div>
				</div>

				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button"
						class="btn btn-success dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">언론사</button>
					<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
						<a class="dropdown-item" href="#">Dropdown link</a> <a
							class="dropdown-item" href="#">Dropdown link</a>
					</div>
				</div>

				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button"
						class="btn btn-success dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">통합분류</button>
					<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
						<a class="dropdown-item" href="#">Dropdown link</a> <a
							class="dropdown-item" href="#">Dropdown link</a>
					</div>
				</div>

				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button"
						class="btn btn-success dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">사건사고분류</button>
					<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
						<a class="dropdown-item" href="#">Dropdown link</a> <a
							class="dropdown-item" href="#">Dropdown link</a>
					</div>
				</div>



				<div class="btn-group" role="group">
					<button id="btnGroupDrop1" type="button"
						class="btn btn-success dropdown-toggle" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false">상세검색</button>
					<div class="dropdown-menu" aria-labelledby="btnGroupDrop1">
						<a class="dropdown-item" href="#">Dropdown link</a> <a
							class="dropdown-item" href="#">Dropdown link</a>
					</div>
				</div>
			</div>
			<!-- 분류창 부분입니다. -->

		</div>
	</div>
	<!-- 중간 위 -->
	<div class="jumbotron">
		<div class="container" style="background-color: white">
			<table class="table table-borderless table-sm">
				<thead>
					<tr>
						<td scope="col">직무별 인기공고</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="row">
								<div class="col-4">
									<div class="list-group" id="list-tab" role="tablist">
										<a class="list-group-item list-group-item-action active"
											id="list-home-list" data-toggle="list" href="#list-home"
											role="tab" aria-controls="home">Home</a> <a
											class="list-group-item list-group-item-action"
											id="list-profile-list" data-toggle="list"
											href="#list-profile" role="tab" aria-controls="profile">Profile</a>
										<a class="list-group-item list-group-item-action"
											id="list-messages-list" data-toggle="list"
											href="#list-messages" role="tab" aria-controls="messages">Messages</a>
										<a class="list-group-item list-group-item-action"
											id="list-settings-list" data-toggle="list"
											href="#list-settings" role="tab" aria-controls="settings">Settings</a>
									</div>
								</div>
								<div class="col-8">
									<div class="tab-content" id="nav-tabContent">
										<div class="tab-pane fade show active" id="list-home"
											role="tabpanel" aria-labelledby="list-home-list">...</div>
										<div class="tab-pane fade" id="list-profile" role="tabpanel"
											aria-labelledby="list-profile-list">...</div>
										<div class="tab-pane fade" id="list-messages" role="tabpanel"
											aria-labelledby="list-messages-list">...</div>
										<div class="tab-pane fade" id="list-settings" role="tabpanel"
											aria-labelledby="list-settings-list">...</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 중간 하 -->
	<div class="jumbotron">
		<div class="container">
<table class="table table-borderless text-center">
  <tbody>
    <tr>
      <td>작성 도구</td>
    </tr>
    <tr>
      <td>취업 자료</td>
    </tr>
    <tr>
      <td>정보 공유</td>
    </tr>
  </tbody>
</table>
		</div>
	</div>
	<!-- 맨 아래 -->

	<div class="jumbotron">
		<div class="container">기업 신청란</div>
	</div>
	<!-- 기업 신청란 -->
</div>
<!-- 전체 div -->

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
