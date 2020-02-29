ac_config={}

function textTestCallback( range ) {
    // You do not want to autocomplete a non-empty selection.
    if ( !range.collapsed ) {
        return null;
    }
    // Use the text match plugin which does the tricky job of performing
    // a text search in the DOM. The matchCallback function should return
    // a matching fragment of the text.
    return CKEDITOR.plugins.textMatch.match( range, matchCallback );
}

// Returns the position of the matching text.
// It matches a word starting from the '#' character
// up to the caret position.
var TagMap={
	"문항유형":"types",
	"직무":"depts",
	"시작문장":"isStart",
	"마지막문장":"isEnd",
}

function matchCallback( text, offset ) {
    // Get the text before the caret.
    var keywords = text.slice( 0, offset ).split(/다 |\.|\n/);
    var keyword=keywords[keywords.length-1];
    if (offset==0)
    	return null;
    return { start:offset-keyword.length, end: offset};
}

ac_config.textTestCallback = textTestCallback;

// Returns (through its callback) the suggestions for the current query.
var	timer_auto=null;
function dataCallback( matchInfo, callback ) {
	if(timer_auto){
		clearTimeout(timer_auto);
	}
	timer_auto = setTimeout(function(){
		var form={
				keyword:"",
				types:[],
				depts:[],
				isStart:false,
				isEnd:false,
				searchType:""
			};
		var query=matchInfo.query;//.replace(/~/g,"%");
		var matches=query.match(/((문항유형|직무):.*?|(시작문장|마지막문장)::)\s/g);
		var tagsRaw="";	
		if(matches){
			matches.forEach(function(element,index){
				var unwrap=element.split(":");
				if(unwrap[1]){
					if(unwrap[1]!="*")
						form[TagMap[unwrap[0]]].push($.trim(unwrap[1]).replace(/_/g," "));
				}
				else
					form[TagMap[unwrap[0]]]=true;
				query=query.replace(element,"");
				tagsRaw+=element;
			});
		}
		
		var searchTypeMatch=query.match(/(문항유형|직무):.*/g);
		if(searchTypeMatch){
			var unwrap=searchTypeMatch[0];
			unwrap=unwrap.split(":");
			form.searchType=unwrap[0];
			query=query.replace(unwrap[0]+":","");
		}
		form.types=JSON.stringify(form.types);
		form.depts=JSON.stringify(form.depts);
		form.keyword=$.trim(query);
		//console.log(form);
		
		$.get("/resume/auto", form, function(data) {
			var itemsArray=[];
			//console.log(data);
			var $frag=$(document.createDocumentFragment());
			//var wrapTagsRaw=tagsRaw.replace(/\s/g,",");
			var frontTrim=$.trim(form.keyword.split("%")[0]);
			data.list.forEach(function(e,i){
				if(form.searchType==""){
					var front=frontTrim;
					itemsArray.push({tags:"", id: i, name: e.text, front:front, back:e.text.replace(front,"")});
				}
				else{
					var front= form.searchType+":" +frontTrim;
					var fullname=e.type+":"+e.name;
					itemsArray.push({tags:"", id: i, name: tagsRaw+fullname.replace(/\s/g,"_")+" ", front:front, back:fullname.replace(front,"")});
				}
			});
			
		    var suggestions = itemsArray;
		    // Note: The callback function can also be executed asynchronously
		    // so dataCallback can do an XHR request or use any other asynchronous API.
		    //if(suggestions.length>0)
		    try{
		    	callback( suggestions );
		    }
		    catch(e){
		    }
		});
	},100);
}
ac_config.dataCallback = dataCallback;

ac_config.itemTemplate = '<li data-id="{id}">{tags}    <mark><strong>{front}</strong></mark>{back}</li>';
ac_config.outputTemplate = ' {name}';
ac_config.throttle = 100;