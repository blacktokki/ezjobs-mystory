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

.list-sentence li:hover{
	background:#BBBBBB;
}
</style>

<nav aria-label="breadcrumb">
	<div class=" breadcrumb pb-0  mx-0 row">
		<div class="nav nav-tabs col-2 " id="nav-tab-left" role="tablist">
			<a class="nav-item nav-link breadcrumb-item text-right" id="resume-create" href="#"> 새 자기소개서 </a>
		</div>
		<div class="nav nav-tabs accordion justify-content-center col-8" id="nav-tab" role="tablist">
			<a class="nav-item nav-link breadcrumb-item text-center active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab"
				aria-controls="nav-home" aria-selected="true">작성 목록</a>
			<!-- 작성중,작성중:내용없음 -->
			<a class="nav-item nav-link breadcrumb-item text-center" id="nav-profile-tab" data-toggle="tab" href="#nav-profile"
				role="tab" aria-controls="nav-profile" aria-selected="false">작성중</a>
			<a class="nav-item nav-link breadcrumb-item text-center" id="nav-contact-tab" data-toggle="tab" href="#nav-contact"
				role="tab" aria-controls="nav-contact" aria-selected="false">작성 완료</a>
			<!--작성완료:미제출,작성완료:제출됨 작성완료:서류합격 -->
			<!--
			<a class="nav-item nav-link breadcrumb-item text-center" id="nav-contact-tab" data-toggle="tab" href="#nav-contact"
				role="tab" aria-controls="nav-contact" aria-selected="false">작성 기록</a>
			-->
		</div>
		<div class="col-2">
			<div class="justify-content-right" id="nav-tab-right">
			</div>
		</div>
	</div>
</nav>
<!--  -->
<div class="container-fluid row" style="min-height:66vh">
	<div class="col-2">
	</div>
	<div class="tab-content col-8" id="nav-tabContent">
		<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
			<div id="accordion1" role="tablist">
				<%@ include file="/WEB-INF/jsp/resume/list.jsp"%>
			</div>
		</div>
		<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
			<div id="accordion2" role="tablist"></div>
		</div>
		<div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
			<div id="accordion3" role="tablist">
				<%@ include file="/WEB-INF/jsp/resume/writedlist.jsp"%>
			</div>
		</div>
	</div>
	<div class="col-2">
	</div>
