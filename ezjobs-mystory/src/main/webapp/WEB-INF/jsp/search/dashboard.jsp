<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<!-- Ctrl+F #워드클라우드 #차트 #트리맵 #색상 #트리맵 도메인 #JSON연결 #데이터출력수 #특수문자주소 -->
<!-- body -->

<script src="https://d3js.org/d3.v4.js"></script>
<script src="https://rawgit.com/jasondavies/d3-cloud/master/build/d3.layout.cloud.js"></script>

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

<script>
document.getElementById("jsonChart1").style.display = "none"; // 기본적으로 워드클라우드만 보여주기 위한 처리
document.getElementById("jsonChart2").style.display = "none";
document.getElementById("vizTreeMap").style.display = "none";

function showCloud(){ // 특정한 시각화 데이터 제공을 위한 버튼에 사용되는 함수 
	document.getElementById("wordcloud").style.display = "block";
	document.getElementById("jsonChart1").style.display = "none";
	document.getElementById("jsonChart2").style.display = "none";
	document.getElementById("vizTreeMap").style.display = "none";
}

function showChart1(){
	document.getElementById("wordcloud").style.display = "none";
	document.getElementById("jsonChart1").style.display = "block";
	document.getElementById("jsonChart2").style.display = "none";
	document.getElementById("vizTreeMap").style.display = "none";
}

function showChart2(){
	document.getElementById("wordcloud").style.display = "none";
	document.getElementById("jsonChart1").style.display = "none";
	document.getElementById("jsonChart2").style.display = "block";
	document.getElementById("vizTreeMap").style.display = "none";
}

