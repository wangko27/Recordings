$(document).ready(function() {
	$('#navigation #slidebutton').click( function() 
	{
		var leftPos = $('#navigation').position().left;
		
		if( leftPos > 0 )
			$('#navigation').animate({left:"-=10%"});
		else
			$('#navigation').animate({left:"+=10%"});
	} );
	
	
	$('.errorbox').click(function()
	{
		$(this).hide(); 
	}); 
});

function showLoadingMessage( id )
{
	$("#" + id).html('<div>loading...</div>');
}

function scrollToBottom()
{
	$('html, body').animate({ scrollTop: 1050}, 'slow');
}

function scrollToTop()
{
	$('html, body').animate({ scrollTop: 0}, 'slow');
}