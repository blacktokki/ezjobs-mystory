<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->
<div>
	<img src="/image/building.jpg" width="100%" height="340">
</div>

<div class="jumbotron mb-0 px-5 py-5 d-none d-md-block">
	<%@ include file="/WEB-INF/jspf/search/search.jspf"%>
</div>

<br>
<br>
 
<div class="container pb-3"> <!-- style="position: relative; margin: 0px 0px 120px 440px;" -->
	<div class="navbar navbar-light" style="background-color:#EDEDED">	
		<div class="navbar-brand"><h3>&nbsp;검색 결과</h3></div>
		<ul class="navbar-nav flex-row">
			<li class="nav-item dropdown mr-3">
		 		<button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					페이지 당 자료 수
				</button>
				<div class="dropdown-menu" aria-labelledby="dropdownMenu1">
					<button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=5&searchTags=${searchTags}'" style="text-align: right;">5개&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=10&searchTags=${searchTags}'" style="text-align: right;">10개&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=20&searchTags=${searchTags}'" style="text-align: right;">20개&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
			</li>
			<li class="nav-item dropdown">
			  <button class="btn btn-success dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			    검색 범위 지정
			  </button>
			  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
			    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=1&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}'" style="text-align: right;">태그 하나 이상(OR)</button>
			    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=2&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}'" style="text-align: right;">태그 모두 포함(AND)</button>
			  </div>
			</li>
		</ul>
	</div>
	<br>
	<div style="font-size: 12px;">
		<div style="float: left; font-weight: bold; font-size: 14px; color: #197fd9;">${param.searchText}</div>&nbsp;
		에 대한 검색 결과입니다.
		<div style="float: right">
			검색된 문장 수 :
		<div style="float: right; font-size: 15px; color: #008d62; font-weight: bold">&nbsp;&nbsp;${recordCount}
		</div>
		</div>
	</div>
	<div>
		<c:forEach var="item" items="${searchs.content}">
			<tr>
				<td>
					<div class="card" style="border-left: 2px solid #2196f3; width: 100%; height:7rem;">
						<div class="card-body">
							<div style="font-size: 16px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; margin-bottom: 8px;">
								${item.text}</div><br>
							<div style="font-size: 14px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; margin-bottom: 8px;">
								태그 : ${item.tags}</div>	
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</div>
</div>
<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center">
		<li class="page-item"><a class="page-link"
			href="?searchText=${ param.searchText }&page=${pageNavNumber*5 + ifPageZeroThenPlusOne}&searchWay=${searchWay}&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}"
			aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
		</a></li>
		<c:forEach var="item" begin="${pageNavNumber*5+1}"
			end="${(pageNavNumber+1)*5}">
			<li class="page-item"><a class="page-link"
				href="?searchText=${ param.searchText }&page=${item}&searchWay=${searchWay}&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}">${item}</a></li>
		</c:forEach>
		<li class="page-item"><a class="page-link"
			href="?searchText=${ param.searchText }&page=${(pageNavNumber+1)*5+1}&searchWay=${searchWay}&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}"
			aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a></li>
	</ul>
</nav>

<script>
var tagsError=${tagsError};
if(tagsError==1){
	alert("태그는 세개 초과하여 검색할 수 없습니다.");
	location.href='/search/list?searchText=${userSearchWord}&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=${numberOfSeeSentence}&searchTags=${searchTagArray0}%2C${searchTagArray1}%2C${searchTagArray2}'
}
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>