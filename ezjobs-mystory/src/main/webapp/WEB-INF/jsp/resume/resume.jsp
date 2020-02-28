<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/extendHead.jspf"%>
<style>
[data-toggle="collapse"]:after {
	display: inline-block;
	font: normal normal normal 14px/1 FontAwesome;
	font-size: inherit;
	text-rendering: auto;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	content: "\f054";
	transform: rotate(90deg);
	transition: all linear 0.25s;
	float: right;
}

[data-toggle="collapse"].collapsed:after {
	transform: rotate(0deg);
}

.list-sentence li:hover{
	background:#BBBBBB;
}
</style>

<nav aria-label="breadcrumb">
	<div class=" breadcrumb pb-0  mx-0 row">
		<div class="nav nav-tabs col-2 " id="nav-tab-left" role="tablist">
			<a class="nav-item nav-link breadcrumb-item text-right" id="resume-create" href="#" onclick="event.preventDefault();createResume()"> 새 자기소개서 </a>
		</div>
		<div class="nav nav-tabs accordion justify-content-center col-8" id="nav-tab" role="tablist">
			<a class="nav-item nav-link breadcrumb-item text-center active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab"
				aria-controls="nav-home" aria-selected="true">작성 목록</a>
			<a class="nav-item nav-link breadcrumb-item text-center" id="nav-profile-tab" data-toggle="tab" href="#nav-profile"
				role="tab" aria-controls="nav-profile" aria-selected="false">작성중</a>
		</div>
		<div class="col-2">
			<div class="justify-content-right" id="nav-tab-right">
			</div>
		</div>
	</div>
</nav>
<!--  -->
<div class="container px-5" style="min-height:66vh">
	<div class="tab-content" id="nav-tabContent">
		<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
			<div id="accordion1" role="tablist">
				<%@ include file="/WEB-INF/jsp/resume/list.jsp"%>
			</div>
		</div>
		<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
			<div id="accordion2" role="tablist"></div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/jsp/resume/review.jsp"%>
<script src="/js/ckeditor/autocomplete-config.js"></script>
<script>
	var createResume;
	var changePageSize;

	window.addEventListener('DOMContentLoaded', function() {
		createResume=function(){
			RESUME.app.createResume();
		}
		changePageSize=function(){
			RESUME.app.getList();
		}
	});
</script>
<script src="/webjars/ckeditor/standard/ckeditor.js" defer></script>
<script src="/webjars/ckeditor/standard/adapters/jquery.js" defer></script>
<script src="/wro4j/cke-plugins.js" defer></script>
<script src="/wro4j/resume.js" defer></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>


