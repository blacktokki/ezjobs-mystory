(function(global) {
	
	'use strict'
	function View() {
	}
	
	function init(){
	
		View.prototype.bindEvents={
		};
		
		View.prototype.renderViews={
		};
		
		View.prototype.bind=function(event,handler){
			this.bindEvents[event](this,handler);
		};
		
		View.prototype.render=function(view,data){
			this.renderViews[view](this,data);
		};
	}
	
	global.RESUME=global.RESUME || {};
	global.RESUME.View=View;
	global.RESUME.init=global.RESUME.init || {};
	global.RESUME.init.view=init;
	if(global.RESUME.load) global.RESUME.load();
	
}(this));