</div>
<%@ include file="/WEB-INF/jsp/resume/review.jsp"%>
<script>
	var resume_idx = 1;
	var resume_new = 1;
	var	timer_auto=null;
	var ck_config={
		width: "100%",
		height: 500,
		language:'korean',
		toolbar:[
			{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo','Redo2'] },
			{ name: 'editing', groups: [ 'find', 'selection' ], items: [ 'Find', 'Replace', '-', 'SelectAll'] },
			{ name: 'about', items: [ 'About' ] }
		],
		wordcount:{
			showWordCount: true,
			showCharCount: true,
			showParagraphs: false,
			countSpacesAsChars: true,
		}
	 }
	//console.log(CKEDITOR.plugins);
	//console.log(CKEDITOR.config.plugins);
	//console.log(CKEDITOR.config);
	CKEDITOR.config.extraPlugins = 'wordcount';
	
	function refreshList(){
		$.get("/resume/content?state=미작성", function(data) {
			//console.log(data2);
			$("#accordion1").html(data);
		});
		
		$.get("/resume/content?state=작성완료", function(data) {
			//console.log(data2);
			$("#accordion3").html(data);
		});
	}

	function Export2Doc(content, filename){
	    var preHtml = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:w='urn:schemas-microsoft-com:office:word' xmlns='http://www.w3.org/TR/REC-html40'><head><meta charset='utf-8'><title>Export HTML To Doc</title></head><body>";
	    var postHtml = "</body></html>";
	    var html = preHtml+content+postHtml;
	    var blob = new Blob(['\ufeff', html], {
	        type: 'application/msword'
	    });
	    
	    // Specify link url
	    var url = 'data:application/vnd.ms-word;charset=utf-8,' + encodeURIComponent(html);
	    // Specify file name
	    filename = filename?filename+'.doc':'document.doc';
	    // Create download link element
	    if(navigator.msSaveOrOpenBlob ){
	        navigator.msSaveOrOpenBlob(blob, filename);
	    }else{
	    	var downloadLink = document.createElement("a");
		    document.body.appendChild(downloadLink);
	        // Create a link to the file
	        downloadLink.href = url;
	        // Setting the file name
	        downloadLink.download = filename;
	        //triggering the function
	        downloadLink.click();
	        document.body.removeChild(downloadLink);   
	    }
	}
	function reviewText(){
		var currentVal="";
		$copy=$("#wordChange ul .list-sentence").clone();
		if($copy.length>0){
			$copy.find("select").each(function(i,element){
				$(element).html($(element).find("option:selected").val());
			});
			$copy.each(function(i,element){
				currentVal+=" "+$.trim($(element).text().replace(/\s+/g, " "));
				if($(element).find("br").length)
					currentVal+="\r\n";	
			});
			console.log(currentVal);
		};
		return currentVal;
	}
	function writePageBuild($result,callback){
		$result.find(".card-header")
			.attr("id","heading-write" + resume_idx)
			.find("a")
			.attr("href","#collapse-write" + resume_idx)
			.attr("aria-controls","collapse-write" + resume_idx);
		$result.find(".collapse")
			.attr("id","collapse-write" + resume_idx)
			.attr("aria-labelledby","heading-write" + resume_idx)
			.find(".write-question")
			.attr("id","write-question" + resume_idx);
		$result.find(".collapse").find(".write-answer").attr("id","write-answer" + resume_idx).ckeditor(ck_config);
		$result.find(".tags").attr("id","tags-write" + resume_idx);
		$result.find(".write-date").datepicker({
		    dateFormat: 'yy-mm-dd'
	  	});
		callback($result);
	}
	function writePageAppend($result){
		$("#nav-profile-tab").tab("show");
		$result.appendTo("#accordion2").find(".collapse").collapse("show");
		$("#tags-write" + resume_idx).tagsInput({
			'height' : '100%',
			'width' : '80%',
		});
		var editor=$("#write-answer" + resume_idx).ckeditor().editor;
		var ac=new CKEDITOR.plugins.autocomplete(editor, ac_config );
	}
	
	$("body").delegate("#resume-create","click",function() {
		$(document.createDocumentFragment()).load("/resume/write",function(response) {//새 자기소개서
			writePageBuild($(response),function($result){
				$result.find(".write-question").val("새 자기소개서 " + resume_new);
				$result.find(".card-header a").html("새 자기소개서 " + resume_new);
				writePageAppend($result);
			});
			resume_idx += 1;
			resume_new += 1;
		});
		return false;
	});
	$("#accordion1").delegate(".resume-link","click",function(e) {//자기소개서 불러오기
		var href = $(e.target).attr("href");
		var card = href.replace("/resume/write/", "#resume-card");
		//console.log(card);
		if ($(card).length == 0) {
			$(document.createDocumentFragment()).load(href,function(response) {
				writePageBuild($(response),writePageAppend);
				resume_idx += 1;
			});
		} else {
			$("#nav-profile-tab").tab("show");
			$(card).find(".collapse").collapse("show");
		}
		return false;
	});
	$("#accordion3").delegate(".resume-link","click",function(e) {//자기소개서 불러오기(완성된)
		return false;
	});
	$("#accordion1").delegate(".resume-link-state","click",function(e) {//자기소개서 상태변경	
		var href = $(this).attr("href");
	 	//console.log(href);
		var form = {_method:"put",state:"작성완료"};
		$.post(href, form, function(data) {
			//console.log(data);
			refreshList();
		});
		return false;
	});
	
	$("#accordion3").delegate(".resume-link-state","click",function(e) {//자기소개서 상태변경	
		var href = $(this).attr("href");
		var form = {_method:"put",state:"미작성"};
		$.post(href, form, function(data) {
			//console.log(data);
			refreshList();
		});
		return false;
	});

	$("#accordion2").delegate(".write-question","propertychange change keyup paste input", function(e){//제목 동기화
				var id = $(e.target).attr("id").replace("write-question", "");
				var currentVal=$(e.target).val();
				$("#heading-write" + id + " a").html(currentVal);
			});
	
	$("#accordion2").delegate(".card-header","click",function(e){
		$("#autoComplete-keyword").val("");
		$("#autoComplete ul").html("");
		clearTimeout(timer_auto);
	});
	
	$("body").delegate(".review-modal","shown.bs.modal",function(e){//검토 하기
		var currentVal=$("#accordion2 .card").find(".show").find(".write-answer").val();
		console.log(currentVal);
		var form={answer:currentVal};
		$.get("/resume/changelist", form, function(data) {
			$("#wordChange ul").html(data).sortable().find("li").each(function(i,element){
				$(element).find("ul").sortable();
			});
		});
	});
	
	$("#wordChange").delegate("select","change",function(e){//단어교체
		var first=$("option",this).first().val();
		var change=this.value;
		if(change=="_add"){
			change=prompt("단어 추가",first);
			console.log(change);
			if (change!=""&&change!=null){
				$("option[value=_add]",this).text(change).attr("value",change);
				$("<option value='_add'>추가..</option>").appendTo($(this))
				var form={
					keyword:first,
					synonym:change
				}
				$.post("/resume/synonym/", form, function(data) {
					//console.log(data);
					$(e.target).find(".resume-id").val(data.map.id);
					$(e.target).find(".resume-method").val("put");
					refreshList();
				});
			}
			else{
				change=first;
			}
		}
		$("option[value="+change+"]",this)
		.attr("selected",true).siblings()
		.removeAttr("selected");
		
	});
	
	$("#exampleModal").delegate(".btn-apply","click",function(e){//단어교체 적용하기
		var currentVal=reviewText();
		$target=$("#accordion2 .card").find(".show").find(".write-answer").val(currentVal);
		return false;
	});
	
	$("#exampleModal").delegate(".btn-load","click",function(e){//유사도검사
		var currentVal=reviewText();
		//console.log(currentVal.replace(/<p>|<\/p>/g));
		var form={answer:currentVal.replace("<p>","").replace("</p>","")};
		$.get("/resume/comparelist", form, function(data) {
			$("#compare ul").html(data).find("li").each( function() {
		          var sentence=$.trim($(this).text().replace(/\s+/g, " "));
		          var form={sentence:sentence};
		          $(this).html('<div class="spinner-border" role="status">'
		        		  +'<span class="sr-only">Loading...</span></div>');
		          var $target=$(this);
		          $.get("/resume/compare", form, function(data_part) {
		        	  $target.html(data_part);
		          });
		    });
			
		});
		return false;
	});
	
	$("#compare").delegate(".btn-check","click",function(e){//맞춤법검사
		var currentVal=$("#accordion2 .card").find(".show").find(".write-answer").val();
		
		var url = "http://www.saramin.co.kr/zf_user/tools/spell-check?content="+currentVal;
        var $script = $("<script><\/script>");
        $script.attr("type","application/json").attr("src",url);
        console.log($script.appendTo("head").text());
      
		return false;
	});
	
	
	$("#accordion2").delegate(".resume-export","click",function(e){//내보내기
		var form=$(e.target).closest("form").serializeJSON();
		//console.log(form.answer);
		var text=form.question+" - "+form.company+"<p>"
		+form.answer;
		Export2Doc(text,form.question);
		return false;
	});
	
	var saveMethod=""
	$("#accordion2").delegate("form :submit","click",function(e){
		saveMethod=$(e.target).val();
	});
	
	$("#accordion2").delegate("form", "submit", function(e) {//저장하기

		var tags = [];
		 $(event.target).find(".tagsinput").find(".tag>span").each(
		function(i, e) {
			tags.push($.trim($(e).text()));
		});
		$(event.target).find(".tags").val(tags.join(","));
		var form = $(e.target).serializeJSON();
		form._method=saveMethod;
		if(saveMethod=="post")
			form.id="";
		saveMethod="";
		console.log(form);
		$.post("/resume/content/" + form.id, form, function(data) {
			var id=data.map.id;
			$(e.target).find(".resume-id").val(data.map.id);
			$(e.target).closest(".card").attr("id","resume-card"+data.map.id);
			$(e.target).find(".resume-method").val("put");
			refreshList();
		});
		return false;
	});
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>


