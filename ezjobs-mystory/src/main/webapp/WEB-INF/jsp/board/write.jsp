<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<!-- 점보트론 스타일 -->
<style>
.jumbotron {
	background-image: url(/image/jumbotronBG.jpg);
	background-size: cover;
	background-position: 0% 40%;
	text-shadow: black -0.4em 0.4em 0.4em;
	color: white;
}
</style>
<div class="container">
	<div class="jumbotron">
		<h1 class="display-4">글쓰기</h1>
		<p class="lead">회원님들의 이야기를 자유롭게 적어주세요.</p>
		<hr class="my-4">
		<p>글쓰는중 ㅎㅎ</p>
	</div>
</div>
<p>
<div class="container">
	<h2>끄적끄적</h2>
	<p>
	<div class="card">
		<form method="post" action="/board/write">
			<div class="card-body">
			
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="basic-addon1">글제목</span>
							</div>
							<input type="text" class="form-control" name="title">
						</div>
				
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="basic-addon2">작성자</span>
							</div>
							<input type="text" class="form-control" name="userId">
						</div>
					
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">글내용</span>
							</div>
							<textarea class="form-control" name="text" rows="15"></textarea>
						</div>
						
				<button type="submit" class="btn btn-primary resume-submit">글올리기</button>
			</div>

		</form>
	</div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>