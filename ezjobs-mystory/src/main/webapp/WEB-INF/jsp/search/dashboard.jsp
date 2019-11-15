<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<!-- body -->
<script src="https://d3js.org/d3.v3.min.js"></script>
<script
	src="https://rawgit.com/jasondavies/d3-cloud/master/build/d3.layout.cloud.js"
	type="text/JavaScript"></script>
<script src="https://d3js.org/d3.v4.js"></script>

<div class="alert alert-primary" role="alert"
	style="left: 50%; text-align: center; font-weight: bold; color: #6e6e6e; margin: 30px 0px 40px -400px; width: 800px;">
	자기소개서 분석 / 시각화 워드클라우드</div>

<div id="wordcloud" align="center"></div>
<!-- 워드 클라우드 -->

<div class="chart-container" style="height: 100vh; width: 80vw; margin: 0px 0px 0px 160px">
	<canvas id="myChart"></canvas>
</div>

<div id="my_dataviz"></div>
<script>
	var frequency_list = $.ajax({
		type : 'get',
		url : '/search/word',
		datatype : 'json',
		async : false,
		success : function() {
			// alert("성공")
		},
		error : function() {
			alert("스크립트 ajax 에러")
		}
	}).responseText;

	var x = JSON.parse(frequency_list); // x에 배열로써 JSON 파싱
	/* var color = d3.scale.linear().domain(
	 [ 0, 1, 2, 3, 4, 5, 6, 10, 15, 20, 100 ]).range(
	 [ "#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666",
	 "#555", "#444", "#333", "#222" ]); */

	var r1 = parseInt(Math.random() * 256); // 색상을 랜덤 부여하기 위한 변수 1,2,3
	var r2 = parseInt(Math.random() * 256);
	var r3 = parseInt(Math.random() * 256);
	var r1mem = 257;

	function draw(words) {
		d3.select("#wordcloud").append("svg").attr("width", 1200).attr(
				"height", // 여기서 div 크기(width, height), 위치(translate) 조절
				450).attr("class", "wordcloud").append("g").attr("transform",
				"translate(600,200)").selectAll("text").data(words).enter()
				.append("text").style("font-weight", function(d) {
					return "600";
				}).style("font-size", function(d) {
					return d.size + "px";
				}).style("fill", function(d, i) {
					while (r1 + r2 + r3 < 550 || r1mem == r1) { // 너무 흰색에 가깝지 않게 처리
						r1 = parseInt(Math.random() * 255);
						r2 = parseInt(Math.random() * 255);
						r3 = parseInt(Math.random() * 255);
					}
					r1mem = r1;
					c1 = r1.toString(16);
					c2 = r2.toString(16);
					c3 = r3.toString(16);
					return c1 + c2 + c3;
				}).attr(
						"transform",
						function(d) {
							return "translate(" + [ d.x, d.y ] + ")rotate("
									+ d.rotate + ")";
						}).text(function(d) {
					return d.text;
				});
	}

	d3.layout.cloud().size([ 1200, 450 ]).words(x).rotate(0).fontSize( // size로 div크기가 아니라, div 안에 그려지는 창의 크기 조절
	function(d) {
		return d.size;
	}).on("end", draw).start(); // 워드 클라우드 끝

	//차트 시작
	var sizeTextList = new Array(2);
	sizeTextList[0] = new Array();
	sizeTextList[1] = new Array();

	for ( var i in x) {
		sizeTextList[0].push(x[i].size);
		sizeTextList[1].push(x[i].text);
	}

	function swap(_arr, _firstIndex, _secondIndex) // 이하의 세개의 함수는 차트 높은순 정렬 용도
	{
		var temp = _arr[0][_firstIndex];
		var temp2 = _arr[1][_firstIndex];
	    _arr[0][_firstIndex]   = _arr[0][_secondIndex];
	    _arr[1][_firstIndex]   = _arr[1][_secondIndex];
	    _arr[0][_secondIndex]  = temp;
	    _arr[1][_secondIndex]  = temp2;
	}
	
	function partition(_arr, _left, _right) 
	{
	 
	    var pivot   = _arr[0][Math.floor((_right + _left) / 2)],
	        left    = _left,
	        right   = _right;
	 
	 
	    while (left <= right) 
	    {
	 
	        while (_arr[0][left] < pivot) 
	        {
	            left++;
	        }
	 
	        while (_arr[0][right] > pivot) 
	        {
	            right--;
	        }
	 
	        if (left <= right) 
	        {
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

	arrSort(sizeTextList);
	sizeTextList[0].reverse();
	sizeTextList[1].reverse();

	var chart = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(chart, {
		type : 'bar',
		data : {
			labels : sizeTextList[1],
			datasets : [ {
				label : '# 검색 빈도',
				data : sizeTextList[0],
				backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
						'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
						'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
						'rgba(255, 159, 64, 0.2)', 'rgba(0, 0, 0, 0.2)' ],
				borderColor : [ 'rgba(255, 99, 132, 1)',
						'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)',
						'rgba(75, 192, 192, 1)', 'rgba(153, 102, 255, 1)',
						'rgba(255, 159, 64, 1)', 'rgba(0, 159, 64, 0.2)' ],
				borderWidth : 1
			} ]
		},
		options : {
			scales : {
				yAxes : [ {
					ticks : {
						beginAtZero : true
					}
				} ]
			}
		}
	});

	// 트리맵 시작

	// set the dimensions and margins of the graph
	var margin = {
		top : 10,
		right : 10,
		bottom : 10,
		left : 10
	}, width = 445 - margin.left - margin.right, height = 445 - margin.top
			- margin.bottom;

	// append the svg object to the body of the page
	var svg = d3.select("#my_dataviz").append("svg").attr("width",
			width + margin.left + margin.right).attr("height",
			height + margin.top + margin.bottom).append("g").attr("transform",
			"translate(" + margin.left + "," + margin.top + ")");

	// Read data
	d3
			.csv(
					'https://raw.githubusercontent.com/holtzy/D3-graph-gallery/master/DATA/data_hierarchy_1level.csv',
					function(data) {
						var root = d3.stratify().id(function(d) {
							return d.name;
						}) // Name of the entity (column name is name in csv)
						.parentId(function(d) {
							return d.parent;
						}) // Name of the parent (column name is parent in csv)
						(data);
						root.sum(function(d) {
							return +d.value
						}) // Compute the numeric value for each entity

						// Then d3.treemap computes the position of each element of the hierarchy
						// The coordinates are added to the root object above
						d3.treemap().size([ width, height ]).padding(4)(root)

						// use this information to add rectangles:
						svg.selectAll("rect").data(root.leaves()).enter()
								.append("rect").attr('x', function(d) {
									return d.x0;
								}).attr('y', function(d) {
									return d.y0;
								}).attr('width', function(d) {
									return d.x1 - d.x0;
								}).attr('height', function(d) {
									return d.y1 - d.y0;
								}).style("stroke", "black").style("fill",
										"#69b3a2");

						// and to add the text labels
						svg.selectAll("text").data(root.leaves()).enter()
								.append("text").attr("x", function(d) {
									return d.x0 + 10
								}) // +10 to adjust position (more right)
								.attr("y", function(d) {
									return d.y0 + 20
								}) // +20 to adjust position (lower)
								.text(function(d) {
									return d.data.name
								}).attr("font-size", "15px").attr("fill",
										"white")
					})
</script>


<%@ include file="/WEB-INF/jspf/footer.jspf"%>