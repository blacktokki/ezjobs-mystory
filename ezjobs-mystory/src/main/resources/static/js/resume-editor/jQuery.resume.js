(function(global,$) {
	'use strict'

	function View() {
	}
	
	View.prototype.bindEvents={
	};
	
	View.prototype.renderViews={
	};
	
	View.prototype.bindHandler=function(event,handler){
		this.bindEvents[event].bind(this)(handler);
		return this;
	};
	
	View.prototype.render=function(view,data){
		this.renderViews[view].bind(this)(data);
	};
	
	View.prototype.bindRender=function(event,view){
		var self=this;
		this.bindHandler(event,function(data){
			self.render(view,data);
		});
		return this;
	}
	
	function App(list,write,review) {
		this.list=list;
		this.write=write;
		this.review=review;
		this.resume_idx = 0;
		this.resume_new = 0;
		
		this.list.bindHandler("loadResume",this.getResume)
			.bindHandler("search",this.getList)
			.bindHandler("changePage",this.getList)
			.bindHandler("deleteResume",this.deleteResume);
		
		this.write.bindRender("titleSync","titleSync")
			.bindHandler("exportResume",RESUME.export2Doc)
			.bindRender("changeMethod","changeMethod")
			.bindRender("appendTag","appendTag")
			.bindRender("appendTagPrompt","appendTag")
			.bindRender("appendTagCustom","appendTag")
			.bindHandler("saveResume",this.postResume);
		
		this.review.bindHandler("startReview",this.reviewResume)
			.bindHandler("changeWord",this.changeWord)
			.bindHandler("applyReview",this.applyReview)
			.bindHandler("compare",this.compare);
	};
	
	App.prototype.getList=function(){
		var form=self().list.getSearchForm();
		self().list.render("preRefresh",form);
		$.get("/resume/content",form,function(data){
			self().list.render("refresh",data);
		});
	}
	App.prototype.createResume=function(){
		self().resume_idx += 1;
		self().resume_new += 1;
		$(document.createDocumentFragment()).load("/resume/write",function(response){
			var data={
				response : response,
				model :self(),
				resume_idx : self().resume_idx,
				resume_new : self().resume_new,
			};
			self().write.render("appendResume",data);
		});
	}
	App.prototype.getResume=function(href,target){
		if ($(target).length == 0){
			self().resume_idx += 1;
			$(document.createDocumentFragment()).load(href,function(response){
				var data={
					response : response,
					resume_idx : self().resume_idx,
				};
				self().write.render("appendResume",data);
			});
		}
		else
			self().write.render("showExistResume",target);
	}
	App.prototype.deleteResume=function(form){
		$.post("/resume/content",form,function(data){
			self().list.render("hideDeleteModal",data);
			self().getList();
		});
	}
	App.prototype.postResume=function(target,form){
		console.log(form);
		$.post("/resume/content/" + form.id, form, function(data) {
			self().getList();
			self().write.render("saveResume",{
				target:target,
				data:data,
			});
		});
	}
	App.prototype.reviewResume=function(){
		$.get("/resume/changelist",self().write.getCurrentAnswer(),function(data){
			self().review.render("changeList",data);
		});
	}
	App.prototype.changeWord=function(target,form){
		var data={
			target:target,
			form:form,
		};
		if (form.isAdd){
			$.post("/resume/synonym/", form);
			self().review.render("createWord",data);
		};
		self().review.render("changeWord",data);
	}
	App.prototype.applyReview=function(data){
		self().review.render("applyReview",data);
		self().write.render("applyReview",data);
	}
	App.prototype.compare=function($target,form){
		$.get("/resume/compare", form,function(data_part) {
			var data={
				$target:$target,
				data_part:data_part,
			}
			self().review.render("compare",data);
		});
	}
	
	function self(){
		return RESUME.app;
	}
	
	global.RESUME={};
	global.RESUME.init={};
	global.RESUME.loadingTmpl='<div><div class="spinner-border" role="status">'
		  +'<span class="sr-only">Loading...</span></div>데이터를 불러오는 중입니다</div>'
	global.RESUME.export2Doc=function(content, filename){
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
	global.RESUME.load=function(){
		if (
			RESUME.List &&
			RESUME.Write &&
			RESUME.Review &&
			!RESUME.app
		){
			RESUME.init.list(View);
			RESUME.init.write(View);
			RESUME.init.review(View);
			
			var list=new RESUME.List();
			var write=new RESUME.Write();
			var review=new RESUME.Review();
			RESUME.app=new App(list,write,review);
			
		}
	}
}(this,jQuery));