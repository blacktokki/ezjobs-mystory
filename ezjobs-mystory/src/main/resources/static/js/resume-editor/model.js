(function(global,$) {
	
	'use strict'
	function Model() {
		this.resume_idx = 0;
		this.resume_new = 0;
		this.ck_config={
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
		console.log("create Model");
	}
	
	function init(){
		Model.prototype.list=function(form,callback){
			$.get("/resume/content",form, function(data) {
				callback(data);
			});
		}
		Model.prototype.create=function(callback){
			this.resume_idx += 1;
			this.resume_new += 1;
			var model=this;
			//console.log(model);
			$(document.createDocumentFragment())
				.load("/resume/write",function(response){
				callback({
					response : response,
					model :model,
					isNew : true,
				});
			});
		}
		Model.prototype.load=function(href,callback){
			this.resume_idx += 1;
			var model=this;
			$(document.createDocumentFragment())
				.load(href,function(response){
				callback({
					response : response,
					model :model,
					isNew : false,
				});
			});
		}
		Model.prototype.remove=function(form,callback){
			$.post("/resume/content",form,function(data){
				callback(data);
			});
		}
		Model.prototype.save=function(form,callback){
			$.post("/resume/content/" + form.id, form, function(data) {
				callback(data);
			});
		}
		Model.prototype.review=function(form,callback){
			$.get("/resume/changelist",form, function(data) {
				callback(data);
			});
		}
		
		Model.prototype.addWord=function(form){
			$.post("/resume/synonym/", form);
		}
		
		Model.prototype.compare=function(form,callback){
			$.get("/resume/compare", form, function(data){
				callback(data);
			});
		}

	}
	global.RESUME=global.RESUME || {};
	global.RESUME.Model=Model;
	global.RESUME.init=global.RESUME.init || {};
	global.RESUME.init.model=init;
	if(global.RESUME.load) global.RESUME.load();
	
}(this,jQuery));