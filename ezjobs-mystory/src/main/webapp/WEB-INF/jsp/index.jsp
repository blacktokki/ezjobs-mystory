<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<style>
#div_root{
margin : auto;
width:80%;
height: 100%;
}

#div_top{
width:100%;
height:30%;
background-color:#82FA58;
text-align: center;
}

#div_menu{
width:100%;
height:30%;
float:left;
background-color: #819FF7;
text-align:left;
}

#div_con{
width:100%;
height: 30%;
float:left;
background-color: #2DDA69;
text-align: center;
}

#div_bottom{
width:100%;
height: 100%;
clear.both;
background-color: #C8FF2E;
text-align: center;
}

#carouselExampleIndicators{
position:relative;
width:100%;
height: 100%;
}
#carousel_one{
position:relative;
width:100%;
height: 100%;
}
#first_img{
position:relative;
width:100%;
height: 100%;
}
.img_wrap{
	height:100%;
}
html, body{width:100%; height:100%;}

.nav_wrap{
width:100%;
height:45px;
}
.nav{width:100%; height:45px;position:fixed; top:0; z-index:2; background-color:yellow;}
// 화면 고정 z-index -> 화면의 높이를 z축을 도입해서 높낮이를 줄 수 있음. 숫자가 크면 위로 올라옴.

</style>

<!-- body -->
<div class="nav_wrap">
	<div class="nav"> 줄 고정 하는 것 연습용 창입니다 편하게 지울 수 있으니 걱정 ㄴㄴ
	</div>
</div><!-- 화면 휠이 내려가도 고정 시키는것. -->
<!-- 깃 허브 재 업로드 용 소스 -->
<div id="div_root">
<div id ="div_top">
<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div id="carousel_one" class="carousel-inner">
    <div class="carousel-item active img_wrap">
      <img src="..." id="first_img" class="d-block w-100" alt="마땅한 사진 없음 1">
    </div>
    <div class="carousel-item img_wrap">
      <img src="..." class="d-block w-100" alt="마땅한 사진 없음 2">
    </div>
    <div class="carousel-item img_wrap">
      <img src="..." class="d-block w-100" alt="마땅한 사진 없음 3">
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
</div>
<div id="div_menu">
<p>
  <a class="btn btn-primary" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false" aria-controls="multiCollapseExample1">1번 버튼</a>
  <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#multiCollapseExample2" aria-expanded="false" aria-controls="multiCollapseExample2">2번 버튼</button>
  <button class="btn btn-primary" type="button" data-toggle="collapse" data-target=".multi-collapse" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample2">동시 버튼</button>
</p>
<div class="row">
  <div class="col">
    <div class="collapse multi-collapse" id="multiCollapseExample1">
      <div class="card card-body">
		1번 입니다.
      </div>
    </div>
  </div>
  <div class="col">
    <div class="collapse multi-collapse" id="multiCollapseExample2">
      <div class="card card-body">
        2번 입니다.
      </div>
    </div>
  </div>
</div>
</div>
<div id="div_con">내용</div>
<div id="div_bottom">마무리</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>	
