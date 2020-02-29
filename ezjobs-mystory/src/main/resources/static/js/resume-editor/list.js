(function(global,$) {
	
	'use strict'
	
	function List() {
		this.element="#accordion1";
		this.resumeLink=".resume-link";//href
		this.searchForm=".search-form";//input[name=page]
		this.pageItem=".page-item a";//data-page
		this.resumeCard="#nav-resume-tab";
		this.deleteForm=".delete-form";
		this.deleteModal=".delete-modal";
		this.pageFormSelect=".page-form select";
	}
	
	function init(View){
		List.prototype=new View();
		List.prototype.constructor=List;
		
		List.prototype.bindEvents={
			loadResume : function(handler){//자기소개서 불러오기
				var resumeCard=this.resumeCard;
				$(this.element).delegate(this.resumeLink,"click",function(e){
					e.preventDefault();
					var href = $(e.target).attr("href");
					var card = href.replace("/resume/write/",resumeCard);
					handler(href,card);
				});
			},
			search : function(handler){//조건검색
				$(this.element).delegate(this.searchForm,"submit",function(e) {//조건검색
					e.preventDefault();
					handler();
				});
			},
			
			changePage : function(handler){//페이지 이동
				var searchForm=this.searchForm;
				$(this.element).delegate(this.pageItem,"click",function(e){//페이지 이동
					e.preventDefault();
					var page=$(e.target).attr("data-page");
					$(searchForm).find("input[name=page]").val(page);
					handler();

				});
			},
			
			deleteResume : function(handler){//자기소개서 삭제
				$(this.element).delegate(this.deleteForm,"submit",function(e){//자기소개서 삭제
					var form=$(e.target).serializeJSON();
					e.preventDefault();
					handler(form);
					return false;
				});
			},
		};
		
		List.prototype.renderViews={
			preRefresh:function(data){
				$(this.element).html(RESUME.loadingTmpl);
			},
			refresh : function(data){
				$(this.element).html(data);
			},
			hideDeleteModal :function(data){
				$(this.deleteModal).modal('hide');
			}
		};
		
		List.prototype.getSearchForm=function(){
			var form=$(this.element).find(this.searchForm).serializeJSON();
			form.size=$(this.element).find(this.pageFormSelect).val();
			return form;
		}
	}

	global.RESUME.List=List;
	global.RESUME.init.list=init;
	global.RESUME.load();
	
}(this,jQuery));
