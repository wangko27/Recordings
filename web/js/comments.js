$(document).ready(function()
{  
	$("#commentContent input[value=delete]").live('click', function() {
		var id = $(this).attr("id");
		
		$.post($('#postcomment #commentSource').val() + 'comments', {
			commentId: id,
			deletecomment: true
		}, function(data) { 
			reloadCommentSection($('#comments #commentContent'), null); 
		});
	});
	
	$("${input[value='edit']}").live("click", function() 
	{
		var id = $(this).attr("id");
		var commentTextDiv = $("div." + id);
		
		// if there exists a textbox with a class of editcommenttextbox then 
		// the comment box is already in edit mode, so revert back to just showing the text
		// otherwise change to edit mode
		var editcommenttextbox = $("input[class=editcommenttextbox]",$(this).parent("div"));
		// check existance by length (http://stackoverflow.com/questions/31044/is-there-an-exists-function-for-jquery)
		if( editcommenttextbox.length <= 0 )
		{
			var text = $(commentTextDiv).text();
			
			// get the text in the comment div
			if( text != null )
				text = text.trim();
			else
				text = "";
			
			// clear the comment div and replace it with the textbox and save button
			$(commentTextDiv).empty();
			$(commentTextDiv).append('<input type="textbox" class="editcommenttextbox" value="" />');
			$("input[type=textbox]",commentTextDiv).val(text);
			$(commentTextDiv).after('<input type="button" value="save" id="' + id + '" />');
		}
		else
		{
			var text = $(editcommenttextbox).val();
			
			// get the text in the comment div
			if( text != null )
				text = text.trim();
			else
				text = "";
			
			$(commentTextDiv).empty();
			$(commentTextDiv).text(text);
			$("input[value=save]", $(this).parent("div")).hide();
		}
	});
	
	$("#commentContent input[value='save']").live('click', function() {
		var id = $(this).attr("id");
		var text = $("input[type=textbox]",$(this).prev("div.commentText")).val();
		
		$.post($('#postcomment #commentSource').val() + 'comments', {
			commentId: id,
			text: text,
			editcomment: true
		}, function(data) { 
			reloadCommentSection($('#comments #commentContent'), null); 
		});
	});

	/**
	 * prepares a data string for submission to the comments controller
	 * based on the form input values. 
	 */
	 function prepareCommentDataString()
	 {		
		 var commentString = "postcomment=true" + "&source=" + $('#postcomment #commentSource').val() + "&id=" + $('#commentSourceId').val() ;
		 commentString += "&user=" + $('#postcomment #inusername').val(); 
		 commentString += "&text=" + $('#postcomment #intext').val();
		 commentString += "&validation=" + $('#postcomment #securityanswer').text(); 
		 
		 return commentString; 
	 }
	 
	 function prepareUpvoteDataString(commentid)
	 {
		 var commentString = "votecomment=true" + "&commentid=" + commentid;
		 return commentString; 
	 }
	
	 function upvoteComment(url, dataString, commentid, previousVoteCount, processing)
	 {
		 processing.show(); 
		 
		 $.ajax({ 
		        url: url,
		        data: dataString, 
		        dataType:'json',		       
		        
		        complete:function(xhr, jq)
		        {
		        	processing.hide(); 
		        },
		        
		        // data was not received: show transmit error 
		        error:function (xhr, ajaxOptions, thrownError)
		        {		   
		        	var errorBox = '#comment_' + (commentid) + ' .error'; 
		        	$(errorBox).show(); 
        			$(errorBox).text(' there was a problem with the connection (click to close)'); 
		        	return; 
		        },
		        	        
		        // data was transmitted: show validation error or display comment
		        success: function(dataResult)
		        {   
		        	if(dataResult)
	        		{
		        		if(typeof dataResult.voteRank != 'undefined' && typeof dataResult.voteRank != 'Undefined')
		        		{
		        			var newCommentId = '#comment_' + (commentid); 
		        			var commentVotesBox =  newCommentId + ' .commentVotes'; 	
		        			$(commentVotesBox).html( (dataResult.voteRank) +  '<input type="hidden" id="commentid" value=' + commentid +  '><div>Processing</div>'); 
		        		}
		        		else
		        		{	
		        			var newCommentId = '#comment_' + (commentid); 
		        			var commentVotesBox =  newCommentId + ' .commentVotes';
		        			var errorBox = '#comment_' + (commentid) + ' .error'; 
		        			$(errorBox).show(); 
		        			$(errorBox).text(dataResult.message + ' (click to close)'); 
		        		}
	        		}
		        }
		    });
	 }
	 
	 /**
	  * actually makes an AJAX request to CommentController and handles the results
	  * @param url the url of the comment controller
	  * @param dataString the string of parameters/values to send
	  */
	 function makeCommentPost(url, dataString)
	 {	
		 $.ajax({ 
		        url: url,
		        data: dataString, 
		        dataType:'json',		       
		        
		        // data was not received: show transmit error 
		        error:function (xhr, ajaxOptions, thrownError)
		        {
			        // renable submit button 
			        $('#postcomment #makenewpost').attr('disabled', false);
		        	errorMessage("There was a problem accessing the server, please try again and make sure you have a valid internet connection " + thrownError); 
		        	return; 
		        },
		        	        
		        // data was transmitted: show validation error or display comment
		        success: function(data)
		        {          
			        // renable submit button 
			        $('#postcomment #makenewpost').attr('disabled', false);
			        
			        // validation error 
			        if(data.message)
		        	{
			        	errorMessage(data.message); 
			        	return; 
		        	}
			        
			        // success 
		        	displayCommentPostResults(data); 
		        	clearCommentForm(); 
		        }
		    });
	 }
	 
	 /**
	  * displays an error message 
	  * @param message the message to display 
	  */
	 function errorMessage(message)
	 {
		 $('#commentsposting .error #errormessage').text(message); 
		 $('#commentsposting .error').show(); 
	 }
	 
	 /**
	  * displays a recently posted comment as a new comment div 
	  * right after the last bob comment
	  * @param data the JSON object representing the BasicCommentBean 
	  */
	 function displayCommentPostResults(data)
	 { 
		 var commentContentDiv = $('#comments #commentContent'); 		 

		 reloadCommentSection(commentContentDiv, data); 
		 
	 }
	 
	 function reloadCommentSection(commentContentDiv, data)
	 {
		 commentContentDiv.empty(); 
		 
		 var requestUrl = $('#comments #commentControllerUrl').val(); 
		 
		 commentContentDiv.load(requestUrl +  ' #comments #commentContent', function()
		 {
			 if(data != null) 
			 {
				 // fade into view
				 var newCommentDiv = $('#comment_' + data.id); 
				 newCommentDiv.css({ opacity: 0.0 }).animate({opacity: 1.0}, 1000);
				 window.location = "#comment_" + data.id;
			 }
		 });
	 }
	 
	 /**
	  * clears the values that the user has entered in the posting comment section
	  */
	 function clearCommentForm()
	 {		
		 $('#postcomment #inusername').val(""); 
		 $('#postcomment #intext').val("");		
		 $('#postcomment #securityanswer').val(""); 
	 }
	 
	 /**
	  * called when the user clicks the "post" button 
	  */
	 $('#postcomment #makenewpost').click(function()
	 {
		 // hide error message
		 $('#commentsposting .error').hide(); 
		 // disable submit option
		 $(this).attr('disabled', true);
		 
		 var dataString = prepareCommentDataString(); 
		 var url = $('#postcomment #commentSource').val() + 'comments'; 

		 makeCommentPost(url, dataString); 		 
	 }); 
	 
	 $('.comment .commentVotes').live('click', function()
	 {
	     var commentid =     $(this).find(':input').val();
	     var dataString = prepareUpvoteDataString(commentid); 
	     var url = $('#postcomment #commentSource').val() + 'comments';
	     var previousVoteCount = parseInt($(this).text());
	     $(this).find('.error').empty(); 
	     $('.comment .error').hide(); 
	     
	     upvoteComment(url, dataString, parseInt(commentid, 10), previousVoteCount, $(this).find('div')); 	     
	 }); 
	 
	 $('.comment .error').live('click', function()
	 {
		  $(this).slideToggle(); 
	 });
	 
});
