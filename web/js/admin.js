$(document).ready(function()
{
	function authenticate(userValue)
	{		 	
		$('#admin .progress').show();
		
		$.post("authenticate","password=" + userValue, function(data, textStatus, jqXHR)
		{	        
	        	if(data.message == 'success')
        		{
	        		$('#admin #login').hide(); 
	        		$('#admin #status').show(); 
	        		refresh(); 
        		}
	        	else
        		{
	        		$('#admin #error').text("Sorry, we couldn't log you in"); 
	        		$('#admin #error').show(); 
	        		$('#admin .progress').hide();
        		}	        		      
		}, "json"); 
	}
	
	function logout()
	{
		$('#admin .progress').show(); 
		
		$.post("authenticate", "logout=true", function(data, textStatus, jqXHR)
		{
			if(textStatus == 'success')
    		{
        		$('#admin #login').show(); 
        		$('#admin #status').hide(); 
        		refresh(); 
    		}
        	else
    		{
        		$('#admin #error').text("Sorry, we couldn't log you out"); 
        		$('#admin #error').show(); 
        		$('#admin .progress').hide();
    		}
			
		}, "json");	
	}
	
	function refresh()
	{		
		$('#admin').children().not('.progress').hide(); 
		window.location.reload(); 
	}
	
	
	$('#admin #authenticate').click(function()
	{
		$('#admin #error').hide(); 
		authenticate( $('#admin #password').val()); 
	}); 
	
	$('#admin #logout').click(function()
	{
		$('#admin #error').hide(); 
		logout(); 		
	}); 
	
	$('#admin .admintoggle').click(function() 
	{ 
		var hideableChildren =$('#admin #login'); 

		$(hideableChildren).slideToggle(); 
		$('#admin .error').hide(); 
	}); 
}); 