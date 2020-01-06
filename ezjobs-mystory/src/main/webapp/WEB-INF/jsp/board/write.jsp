<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<!-- body -->

<!-- 점보트론 스타일 -->
<style>
.jumbotron {
	background-image: url(/image/jumbotronBG.jpg);
	background-size: cover;
	background-position: 0% 40%;
	text-shadow: black -0.4em 0.4em 0.4em;
	color: white;
}
</style>
<div class="container">
	<div class="jumbotron">
		<h1 class="display-4">글쓰기</h1>
		<p class="lead">회원님들의 이야기를 자유롭게 적어주세요.</p>
		<hr class="my-4">
		<p>글쓰는중 ㅎㅎ</p>
	</div>
</div>
<p>
<div class="container">
	<h2>끄적끄적</h2>
	<p>
	<div class="card">
		<form method="post" action="/board/write/${board.id}">
			<input type="hidden" name="_method" value="${method}">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<div class="card-body">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="basic-addon1">글제목</span>
							</div>
							<input type="text" class="form-control" name="title" value="${board.title}">
						</div>
					
						<div class="input-group">
							<div class="input-group-prepend">
								<span class="input-group-text">글내용</span>
							</div>
							<textarea class="form-control" id="form-content" name="text" rows="15">${board.text}</textarea>
							<script type="text/javascript">
							
							//CKEDITOR.plugins.addExternal( 'contextmenu', '/js/ckeditor/plugins/contextmenu/plugin.js' );
							//console.log(CKEDITOR.config.plugins);
							editor =CKEDITOR.replace('form-content'
							 	,{
							 		removePlugins: 'contextmenu,tabletools,tableselection',
									height: 500,
									language:'korean',
									toolbar:[
										{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo','Redo2'] },
										{ name: 'editing', groups: [ 'find', 'selection' ], items: [ 'Find', 'Replace', '-', 'SelectAll'] },
										{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
										{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align'], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
										{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
										{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
										{ name: 'about', items: [ 'About' ] }
									]
							    });
								/*
								console.log(editor);
								editor.addCommand("mySimpleCommand", { // create named command
									exec : function(edt) {
										alert($(edt.getData()).text());
									}
								});
								editor.ui.addButton('Redo2',{ // add new button and bind our command
									label : "Click me",
									command : 'mySimpleCommand',
									icon : 'https://avatars1.githubusercontent.com/u/5500999?v=2&s=16'
								});*/
							</script>
						</div>
						
				<button type="submit" class="btn btn-primary resume-submit">글올리기</button>
			</div>

		</form>
	</div>

</div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>