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
	
document.getElementById("jsonChart1").style.display = "none"; // 기본적으로 워드클라우드만 보여주기 위한 처리
document.getElementById("jsonChart2").style.display = "none";
document.getElementById("vizTreeMap").style.display = "none";
window.addEventListener('DOMContentLoaded', function() {
	require(["d3","chart"],function(d3,Chart){
		
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
			var sizeTextList2 = new Array(2);
			sizeTextList2[0] = new Array();
			sizeTextList2[1] = new Array();
			
			for (var i in y2) {
				sizeTextList2[0].push(y2[i].doc_count);
				sizeTextList2[1].push(y2[i].text);
			}
			arrSort(sizeTextList2);
			sizeTextList2[0].reverse();
			sizeTextList2[1].reverse();
			
			sizeTextList2[0] = sizeTextList2[0].slice(0,40); // #데이터출력수
			sizeTextList2[1] = sizeTextList2[1].slice(0,40);
			
			var chartColors = new Array();
			var chartBold = new Array();
			r1 = parseInt(Math.random() * 256); // 색상을 랜덤 부여하기 위한 변수 1,2,3
			r2 = parseInt(Math.random() * 256);
			r3 = parseInt(Math.random() * 256);
			r1mem = 257;
			var i = 0;
			
			while (i < sizeTextList2[0].length) { // 랜덤 색상 설정
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
						labels : sizeTextList2[1],
						datasets : [ {
							label : '# 시작 단어 태그 빈도',
							data : sizeTextList2[0],
							backgroundColor : chartColors,
							borderColor : chartBold,
							borderWidth : 1
						} ]
					},
					options : {
						 onClick: function(evt, active) { // 클릭시 링크
						      var Id = active[0]._index;
						      var charConvert = sizeTextList2[1][Id]; 
						   // #특수문자주소 : 특수문자 주소를 변환하는 곳
						      charConvert = charConvert.replaceAll("&", "%26");
						      charConvert = charConvert.replaceAll("(", "%28");
						      charConvert = charConvert.replaceAll(")", "%29");
						      location.href ="/search/list?searchText="+charConvert;
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
	});
});