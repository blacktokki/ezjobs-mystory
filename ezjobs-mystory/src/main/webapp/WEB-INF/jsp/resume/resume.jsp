<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
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
</style>
<nav aria-label="breadcrumb">
	<div class="nav nav-tabs breadcrumb pb-0" id="nav-tab" role="tablist">
		<a class="nav-item nav-link breadcrumb-item active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab"
			aria-controls="nav-home" aria-selected="true">작성 목록</a><!-- 작성중,작성중:내용없음 -->
		<a class="nav-item nav-link breadcrumb-item" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" 
			aria-controls="nav-profile" aria-selected="false">작성중</a> 
		<a class="nav-item nav-link breadcrumb-item" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab"
			aria-controls="nav-contact" aria-selected="false">작성 완료</a><!--작성완료:미제출,작성완료:제출됨 작성완료:서류합격 -->
		<a class="nav-item nav-link breadcrumb-item" id="resume-create" href="#">
			새 자기소개서
		</a>
		<!-- 
		<a class="nav-item nav-link breadcrumb-item" id="resume-create" href="#">
			작성 기록
		</a>-->
	</div>
</nav>
<div class="container-fluid row">
	<div class="col-3 pl-2 pr-0">
		<input type="text" class="form-control" id="exampleInput" placeholder="Enter text">
	</div>
	<div class="col-6 px-3">
		<div class="tab-content" id="nav-tabContent">
			<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<div id="accordion1" role="tablist">
					<%@ include file="/WEB-INF/jsp/resume/list.jsp"%>
				</div>
			</div>
			<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
				<div id="accordion2" role="tablist">
				</div>
			</div>
			<div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
				<div id="accordion3" role="tablist">
					새 자기소개서
				</div>
			</div>
		</div>	
	</div>
	<div class="col-4"></div>
</div>
<script>
	var resume_idx=1;
	var resume_new=1;
	$("body").delegate(".tagsinput", "propertychange change keyup paste input", function(e) {
		var id=$(event.target).attr("id").replace("_tag","");
		var tags=[];
		$("#"+id+"_tagsinput").find(".tag>span").each(function(i,e){
			tags.push($.trim($(e).text()));
		});
		//console.log($("#"+id).val());
		$("#"+id).val(tags.join(","));
		return true;
	});
	$("body").delegate("#resume-create", "click", function() {
		 $(document.createDocumentFragment()).load("/resume/write",function(response){
			var $result=$(response);
			$result.find(".card-header")
		           .attr("id","heading-write"+resume_idx)
		           .find("a")
				   .attr("href","#collapse-write"+resume_idx)
				   .attr("aria-controls","collapse-write"+resume_idx)
				   .html("새 자기소개서 "+resume_new);
			$result.find(".collapse")
				   .attr("id","collapse-write"+resume_idx)
				   .attr("aria-labelledby","heading-write"+resume_idx)
				   .find(".write-question")
				   .attr("id","write-question"+resume_idx)
				   .val("새 자기소개서 "+resume_new);
			$result.find(".tags").attr("id","tags-write"+resume_idx);
			$("#nav-profile-tab").tab("show");
			$result.appendTo("#accordion2").find(".collapse").collapse("show");
			$("#tags-write"+resume_idx).tagsInput();
<<<<<<< HEAD
=======

>>>>>>> branch 'develop-prepare' of https://github.com/blacktokki/ezjobs-mystory.git
			resume_idx+=1;
			resume_new+=1;
		});
		return false;
	});
	$("#accordion1").delegate(".resume-link","click",function(e){
		var href=$(e.target).attr("href");
		var card=href.replace("/resume/write/","#resume-card");

		if($(card).length==0){
			$(document.createDocumentFragment()).load(href,function(response){
				var $result=$(response);
				$result.find(".card-header")
			           .attr("id","heading-write"+resume_idx)
			           .find("a")
					   .attr("href","#collapse-write"+resume_idx)
					   .attr("aria-controls","collapse-write"+resume_idx)
				$result.find(".collapse")
					   .attr("id","collapse-write"+resume_idx)
					   .attr("aria-labelledby","heading-write"+resume_idx)
					   .find(".write-question")
					   .attr("id","write-question"+resume_idx)
				$result.find(".tags").attr("id","tags-write"+resume_idx);
				$("#nav-profile-tab").tab("show");
				$result.appendTo("#accordion2").find(".collapse").collapse("show");
				$("#tags-write"+resume_idx).tagsInput();

				resume_idx+=1;
			});
		}
		else{
			$(card).find(".collapse").collapse("show");
		}
		return false;
	})
	$("#accordion2").delegate(".write-question","propertychange change keyup paste input", function(e) {
		var id=$(e.target).attr("id").replace("heading","");
		$("#heading-write"+id+" a").html(currentVal);
	});
	$("#accordion2").delegate("form","submit",function(e){
		var form=$(e.target).serializeJSON();
		//console.log(form);
		$.post("/resume/content/"+form.id,form,function(data){
			//console.log(data);
			$(e.target).find(".resume-id").val(data.map.id);
			$(e.target).find(".resume-method").val("put");
			$.get("/resume/content",function(data2){
				console.log(data2);
				$("#accordion1").html(data2);
			});
		});
		return false;
	});
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
