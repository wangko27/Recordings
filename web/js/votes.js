function Voter(votescontrollerurl)
{
	this.url = votescontrollerurl;
}

Voter.prototype.updateVoteDisplay = function(votes)
{		
	var totalVoteSection =$('.votes span');  
    totalVoteSection.text(votes);
	
}; 

Voter.prototype.makeVote =  function (voteType, id, processingDiv, upvotes, downvotes)
{
	urlString = "vote=" + voteType + "&id=" + id; 
	voteUrl = this.url; 
	processingDiv.show(); 
	upvotes.hide(); 
	downvotes.hide(); 
	
	 $.ajax(
	 { 
		type: 'POST', 
        url: voteUrl,        
        data: urlString, 
        dataType:'json',		       
        
        complete:function (xhr, status)
        {
        	processingDiv.hide();
        	upvotes.show(); 
        	downvotes.show(); 
        },
        
        // data was not received: show transmit error 
        error:function (xhr, ajaxOptions, thrownError)
        {		   	        	
        	$('#errorpopup').show(); 
        	$('#errorpopup').offset( { top: $('.votes').offset().top, left: $('.votes').offset().left}); 
        	
        	$('#errorpopup #message').text("There was a problem contacting the server. Make sure you are connected to the internet."); 
        	return; 
        },
        	                
        // data was transmitted: show validation error or display comment
        success: function(data)
        {   
        	if(data.message)
    		{
        		$('#errorpopup').show(); 
        		$('#errorpopup').offset( { top: $('.votes').offset().top, left: $('.votes').offset().left});  
        		$('#errorpopup #message').text(data.message);
        		return; 
    		}
        	else
    		{
        		Voter.prototype.updateVoteDisplay(data.votes); 
        		return; 
    		}        	
        }
    });
}; 

Voter.prototype.initializeDefaultUI = function()
{
	makeVote = this.makeVote; 
	url = this.url; 
	
	var upvotes = $('.votes img:first'); 
	var downvotes = $('.votes img:last');
	
	$(upvotes).click(function()		
	{	
		var voteType = "up"; 
		var id = $('#id').text(); 
		
		$('.errorbox').hide(); 
		makeVote(voteType, id, $('.votes div'), upvotes, downvotes); 
	}); 
	
	$(downvotes).click(function()
	{		
		var voteType = "down"; 
		var id = $('#id').text(); 
		
		$('.errorbox').hide(); 
		makeVote(voteType, id, $('.votes div'), upvotes, downvotes); 
	}); 
}; 