function showTreeMap(){
	document.getElementById("wordcloud").style.display = "none";
	document.getElementById("jsonChart1").style.display = "none";
	document.getElementById("jsonChart2").style.display = "none";
	document.getElementById("vizTreeMap").style.display = "block";
}


	var randomColor = new Array(3); // 트리맵 색상 일치 용도, 워드 클라우드의 랜덤색상을 하나씩 따감
	
	var frequency_list = $.ajax({
		type : 'get',
		url : '/search/wordCloudJson', //#JSON연결
		datatype : 'json',
		async : false,
		success : function() {
			//alert("성공")
		},
		error : function() {
			alert("스크립트 ajax 에러")
		}
	}).responseText;

	var x = JSON.parse(frequency_list); // x에 배열로써 JSON 파싱
	
 	var chartJsonList1 = $.ajax({
		type : 'get',
		url : '/search/chartJson', //#JSON연결
		datatype : 'json',
		async : false,
		success : function() {
			//alert("차트성공")
		},
		error : function() {
			alert("스크립트 ajax 에러")
		}
	}).responseText;
 	var chartJsonList2 = $.ajax({
		type : 'get',
		url : '/search/prefixChartJson', //#JSON연결
		datatype : 'json',
		async : false,
		success : function() {
			//alert("차트성공")
		},
		error : function() {
			alert("스크립트 ajax 에러")
		}
	}).responseText;

	var y1 = JSON.parse(chartJsonList1);
	var y2 = JSON.parse(chartJsonList2);
	
	
	var seed = parseInt(Math.random() * 3);
	var r1 = parseInt(Math.random() * 256); // 색상을 랜덤 부여하기 위한 변수 1,2,3
	var r2 = parseInt(Math.random() * 256);
	var r3 = parseInt(Math.random() * 256);
	var r1mem = 257;
	var fontSizePrint = 0;
	
	for(var i=0;i<3;i++){
		r1 = parseInt(Math.random() * 255);
		r2 = parseInt(Math.random() * 255);
		r3 = ((seed+i)*127%381)%256;
		if(r3>250){
			r1 = 2*r1/3;
			r2 = 2*r2/3;
		}
			randomColor[i] = "rgba("+r1+", "+r2+", "+r3+", 0.7)"; //#색상(여기서 쓰는 색상이 아니라, 트리맵에서 사용될 색상)
	}
	
	// #워드클라우드
	$('#wordcloud').jQCloud(x, {
		  classPattern: null,
		  colors: ["#600000","#800000", "#880000", "#881700","#901a00", "#904a00","#a04a00", "#a06a00"],
		  fontSize: {
		    from: 0.07,
		    to: 0.01
		  },
		  autoResize:true,
		});
	
	// #차트
	function swap(_arr, _firstIndex, _secondIndex) // 이하의 세개의 함수는 차트 높은순 정렬 용도
	{
		var temp = _arr[0][_firstIndex];
		var temp2 = _arr[1][_firstIndex];
		_arr[0][_firstIndex] = _arr[0][_secondIndex];
		_arr[1][_firstIndex] = _arr[1][_secondIndex];
		_arr[0][_secondIndex] = temp;
		_arr[1][_secondIndex] = temp2;
	}

	function partition(_arr, _left, _right) {

		var pivot = _arr[0][Math.floor((_right + _left) / 2)], left = _left, right = _right;

		while (left <= right) {

			while (_arr[0][left] < pivot) {
				left++;
			}

			while (_arr[0][right] > pivot) {
				right--;
			}

			if (left <= right) {
				swap(_arr, left, right);
				left++;
				right--;
			}
		}
		return left;
	}

	function arrSort(_arr, _left, _right) {
		var index;

		if (_arr[0].length > 1) {

			_left = typeof _left != 'number' ? 0 : _left;
			_right = typeof _right != 'number' ? _arr[0].length - 1 : _right;

			index = partition(_arr, _left, _right);

			if (_left < index - 1) {
				arrSort(_arr, _left, index - 1);
			}

			if (index < _right) {
				arrSort(_arr, index, _right);
			}
		}
		return _arr;
	}
	
	String.prototype.replaceAll = function(org, dest) {
	    return this.split(org).join(dest);
	}

	var sizeTextList = new Array(2);
	sizeTextList[0] = new Array();
	sizeTextList[1] = new Array();
	
	for (var i in y1) {
		sizeTextList[0].push(y1[i].doc_count);
		sizeTextList[1].push(y1[i].text);
	}
	arrSort(sizeTextList);
	sizeTextList[0].reverse();
	sizeTextList[1].reverse();
	
	sizeTextList[0] = sizeTextList[0].slice(0,40); // #데이터출력수
	sizeTextList[1] = sizeTextList[1].slice(0,40);
	
	var chartColors = new Array();
	var chartBold = new Array();
	r1 = parseInt(Math.random() * 256); // 색상을 랜덤 부여하기 위한 변수 1,2,3
	r2 = parseInt(Math.random() * 256);
	r3 = parseInt(Math.random() * 256);
	r1mem = 257;
	var i = 0;
	
	while (i < sizeTextList[0].length) { // 랜덤 색상 설정
		while (r1 + r2 + r3 > 550 || r1mem == r1) { 
			r1 = parseInt(Math.random() * 255);
			r2 = parseInt(Math.random() * 255);
			r3 = parseInt(Math.random() * 255);
		}
		r1mem = r1;
		
		chartColors[i] = "rgba(" + r1 + ", " + r2 + ", " + r3 + ", 0.2)"; //#색상
		chartBold[i] = "rgba(0, 0, 0, 0.4)";
		
		i++;
	}
	
	var chartinit1={
			type : 'horizontalBar',
			data : {
				labels : sizeTextList[1],
				datasets : [ {
					label : '# 직무 유형 태그 빈도',
					data : sizeTextList[0],
					backgroundColor : chartColors,
					borderColor : chartBold,
					borderWidth : 1
				} ]
			},
			options : {
				 onClick: function(evt, active) { // 클릭시 링크
				      var Id = active[0]._index;
				      var charConvert = sizeTextList[1][Id]; 
				   // #특수문자주소 : 특수문자 주소를 변환하는 곳
				      charConvert = charConvert.replaceAll("&", "%26");
				      charConvert = charConvert.replaceAll("(", "%28");
				      charConvert = charConvert.replaceAll(")", "%29");
				      location.href ="/search/list?searchTags="+charConvert;
				    },
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}
		}
	sizeTextList = new Array(2);
	sizeTextList[0] = new Array();
	sizeTextList[1] = new Array();
	
	for (var i in y2) {
		sizeTextList[0].push(y2[i].doc_count);
		sizeTextList[1].push(y2[i].text);
	}
	arrSort(sizeTextList);
	sizeTextList[0].reverse();
	sizeTextList[1].reverse();
	
	sizeTextList[0] = sizeTextList[0].slice(0,40); // #데이터출력수
	sizeTextList[1] = sizeTextList[1].slice(0,40);
	
	var chartColors = new Array();
	var chartBold = new Array();
	r1 = parseInt(Math.random() * 256); // 색상을 랜덤 부여하기 위한 변수 1,2,3
	r2 = parseInt(Math.random() * 256);
	r3 = parseInt(Math.random() * 256);
	r1mem = 257;
	var i = 0;
	
	while (i < sizeTextList[0].length) { // 랜덤 색상 설정
		while (r1 + r2 + r3 > 550 || r1mem == r1) { 
			r1 = parseInt(Math.random() * 255);
			r2 = parseInt(Math.random() * 255);
			r3 = parseInt(Math.random() * 255);
		}
		r1mem = r1;
		
		chartColors[i] = "rgba(" + r1 + ", " + r2 + ", " + r3 + ", 0.2)"; //#색상
		chartBold[i] = "rgba(0, 0, 0, 0.4)";
		
		i++;
	}
	
	var chartinit2={
			type : 'horizontalBar',
			data : {
				labels : sizeTextList[1],
				datasets : [ {
					label : '# 시작 단어 태그 빈도',
					data : sizeTextList[0],
					backgroundColor : chartColors,
					borderColor : chartBold,
					borderWidth : 1
				} ]
			},
			options : {
				 onClick: function(evt, active) { // 클릭시 링크
				      var Id = active[0]._index;
				      var charConvert = sizeTextList[1][Id]; 
				   // #특수문자주소 : 특수문자 주소를 변환하는 곳
				      charConvert = charConvert.replaceAll("&", "%26");
				      charConvert = charConvert.replaceAll("(", "%28");
				      charConvert = charConvert.replaceAll(")", "%29");
				      location.href ="/search/list?searchTags="+charConvert;
				    },
				scales : {
					yAxes : [ {
						ticks : {
							beginAtZero : true
						}
					} ]
				}
			}
		}
	
	var chart1 = document.getElementById('myChart1').getContext('2d');
	var myChart1 = new Chart(chart1,chartinit1);
	var chart2 = document.getElementById('myChart2').getContext('2d');
	var myChart2 = new Chart(chart2,chartinit2);
	
	// #트리맵
