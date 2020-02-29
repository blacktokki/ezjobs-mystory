(function(global,$,EDITOR) {
	
	'use strict'
	
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
	
	function Write() {
		this.element="#nav-tabContent";
		this.resumeExport=".resume-export";
		this.tagAppend=".tag-append";
		this.tagAppendPrompt=".tag-append-prompt";
		this.tagAppendCustom=".tagsinput .tag>span";
			
		this.writeQuestion="input[name=question]";
		this.writeAnswer="textarea[name=answer]";
		this.resumeId="input[name=id]";
		this.resumeMethod="input[name=_method]";
	}
	
	function init(View){
		Write.prototype=new View();
		Write.prototype.constructor=Write;
		
		
		Write.prototype.bindEvents={
			titleSync: function(handler){//제목 동기화
				$(this.element).delegate(this.writeQuestion,"propertychange change keyup paste input",function(e){
					handler({
						id:$(e.target).closest(".tab-pane").attr("aria-labelledby"),
						currentVal:$(e.target).val()
					});
				});
			},
			exportResume: function(handler){//내보내기
				$(this.element).delegate(this.resumeExport,"click",function(e){
					var form=$(e.target).form().serializeJSON();
					var text=form.question+" - "+form.company+"<p>"+form.answer;
					handler(text,form.question);
					return false;
				});
			},
			changeMethod: function(handler){//저장메소드변경
				$(this.element).delegate("form :submit","click",function(e){
					handler(e.target);
				});
			},
			
			appendTag: function(handler){//태그삽입
				var writeAnswer=this.writeAnswer;
				$(this.element).delegate(this.tagAppend,"click",function(e){
					var data={
						string:$(e.target).text()+":: ",
						editor:$(e.target).form().find(writeAnswer).ckeditor().editor,
					}
					handler(data);
				});
			},
			appendTagPrompt: function(handler){//태그삽입2
				var writeAnswer=this.writeAnswer;
				$(this.element).delegate(this.tagAppendPrompt,"click",function(e){
					var data={
						string:$(e.target).text()+":* ",
						editor:$(e.target).form().find(writeAnswer).ckeditor().editor,
					}
					handler(data);
					
					
				});
			},
			appendTagCustom: function(handler){//태그삽입3
				var writeAnswer=this.writeAnswer;
				var tagConverter=this.tagConverter;
				$(this.element).delegate(this.tagAppendCustom,"click",function(e){
					var text=$(e.target).text();
					var tag=tagConverter(text);
					if (tag[0]=="키워드")
						text=tag[1];
					else
						text=tag[0]+":"+tag[1];
					
					var data={
						string:text,
						editor:$(e.target).closest("form").find(writeAnswer).ckeditor().editor,
					}
					handler(data);
				});
			},
			
			saveResume: function(handler){//저장하기
				var tagAppendCustom=this.tagAppendCustom;
				var tagConverter=this.tagConverter;
				$(this.element).delegate("form", "submit", function(e) {
					e.preventDefault();
					var form = $(e.target).serializeJSON();
					if(form._method=="post")
						form.id="";
					var tags=[];
					$(e.target).find(tagAppendCustom).each(function(i, element){
						var text=$.trim($(element).text());
						var tag=tagConverter(text);	
						tags.push({
							type:tag[0],
							name:tag[1],
						});
					});
					form.tagsStr=JSON.stringify(tags);
					handler(e.target,form);
				});
			},
		};
		
		Write.prototype.renderViews={
			appendResume : function(data){
				var resume_new=data.resume_new;
				var $result=$(data.response);
				$result.find("input[name=closeDate]").datepicker({
				    dateFormat: 'yy-mm-dd'
			  	});
				if(resume_new){
					$result.find("input[name=question]").val("새 자기소개서 " + resume_new);
					$result.find(".nav-item a").html("새 자기소개서 " + resume_new);
				}
				var $head=$result.find(".nav-item");
				var $body=$result.find(".tab-pane");
				$body.appendTo("#nav-tabContent");
				$body.find("input[name=tags]").tagsInput({
					'height' : '100%',
					'width' : '80%',
				});
				var editor=$body.find(this.writeAnswer).ckeditor(ck_config).editor;
				var ac=new EDITOR.plugins.autocomplete(editor, ac_config);
				$head.appendTo("#nav-tab").find("a").tab("show");
			},
			showExistResume : function(target){
				$(target).tab("show");
			},
			titleSync :function(data){
				$("#" + data.id).html(data.currentVal);
			},
			changeMethod :function(target){
				$(target).form()
					.find("input[name=_method]")
					.val($(target).val());
			},
			appendTag:function(data){
				console.log(data);
				var text=data.string;
				//var text="<span style='border-style: solid;border-radius: 10px;border-color:#888888'>"+data.string+'</span>';
				//var newElement = EDITOR.dom.element.createFromHtml(text, data.editor.document );
				//data.editor.insertElement(newElement);
				data.editor.insertHtml(text); 
			},
			saveResume: function(data){
				var target=data.target;
				var data=data.data;
				$(target).find(this.resumeId).val(data.id);
				$(target).find(this.resumeMethod).val("put");
				
				var $body=$(target).closest(".tab-pane");
				$("#"+$body.attr("aria-labelledby"))
					.attr("id","nav-resume-tab"+data.id)
					.attr("href","#nav-resume"+data.id)
					.attr("aria-controls","nav-resume"+data.id);
				$body
					.attr("id","nav-resume"+data.id)
					.attr("aria-labelledby","nav-resume-tab"+data.id);
			},
			applyReview: function(data){
				this.getCurrentAnswer().val(data);
			}
			
		};
		
		Write.prototype.getCurrentAnswer=function(){
			return $(this.element).find(".show").find(this.writeAnswer);
		}
		Write.prototype.tagConverter=function(text){
			var tag=text.split(":");
			if(tag.length>2)
				tag[1]=tag.join('').replace(tag[0],'');
			if (tag.length==1){
				tag[1]=tag[0];
				tag[0]="키워드";
			}
			else if(tag[0]!="문항유형"&&tag[0]!="직무"){
				tag[1]=tag[0]+":"+tag[1];
				tag[0]="키워드";
			}
			return tag;
		}
	}
	
	global.RESUME.Write=Write;
	global.RESUME.init.write=init;
	EDITOR.config.extraPlugins = 'wordcount';
	global.RESUME.load();
	
}(this,jQuery,CKEDITOR));
