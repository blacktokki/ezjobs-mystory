<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="nav nav-tabs " id="nav-tab" role="tablist">
	<a class="nav-item nav-link p-0 border-secondary mx-auto bg-secondary active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab"
		aria-controls="nav-home" aria-selected="true">작성 목록</a><!-- 작성중,작성중:내용없음 -->
	<a class="nav-item nav-link p-0 border-secondary mx-auto bg-secondary" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" 
		aria-controls="nav-profile" aria-selected="false">작성중</a> 
	<a class="nav-item nav-link p-0 border-secondary mx-auto bg-secondary" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab"
		aria-controls="nav-contact" aria-selected="false">작성 완료</a><!--작성완료:미제출,작성완료:제출됨 작성완료:서류합격 --> 
</div>
<nav class="navbar navbar-expand-sm navbar-dark bg-secondary px-0 pt-0">
	<a class="navbar-toggler navbar-toggler-right text-uppercase font-weight-bold text-white rounded w-100" type="button" data-toggle="collapse" data-target="#navbarsExample04" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation" >
	        확장
	  	<i class="fas fa-bars"></i>
	</a>
  	<div class="collapse navbar-collapse flex-column " id="navbarsExample04">
	     <!-- 
	     <a class="navbar-brand d-none d-sm-block " href="#">
	  		<font size=5> 목록</font>
	  	</a>-->
	     <ul class="navbar-nav mr-auto flex-column  px-2" style="margin: 0px 0px 50vh;"><!-- 임시여백 -->
	      <li class="nav-item">
	      	<input type="text" class="form-control" id="exampleInput" placeholder="Enter text">
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">자기소개서 1</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">자기소개서 2</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#">자기소개서 3</a>
	      </li>
	    </ul>
  	</div>
</nav>