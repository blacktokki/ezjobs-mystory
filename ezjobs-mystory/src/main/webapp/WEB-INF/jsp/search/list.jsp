<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/jspf/head.jspf"%>


<%@ page import="java.util.*" %>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="org.springframework.ui.Model" %>


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

<div style="position: absolute; left: 0px; top: 0px;">
	<img src="/image/building.jpg" width="1540" height="340">
</div>

<div style="position: relative; left: 560px; top: 190px; width: 480px; height: 70px; border: 3px solid gray; text-align: center; display: table-cell; vertical-align: middle; background-color:white" >
	<form method="get" action="/search/list">
		<label for="Search">자기소개서 검색&nbsp;&nbsp;&nbsp;</label>
		<input type="text" name="searchText" id="Search" placeholder=" 검색어를 입력해주세요 .">&nbsp;&nbsp;
		<input type="hidden" name="page" value="1"/>
		<button type="submit" class="btn btn-primary resume-submit">검색</button>
	</form>
</div>
<br>
<br>

<c:set var="bg" value = "${5*(nowPage-1)}"/>
<c:set var="st" value = "4"/>

<div style="position: absolute; left: 500px; top: 380px; width: 600px; height: 300px; margin: 0px 0px 200px 0px;">
	<div class="pagination justify-content-center">
			<table class="table">
				<!-- 테이블 제목 -->
				<thead>
					<tr bgcolor="#EDEDED">
						<th width="60">번호</th>
						<th>제목</th>
						<th width="150">작성자</th>
						<th width="270">작성일</th>
					</tr>
				</thead>
				<!-- 티에블 내용 채우기 -->
				<tbody>
							
				<c:forEach var="item" items="${resumes.content}">
					<tr>
						<td align="center">${item.id}</td>
						<td>${item.company}&nbsp;</td>
						<td>${item.company}&nbsp;</td>
						<td align="center">${item.editDate}</td>
					</tr>
				</c:forEach>
				</tbody>

			</table>
		</div>
	
        <%-- 이전 내가 만든 페이징 DB 연동 성공시 삭제
         <c:if test="${ nowPage > 5 }">
            <a href="?searchText=${ param.searchText }&page=${ startBlock - 1 }">◀</a>
        </c:if>
        
        <c:forEach var="i" begin="${ startBlock }" end="${ lastBlock }">
        	<c:if test = "${ lastPage >= i }">
	        	<a href="?searchText=${ param.searchText }&page=${ i }">${ i }</a>
    	    </c:if>
        </c:forEach>
        
        <c:if test="${ lastPage > lastBlock }">
            <a href="?searchText=${ param.searchText }&page=${ lastBlock + 1 }">▶</a>
        </c:if> --%>
        
        <nav aria-label="Page navigation example">
			<ul class="pagination justify-content-center">
				<li class="page-item"><a class="page-link" href="?page=${pageNavNumber*5}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach var="item" begin="${pageNavNumber*5+1}" end="${(pageNavNumber+1)*5}">
					<li class="page-item"><a class="page-link" href="?page=${item}">${item}</a></li>
				</c:forEach>
				<li class="page-item"><a class="page-link" href="?page=${(pageNavNumber+1)*5+1}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</nav>
</div>

 <br><br><br><br><br><br><br><br><br><br><br>

검색어 : ${requestScope.tps}<br>
시작 블록 : ${requestScope.startBlock}<br> 
현재 페이지 : ${requestScope.nowPage}<br>
마지막 페이지 : ${requestScope.lastPage}<br>
마지막 블록 : ${requestScope.lastBlock}<br>
총 검색 자료 수 : ${requestScope.total}<br>
 
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
