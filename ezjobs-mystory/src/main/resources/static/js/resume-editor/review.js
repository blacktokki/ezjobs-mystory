(function(global,$) {
	
	'use strict'
	
	function Review() {
		this.element="#review-modal";
		this.wordChange="#word-change";
		this.comapre="#compare";
	}
	
	function init(View){
		Review.prototype=new View();
		Review.prototype.constructor=Review;
		
		Review.prototype.bindEvents={
			startReview: function(view,handler){//검토시작하기
				$("body").delegate(view.element,"shown.bs.modal",function(e){
					handler();
				});
			},
			changeWord:function(view,handler){//단어교체
				$(view.wordChange).delegate("select","change",function(e){//단어교체
					var first=$("option",e.target).first().val();//기본값
					var change=e.target.value;//선택한 값
					var form=$(view.element).find("form").serializeJSON();
					form.isAdd=false;
					form.synonym=change;
					
					if(change=="_add"){
						change=prompt("단어 추가",first);
						//console.log(change);
						if (change!=""&&change!=null){
							form.isAdd=true;
							form.keyword=first;
							form.synonym=change;	
						}
						else{
							change=first;
							form.synonym=change;
						}
					}
					handler(e.target,form);
				});
			},
			applyReview:function(view,handler){//단어교체 적용하기
				$(view.element).delegate(".btn-apply","click",function(e){
					var currentVal=view.reviewText().join(' ');
					handler(currentVal);
					return false;
					
				});
			},
			compare:function(view,handler){//유사도 검사
				$(view.element).delegate(".btn-load","click",function(e){
					$("#compare ul").html("");
					view.reviewText().forEach( function(element) {
				          var $target=$('<div class="spinner-border" role="status">'
				        		  +'<span class="sr-only">Loading...</span></div>').appendTo("#compare ul");
						  var sentence=$.trim(element.replace(/\s+/g, " "));
				          var form={sentence:sentence};
				          handler($target,form);
				    });
				});
			}
		};
		
		Review.prototype.renderViews={
			changeList:function(view,data){
				$(view.wordChange+" ul").html(data)
					.sortable()
					.find("li")
					.each(function(i,element){
						$(element).find("ul").sortable();
				});
			},
			changeWord:function(view,data){
				var form=data.form;
				var target=data.target;
				$(target).find("option[value="+form.synonym+"]",this)
					.attr("selected",true).siblings()
					.removeAttr("selected");
			},
			createWord:function(view,data){
				var form=data.form;
				var target=data.target;
				$("option[value=_add]",target).text(form.synonym).attr("value",form.synonym);
				$("<option value='_add'>추가..</option>").appendTo($(target));
			},
			applyReview:function(view,data){
				$(view.element).modal('hide');
			},
			compare:function(view,data){
				data.$target.attr("class","").html(data.data_part);
			}
		};
		
		Review.prototype.reviewText=function(){
			var currentArray=[];
			var $copy=$(this.wordChange).find("ul .list-sentence").clone();
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
				//console.log(currentArray);
			};
			return currentArray;
		}
	}
	global.RESUME.Review=Review;
	global.RESUME.init.review=init;
	global.RESUME.load();
	
}(this,jQuery));
