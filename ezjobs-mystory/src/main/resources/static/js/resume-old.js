var resume_idx = 1;
var resume_new = 1;
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
CKEDITOR.config.extraPlugins = 'wordcount';

function refreshList(){
	var form=$("#accordion1").find(".search-form").serializeJSON();
	form.size=$(".page-form").find("select").val();
	console.log(form);
	$.get("/resume/content",form, function(data) {
		//console.log(data2);
		$("#accordion1").html(data);
	});
}

function changePageSize(e){
	refreshList();
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
	$result.find(".collapse")
		.find(".write-answer")
		.attr("id","write-answer" + resume_idx)
		.ckeditor(ck_config);
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

function reviewText(){
	var currentArray=[];
	$copy=$("#wordChange ul .list-sentence").clone();
	if($copy.length>0){
		$copy.find("select").each(function(i,element){
			$(element).html($(element).find("option:selected").val());
		});
		$copy.each(function(i,element){
			var sentence=$.trim($(element).text().replace(/\s+/g, " "));
			if($(element).find("br").length)
				sentence+="\r\n";
			currentArray.push(sentence);
		});
		console.log(currentArray);
	};
	return currentArray;
}
function createResume(){
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
}

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
})/*.delegate(".resume-link-state","click",function(e) {//자기소개서 상태변경	
	var href = $(this).attr("href");
 	//console.log(href);
	var form = {_method:"put",state:"작성완료"};
	$.post(href, form, function(data) {
		//console.log(data);
		refreshList();
	});
	return false;
})*/.delegate(".search-form","submit",function(e) {//조건검색
	//alert("조건검색");
	e.preventDefault();
	refreshList();
}).delegate(".page-item a","click",function(e){//페이지 이동
	var page=$(e.target).attr("data-page");
	$(".search-form").find("input[name=page]").val(page);
	refreshList();
	return false;
})
.delegate(".delete-form","submit",function(e){//자기소개서 삭제
	var form=$(e.target).serializeJSON();
	console.log(form);
	e.preventDefault();
	$.post("/resume/content",form,function(data){
		refreshList();
		$("#delete"+form.id).modal('hide');
	});
	
});

$("#accordion2").delegate(".write-question","propertychange change keyup paste input", function(e){//제목 동기화
	var id = $(e.target).attr("id").replace("write-question", "");
	var currentVal=$(e.target).val();
	$("#heading-write" + id + " a").html(currentVal);

}).delegate(".resume-export","click",function(e){//내보내기
	var form=$(e.target).closest("form").serializeJSON();
	//console.log(form.answer);
	var text=form.question+" - "+form.company+"<p>"
	+form.answer;
	Export2Doc(text,form.question);
	return false;

}).delegate("form :submit","click",function(e){
	$(e.target).form()
		.find("input[name=_method]")
		.val($(e.target).val());

}).delegate("form", "submit", function(e) {//저장하기
	var tags = [];
	 $(event.target).find(".tagsinput").find(".tag>span").each(
	function(i, e) {
		tags.push($.trim($(e).text()));
	});
	$(event.target).find(".tags").val(tags.join(","));
	var form = $(e.target).serializeJSON();
	if(form._method=="post")
		form.id="";
	console.log(form);
	$.post("/resume/content/" + form.id, form, function(data) {
		$(e.target).find(".resume-id").val(data.id);
		$(e.target).closest(".card").attr("id","resume-card"+data.id);
		$(e.target).find(".resume-method").val("put");
		refreshList();
	});
	return false;
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
	var currentVal=reviewText().join(' ');
	$target=$("#accordion2 .card").find(".show").find(".write-answer").val(currentVal);
	$("#exampleModal").modal('hide');
	return false;
	
}).delegate(".btn-load","click",function(e){//유사도검사
	//console.log(currentVal.replace(/<p>|<\/p>/g));
$("#compare ul").html("");
	reviewText().forEach( function(e) {
          var $target=$('<div class="spinner-border" role="status">'
        		  +'<span class="sr-only">Loading...</span></div>').appendTo("#compare ul");
		  var sentence=$.trim(e.replace(/\s+/g, " "));
          var form={sentence:sentence};
          $.get("/resume/compare", form, function(data_part) {
        	  $target.attr("class","").html(data_part);
          });
    });
		
	return false;
});
/*
$("#exampleModal").delegate(".btn-check","click",function(e){//맞춤법검사
	return false;
});*/