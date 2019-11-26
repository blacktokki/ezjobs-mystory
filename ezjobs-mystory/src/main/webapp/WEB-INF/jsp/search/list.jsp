<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<script src="jquery.tagsinput.js"></script>
<link rel="stylesheet" type="text/css" href="jquery.tagsinput.css" />
<!-- body -->
<div>
	<img src="/image/building.jpg" width="1520" height="340">
</div>

<form method="get" action="/search/list" style="left:50%; font-size: 16px; position: absolute; width: 660px; height: 200px; border: 3px solid gray; text-align: center; vertical-align: middle; background-color: white; padding-top:34px; margin : -230px 0px 0px -300px;">
		<div
			style="float: left; font-size: 20px; color: blue; font-weight: bolder; margin: 15px 0px 0px 20px;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Search">자기소개서 검색</label>
		</div>
		<input type="text" name="searchText" id="Search" placeholder=" 자기소개서 내용 검색" style="width:300px; margin: 0px 0px 0px -60px;" value="${userSearchWord}">&nbsp;&nbsp; 
		<input type="hidden" name="page" value="1" />
		<input type="hidden" name="searchWay" value="${searchWay}" />
		<input type="hidden" name="numberOfSeeSentence" value="${numberOfSeeSentence}" />
		<div
			style="float: left; font-size: 22px; color: #7bf09f; font-weight: bolder; margin: 85px 0px 0px -140px;">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;태그 추가
		</div>
		<div style="margin:20px 0px 0px 238px;"><input name="searchTags" id="tags" value="${searchTags}"/></div>
		<button type="submit" class="btn btn-primary resume-submit fa fa-search" style="margin: -220px 0px 2px 530px; width:50px; height: 35px;"></button>
		
</form>

<%-- <form method="get" action="/search/list" style="left:50%; font-size: 16px; position: absolute; width: 440px; height:20px; text-align: center; vertical-align: middle; background-color: white; margin : -150px 0px 0px -120px;">
			<!-- <div style="margin:0px 0px 0px 60px;"><input name="searchTags" id="tags" value="foo,bar,baz"/></div> --> 
			<input type="hidden" name="page" value="1" />
			<input type="hidden" name="searchWay" value="${searchWay}" />
			<input type="hidden" name="searchText" value="" />
			<input type="hidden" name="numberOfSeeSentence" value="${numberOfSeeSentence}" />
		<button type="submit" class="btn btn-success resume-submit fa fa-search" style="margin: -135px 0px 0px 380px;"></button>
</form> --%>

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
								style="border-left: 2px solid #2196f3; width: 63rem; height:7rem;">
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
			</tbody>
		</table>
	</div>
<div class="dropdown" style="position:absolute; left:600px; top:15px;">
  <button class="btn btn-info dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    페이지 당 자료 수
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=1&searchTags=${searchTags}'" style="text-align: right;">1개&nbsp;&nbsp;&nbsp;&nbsp;</button>
    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=3&searchTags=${searchTags}'" style="text-align: right;">3개&nbsp;&nbsp;&nbsp;&nbsp;</button>
    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=5&searchTags=${searchTags}'" style="text-align: right;">5개&nbsp;&nbsp;&nbsp;&nbsp;</button>
  </div>
</div>

<div class="dropdown" style="position:absolute; left:840px; top:15px;">
  <button class="btn btn-success dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    검색 범위 지정
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=1&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}'" style="text-align: right;">태그 하나 이상(OR)</button>
    <button class="dropdown-item" type="button" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=2&numberOfSeeSentence=${numberOfSeeSentence }&searchTags=${searchTags}'" style="text-align: right;">태그 모두 포함(AND)</button>
  </div>
</div>

<%-- <div style="position:absolute; left:480px; top:0px;">
&nbsp;&nbsp;한번에 볼 자기소개서 수<br>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=1'">1</button>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=3'">3</button>&nbsp;
<button type="button" class="btn btn-secondary" onclick="location.href = '?searchText=${ param.searchText }&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=5'">5</button>&nbsp;
<br>
<br>
</div> --%>

<%-- <div style="position:absolute; left:780px; top:0px;">
&nbsp;&nbsp;조건으로 검색 <br>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=1&numberOfSeeSentence=${numberOfSeeSentence }'">전체</button>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=2&numberOfSeeSentence=${numberOfSeeSentence }'">소제목</button>&nbsp;
<button type="button" class="btn btn-info" onclick="location.href = '?searchText=${ param.searchText }&page=1&searchWay=3&numberOfSeeSentence=${numberOfSeeSentence }'">내용</button>&nbsp;
</div> --%>

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
	
</div>

<script>
$('#tags').tagsInput({
  autocomplete_url:'http://myserver.com/api/autocomplete',
  autocomplete:{selectFirst:true,width:'100px',autoFill:true}
});
if(${tagsError}==1){
	alert("태그는 세개 초과하여 검색할 수 없습니다.");
	location.href='/search/list?searchText=${userSearchWord}&page=${nowPage}&searchWay=${searchWay}&numberOfSeeSentence=${numberOfSeeSentence}&searchTags=${searchTagArray0}%2C${searchTagArray1}%2C${searchTagArray2}'
}
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
