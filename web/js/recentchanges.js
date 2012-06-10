

$(document).ready(function()
{
	$('#changelist').load('recentchanges .changeitem',function()
	{			
		$('#changelist').serialScroll({
		
			force: true,
			axis: 'y',
			items:'.changeitem',
			interval: 4000,
			easing:'linear',
			lazy: true,
			step:1,
			start:0,
			cycle:true,
			jump: true,
			event: 'click',
			constant:false
		}); 
		
		$('.changeitem').live('click',function()
		{
			window.location = $('.link', $(this)).text(); 
		});
		
		$('.changeitem').live('hover', function()
		{
			// set status message here
		}); 
	}); 
}); 