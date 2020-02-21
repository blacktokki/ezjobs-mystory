<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/extendHead.jspf"%>

<!-- Ctrl+F #워드클라우드 #차트 #트리맵 #색상 #트리맵 도메인 #JSON연결 #데이터출력수 #특수문자주소 -->
<!-- body -->

<div class="dropdown" style="left: 85%; margin: 20px 2000px 0px 0px;">
  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    시각화 메뉴
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <button class="dropdown-item" type="button" onclick="showCloud()">워드클라우드</button>
    <button class="dropdown-item" type="button" onclick="showChart1()">차트1</button>
    <button class="dropdown-item" type="button" onclick="showChart2()">차트2</button>
    <button class="dropdown-item" type="button" onclick="showTreeMap()">트리맵</button>
  </div>
</div>

<div class="alert alert-primary" role="alert"
	style="left: 50%; text-align: center; font-weight: bold; font-size:24px; color: #6e6e6e; margin: 30px 1000px 45px -400px; width: 800px;">
	자기소개서 분석 / 시각화 </div>

<div id="wordcloud" align="center" style="height:600px; zoom:1.6;" >
#워드클라우드 : 워드클라우드 단어의 크기는 자기소개서에서 사용한 단어의 빈도에 비례합니다.<br><br>
<p style="font-size:6.5px;"> *단어 클릭시 문장검색으로 이동하여 검색합니다. </p>
</div>
<!-- 워드 클라우드 -->
<div class="chart-container" id="jsonChart1" align="center" 
	style="height: 100vh; width: 80vw; margin: 0px 0px 0px 160px;">
	#차트 : 자기소개서의 태그 관한 데이터입니다. 차트에 사용된 데이터는 직무 유형으로 분류되어진 태그입니다. 직무 유형 태그의 빈도에 따라 높은 순으로 보여줍니다.<br><br>
	<p style="font-size:10px;"> *차트의 막대(bar)를 클릭 시 문장검색으로 이동하여 검색합니다. </p>
	<canvas id="myChart1"></canvas>
</div>


<div class="chart-container" id="jsonChart2" align="center" 
	style="height: 100vh; width: 80vw; margin: 0px 0px 0px 160px;">
	#차트 : 자기소개서의 문장에 관한 데이터입니다. 차트에 사용된 데이터는 문장의 시작 단어으로 분류되어진 태그입니다. 시작단어의 빈도에 따라 높은 순으로 보여줍니다.<br><br>
	<p style="font-size:10px;"> *차트의 막대(bar)를 클릭 시 문장검색으로 이동하여 검색합니다. </p>
	<canvas id="myChart2"></canvas>
</div>

<div id="vizTreeMap" style="margin: 0px 0px 100px 0px;" align="center">
#트리맵 : 자기소개서의 태그에 관한 데이터입니다. 차트에 사용된 데이터는 문항 유형으로 분류되어진 태그입니다. 공간의 크기는 문항 유형 태그의 빈도에 따라 비례합니다.<br><br>
<p style="font-size:10px;"> *단어 혹은 공간 클릭시 문장검색으로 이동하여 검색합니다. </p>
</div>

<script src="/js/dashboard.js"></script>
<%@ include file="/WEB-INF/jspf/extendFooter.jspf"%>