var margin = {top: 10, right: 10, bottom: 10, left: 10},
  width = 1300 - margin.left - margin.right,
  height = 700 - margin.top - margin.bottom;


var svg = d3.select("#vizTreeMap")
.append("svg")
  .attr("width", width + margin.left + margin.right)
  .attr("height", height + margin.top + margin.bottom)
.append("g")
  .attr("transform",
        "translate(" + margin.left + "," + margin.top + ")");


var domainValue;
var avg = 0;
var j = 0;
d3.json("/search/treeMapJson", function(data) { //#JSON연결

	  for(j=0;j<data.children.length;j++){
		avg = avg + data.children[j].size;  
	  }
	  avg = avg / data.children.length;
	  var root = d3.hierarchy(data).sum(function(d){
		  domainValue = (d.size + avg/5); // #트리맵 도메인
		  return domainValue;
		  });
	  

  d3.treemap()
    .size([width, height])
    .padding(2)
    (root)
    
  
  svg
    .selectAll("rect")
    .data(root.leaves())
    .enter()
    .append("a").attr("xlink:href", function(d){
					return "/search/list?searchTags="+d.data.text;
				})
    .append("rect")
      .attr('x', function (d) { return d.x0; })
      .attr('y', function (d) { return d.y0; })
      .attr('width', function (d) { return d.x1 - d.x0; })
      .attr('height', function (d) { return d.y1 - d.y0; })
      .style("stroke", "black")
      .style("fill", function (d) {
    	  return randomColor[d.data.type]; // #색상
    	  })
  
    svg.selectAll('text')
      .data(root.leaves())
      .enter()
      .append('text')
      .selectAll('tspan')
      .data(d => {
          return d.data.text.split(/(?=[\s]+)/g) // split the name of movie
              .map(v => { 
                  return {
                      text: v,
                      x0: d.x0,
                      y0: d.y0
                  }
              });
      })
      .enter()
      .append("a").attr("xlink:href", function(d){
					return "/search/list?searchTags="+d.text;
				})
      .append('tspan')
      .attr("x", (d) => d.x0 + 5)
      .attr("y", (d, i) => d.y0 + 15 + (i * 17)) 
      .text((d) =>  d.text.replace(/(\s*)/g, ""))
      .attr("font-size", "0.8em")
      .attr("fill", "white") // #색상
})

</script>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>