(function(global,$,EDITOR) {
	'use strict'

	var list,write,review,model;
	var root="/js/resume-editor/";
	
	function refreshList(callback){
		var form=list.getSearchForm();
		model.list(form,function(data){
			if(callback)
				callback();
			list.render("refresh",data);
		});
		
	}
	
	function createResume(){
		model.create(function(data){
			write.render("appendResume",data);
		});
	}
	
	function loadResume(href,target){
		if ($(target).length == 0){
			model.load(href,function(data){
				write.render("appendResume",data);
			});
		}
		else{
			write.render("showExistResume",target);
		}
	}
	function deleteResume(form){
		model.remove(form,function(data){
			refreshList(function(){
				list.render("hideDeleteModal",data);
			});
		});
	}
	
	function export2Doc(content, filename){
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
	
	function saveResume(target,form){
		model.save(form,function(data){
			console.log(data);
			refreshList();
			write.render("saveResume",{
				target:target,
				data:data,
			});
		});
	}
	
	function reviewResume(){
		var form={
			answer:write.getCurrentAnswer(),
		};
		model.review(form,function(data){
			review.render("changeList",data);
		});
	}
	
	function changeWord(target,form){
		var data={
			target:target,
			form:form,
		};
		if (form.isAdd){
			model.addWord(form);
			review.render("createWord",data);
		};
		review.render("changeWord",data);
	}
	
	function compare($target,form){
		
		model.compare(form, function(data_part) {
			review.render("compare",{
				$target:$target,
				data_part:data_part,
			});
			
        });
	}
	
	function load(){
		if (RESUME.View &&
			RESUME.List &&
			RESUME.Write &&
			RESUME.Review &&
			RESUME.Model
		){
			RESUME.init.view();
			RESUME.init.list(RESUME.View);
			RESUME.init.write(RESUME.View);
			RESUME.init.review(RESUME.View);
			RESUME.init.model();
			
			list=new RESUME.List();
			write=new RESUME.Write();
			review=new RESUME.Review();
			model=new RESUME.Model();
			
			list.bind("loadResume",loadResume);
			list.bind("search",refreshList);
			list.bind("changePage",refreshList);
			list.bind("deleteResume",deleteResume);
			write.bind("titleSync",function(data){
				write.render("titleSync",data);
			});
			write.bind("exportResume",export2Doc);
			write.bind("changeMethod",function(target){
				write.render("changeMethod",target);
			});
			write.bind("saveResume",saveResume);
			review.bind("startReview",reviewResume);
			review.bind("changeWord",changeWord);
			review.bind("applyReview",function(data){
				write.render("applyReview",data);
				review.render("applyReview",data);
			});
			review.bind("compare",compare);
		}
	}
	
	EDITOR.config.extraPlugins = 'wordcount';
	global.RESUME=global.RESUME || {};
	global.RESUME.init=global.RESUME.init || {};
	global.RESUME.load=load;
	global.RESUME.controller={
		refreshList:refreshList,
		createResume:createResume,
	};
	
}(this,jQuery,CKEDITOR));