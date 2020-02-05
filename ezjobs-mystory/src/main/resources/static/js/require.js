requirejs.config({
	"paths" : {
		"jquery-ui-touch-punch" : [ "/webjars/jquery-ui-touch-punch/jquery.ui.touch-punch.min"],
		"chart"  : ["/webjars/chart.js/dist/Chart.min"],
		"ckeditor":["/webjars/ckeditor/standard"],
		"cke-textwatcher":["/js/ckeditor/plugins/textwatcher"],
		"cke-autocomplete":["/js/ckeditor/plugins/autocomplete"],
		"cke-textmatch":["/js/ckeditor/plugins/textmatch"],
		"cke-wordcount":["/js/ckeditor/plugins/wordcount"],
		"d3":["https://d3js.org/d3.v4"],
	},
	"shim" : {
		"ckeditor/ckeditor":{
			exports : 'CKEDITOR',
		},
		"ckeditor/adapters/jquery":["ckeditor/ckeditor"],
		"cke-textwatcher/plugin":["ckeditor/ckeditor"],
		"cke-autocomplete/plugin":["ckeditor/ckeditor"],
		"cke-textmatch/plugin":["ckeditor/ckeditor"],
		"cke-wordcount/plugin":["ckeditor/ckeditor"],
		"cke-wordcount/lang/ko":["cke-wordcount/plugin"],
		"cke-wordcount/lang/en":["cke-wordcount/plugin"],
	},
});

requirejs.config({
	"paths" : {
		"rsm-editor":["/js/resume-editor"],
	},
	"shim" : {
		"rsm-editor/jQuery.resume":{
			deps:[
				"ckeditor/ckeditor",
				"ckeditor/adapters/jquery",
				"cke-textwatcher/plugin",
				"cke-autocomplete/plugin",
				"cke-textmatch/plugin",
				"cke-wordcount/plugin",
				"cke-wordcount/lang/ko",
				"jquery-ui-touch-punch"
				/*
				,
				,*/
			],
			exports : "RESUME",
		},
		"rsm-editor/list":["rsm-editor/jQuery.resume"],
		"rsm-editor/model":["rsm-editor/jQuery.resume"],
		"rsm-editor/review":["rsm-editor/jQuery.resume"],
		"rsm-editor/view":["rsm-editor/jQuery.resume"],
		"rsm-editor/write":["rsm-editor/jQuery.resume"],
	},
	"packages" : []
});

define("resume-bundle",[
	"rsm-editor/jQuery.resume",
	"rsm-editor/list",
	"rsm-editor/model",
	"rsm-editor/review",
	"rsm-editor/view",
	"rsm-editor/write"
	]
	,function(RESUME){
	return RESUME;
});