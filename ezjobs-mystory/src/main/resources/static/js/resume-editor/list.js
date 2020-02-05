(function(global,$) {
	
	'use strict'
	
	function List() {
		this.element="#accordion1";
	}
	
	function init(View){
		List.prototype=new View();
		List.prototype.constructor=List;
		
		List.prototype.bindEvents={
			loadResume : function(view,handler){//자기소개서 불러오기
				$(view.element).delegate(".resume-link","click",function(e){
					e.preventDefault();
					var href = $(e.target).attr("href");
					var card = href.replace("/resume/write/", "#resume-card");
					handler(href,card);
				});
			},
			search : function(view,handler){//조건검색
				$(view.element).delegate(".search-form","submit",function(e) {//조건검색
					e.preventDefault();
					handler();
				});
			},
			
			changePage : function(view,handler){//페이지 이동
				$(view.element).delegate(".page-item a","click",function(e){//페이지 이동
					var page=$(e.target).attr("data-page");
					$(".search-form").find("input[name=page]").val(page);
					handler();
					return false;
				});
			},
			
			deleteResume : function(view,handler){//자기소개서 삭제
				$(view.element).delegate(".delete-form","submit",function(e){//자기소개서 삭제
					var form=$(e.target).serializeJSON();
					e.preventDefault();
					handler(form);
					return false;
				});
			},
		};
		
		List.prototype.renderViews={
			refresh : function(view,data){
				$(view.element).html(data);
			},
			hideDeleteModal :function(view,data){
				$(".delete-modal").modal('hide');
			}
		};
		
		List.prototype.getSearchForm=function(){
			var form=$(this.element).find(".search-form").serializeJSON();
			form.size=$(this.element).find(".page-form select").val();
			return form;
		}
	}

	global.RESUME.List=List;
	global.RESUME.init.list=init;
	global.RESUME.load();
	
}(this,jQuery));
