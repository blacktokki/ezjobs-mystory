<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>


<%@ page import="java.util.*"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="org.springframework.ui.Model"%>



<head>

<!-- 삭제예정 :: 스타일 참고용 주석
	<style type="text/css">
	
		input::placeholder{
			font-style: italic;
			font-size: 11px;
			font-weight: 500;
		}
		html>body {
			width: 100%;
		}
		th{
			color: white;
		}
		
	</style> -->

</head>

<!-- body -->

<div>
	<img src="/image/building.jpg" width="1520" height="340">
</div>

<form method="get" action="/search/list" style="font-size: 16px; position: absolute; width: 480px; height: 110px; border: 3px solid gray; text-align: center; vertical-align: middle; background-color: white; padding-top:34px; margin : -200px 0px 0px 560px;">
		<div
			style="float: left; font-size: 20px; color: blue; font-weight: bolder">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Search">자기소개서 검색</label>
		</div>
		<input type="text" name="searchText" id="Search"
			placeholder=" 검색어를 입력해주세요 .">&nbsp;&nbsp; 
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="searchWay" value="${searchWay}" />
			<input type="hidden" name="numberOfSeeResume" value="${numberOfSeeResume}" />
		<button type="submit" class="btn btn-primary resume-submit">검색</button>
		<br>
</form>
<br>
<br>

<div
	style="position: relative; left: 440px; top: 0px; width:1000px; padding:  0px 0px 120px 0px;">
	<div class="pagination justify-content-center">

		<table class="table table-borderless">
			<thead>
				<tr bgcolor="#EDEDED">
					<th><h3>&nbsp;검색 결과</h3></th>
				</tr>
				<tr>
					<th style="font-size: 12px;"><div
							style="float: left; font-weight: bold; font-size: 14px; color: #197fd9;">${param.searchText}</div>&nbsp;에
						대한 검색 결과입니다.
						<div style="float: right">
							검색된 자소서 수 :
							<div
								style="float: right; font-size: 15px; color: #008d62; font-weight: bold">&nbsp;&nbsp;${recordCount}
								</div>
						</div>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${searchs.content}">
					<tr>
						<td>
							<div class="card"
								style="border-left: 2px solid #2196f3; width: 63rem; height:6rem;">
								<div class="card-body">
									<div
										style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis; margin-bottom: 8px;">
										<h5>${item.question}</h5>
									</div>
									<div
										style="font-size: 14px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; margin-bottom: 8px;">
										${item.answer}</div>
								</div>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<div style="position:absolute; left:-380px; top:50px;">
한번에 볼 자기소개서 수<br>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeResume=1'">1</button>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeResume=3'">3</button>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeResume=5'">5</button>&nbsp;
<br>
<br>
조건으로 검색 <br>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=1&numberOfSeeResume=${numberOfSeeResume }'">전체</button>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=2&numberOfSeeResume=${numberOfSeeResume }'">소제목</button>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=3&numberOfSeeResume=${numberOfSeeResume }'">내용</button>&nbsp;
</div>
	<!-- <div style="position: absolute; top:600px;">
<button type="button" class="btn btn-secondary">Secondary</button>
<button type="button" class="btn btn-secondary">Secondary</button>
<select name="searchWay">
				<option value="1" >1개씩 보기</option>
				<option value="3"selected="selected" >3개씩 보기</option>
				<option value="5">5개씩 보기</option>
</select>

<div style="text-align: left; color: #858585; font-weight: bold">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			검색 조건 선택 &nbsp;:&nbsp;&nbsp;&nbsp; <select name="searchWay">
				<option value="1" selected="selected">전체</option>
				<option value="2">소제목</option>
				<option value="3">내용</option>
			</select>
		</div>


</div> -->

	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item"><a class="page-link"
				href="?searchText=${ param.searchText }&page=${pageNavNumber*5 + ifPageZeroThenPlusOne}&searchWay=${searchWay}&numberOfSeeResume=${numberOfSeeResume }"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
			</a></li>
			<c:forEach var="item" begin="${pageNavNumber*5+1}"
				end="${(pageNavNumber+1)*5}">
				<li class="page-item"><a class="page-link"
					href="?searchText=${ param.searchText }&page=${item}&searchWay=${searchWay}&numberOfSeeResume=${numberOfSeeResume }">${item}</a></li>
			</c:forEach>
			<li class="page-item"><a class="page-link"
				href="?searchText=${ param.searchText }&page=${(pageNavNumber+1)*5+1}&searchWay=${searchWay}&numberOfSeeResume=${numberOfSeeResume }"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</nav>
	
</div>

<%--  
 <br><br><br><br><br><br><br><br><br><br><br>

검색어 : ${requestScope.tps}<br>
시작 블록 : ${requestScope.startBlock}<br> 
현재 페이지 : ${requestScope.nowPage}<br>
마지막 페이지 : ${requestScope.lastPage}<br>
마지막 블록 : ${requestScope.lastBlock}<br>
총 검색 자료 수 : ${requestScope.total}<br> --%>

</body>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
