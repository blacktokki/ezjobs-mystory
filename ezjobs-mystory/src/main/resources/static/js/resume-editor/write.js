(function(global,$,EDITOR) {
	
	'use strict'
	
	function Write() {
		this.element="#accordion2";
		console.log("create Write");
	}
	
	function init(View){
		Write.prototype=new View();
		Write.prototype.constructor=Write;
		
		
		Write.prototype.bindEvents={
			titleSync: function(view,handler){//제목 동기화
				var events="propertychange change keyup paste input";
				$(view.element).delegate(".write-question",events,function(e){
					var id=$(e.target).attr("id").replace("write-question", "");
					var currentVal=$(e.target).val();
					handler({
						id:$(e.target).attr("id").replace("write-question", ""),
						currentVal:currentVal=$(e.target).val()
					});
				});
			},
			exportResume: function(view,handler){//내보내기
				$(view.element).delegate(".resume-export","click",function(e){
					var form=$(e.target).closest("form").serializeJSON();
					console.log(form);
					var text=form.question+" - "+form.company+"<p>"+form.answer;
					
					handler(text,form.question);
					return false;
				});
			},
			changeMethod: function(view,handler){//저장메소드변경
				$(view.element).delegate("form :submit","click",function(e){
					handler(e.target);
				});
			},
			saveResume: function(view,handler){//저장하기
				$(view.element).delegate("form", "submit", function(e) {
					var tags = [];
					$(e.target).find(".tagsinput").find(".tag>span").each(function(i, element){
						tags.push($.trim($(element).text()));
					});
					$(e.target).find(".tags").val(tags.join(","));
					var form = $(e.target).serializeJSON();
					if(form._method=="post")
						form.id="";
					handler(e.target,form);
					return false;
				});
			},
		};
		
		Write.prototype.renderViews={
			appendResume : function(view,data){
				view.resumeTemplate(data,function(data){
					var resume_idx=data.model.resume_idx;
					$("#nav-profile-tab").tab("show");
					data.$result.appendTo("#accordion2").find(".collapse").collapse("show");
					$("#tags-write" + resume_idx).tagsInput({
						'height' : '100%',
						'width' : '80%',
					});
					var editor=$("#write-answer" + resume_idx).ckeditor().editor;
					var ac=new EDITOR.plugins.autocomplete(editor, ac_config );
				});
			},
			showExistResume : function(view,card){
				$("#nav-profile-tab").tab("show");
				$(card).find(".collapse").collapse("show");
			},
			titleSync :function(view,data){
				$("#heading-write" + data.id + " a").html(data.currentVal);
			},
			changeMethod :function(view,target){
				$(target).form()
					.find("input[name=_method]")
					.val($(target).val());
			},
			saveResume: function(view,data){
				var target=data.target;
				var data=data.data;
				$(target).find(".resume-id").val(data.id);
				$(target).closest(".card").attr("id","resume-card"+data.id);
				$(target).find(".resume-method").val("put");
			},
			applyReview: function(view,data){
				$(view.element+" .card").find(".show").find(".write-answer").val(data);
			}
			
		};
		
		Write.prototype.resumeTemplate=function(data,callback){
			var resume_idx=data.model.resume_idx;
			var resume_new=data.model.resume_new;
			console.log(resume_new);
			console.log(resume_idx);
			var ck_config=data.model.ck_config;
			var $result=$(data.response);
	
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
			if(data.isNew){
				$result.find(".write-question").val("새 자기소개서 " + resume_new);
				$result.find(".card-header a").html("새 자기소개서 " + resume_new);
			}
			data.$result=$result;
			callback(data);
		}
		
		Write.prototype.getCurrentAnswer=function(){
			return $(this.element+" .card").find(".show").find(".write-answer").val();
		}
	}
	
	global.RESUME=global.RESUME || {};
	global.RESUME.Write=Write;
	global.RESUME.init=global.RESUME.init || {};
	global.RESUME.init.write=init;
	if(global.RESUME.load) global.RESUME.load();
	
}(this,jQuery,CKEDITOR));
