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
function matchCallback( text, offset ) {
    // Get the text before the caret.
    var keywords = text.slice( 0, offset ).split(/다 |\.|\n/);
    var keyword=keywords[keywords.length-1];
    if (offset==0)
    	return null;
    return { start: offset-keyword.length, end: offset };
}

ac_config.textTestCallback = textTestCallback;

// Returns (through its callback) the suggestions for the current query.
function dataCallback( matchInfo, callback ) {
	
    var query=matchInfo.query;//.replace(/~/g,"%");
	var form={keyword:query,keywordInclude:""};
	form.keyword=$.trim(form.keyword);
	console.log(form);
	var itemsArray=[];
	$.get("/resume/auto", form, function(data) {
		//console.log(data);
		var $frag=$(document.createDocumentFragment());
		data.list.forEach(function(e,i){
			var front=form.keyword.split("%")[0];
			itemsArray.push({ id: i, name: e.text, front:front, back:e.text.replace(front,"")});
		});
    
	    var suggestions = itemsArray;
	
	    // Note: The callback function can also be executed asynchronously
	    // so dataCallback can do an XHR request or use any other asynchronous API.
	    if(suggestions.length>0)
	    	callback( suggestions );
	});
}

ac_config.dataCallback = dataCallback;

ac_config.itemTemplate = '<li data-id="{id}"><mark><strong>{front}</strong></mark>{back}</li>';
ac_config.outputTemplate = ' {name}';
ac_config.throttle = 300;