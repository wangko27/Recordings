$.ajaxSetup({
   async:false
});

var MAXINT = 9999;
$(document).ready(function() 
{
	// increment and decrement track listing buttons
	$("input[name='up']").live('click', function() {
		incrementTrackListing(this);
	});
	$("input[name='down']").live('click', function() {
		decrementTrackListing(this);
	});
	
	// re-order track listing action
	$("input[name='reorder']").click(function() {
		reorderTrackListings();
	});
	
	$("input[name='scrollToTop']").click( function() {
		scrollToTop();
	});
	
	$("input[name='scrollToBottom']").click( function() {
		scrollToBottom();
	});
	
	// set the track listing for the add-song section
	setDefaultTrackListingForAddSongInstance();
});

function setDefaultTrackListingForAddSongInstance()
{
	$("input[name='trackListing']","tr#add").val(getHighestTrackListing());
}

function reorderTrackListings()
{
	var count = 0;
	$("input[name='trackListing']").each( function() {
		var nextLowest = getNextLowestTrackListing(count);
		if( nextLowest != (count+1) )
			setTrackListing(nextLowest, (count+1));
		
		count++;
	});
}

function setTrackListing( oldTrackListing, newTrackListing )
{
	$("input[name='trackListing']").each( function() {
		var trackListing = $(this).val();
		
		if( trackListing == oldTrackListing )
			$(this).val(newTrackListing);
	});
}

function getHighestTrackListing()
{
	var highestTrackListing = 1;
	$("input[name='trackListing']").each( function() {
		var containingTR = $(this).parent().parent();
		var rowId = $(containingTR).attr('id');
		if( rowId != '' )
		{
			var trackListing = $(this).val();
			
			if( trackListing >= highestTrackListing )
				highestTrackListing = (trackListing*1 + 1);
		}
	});
	
	return highestTrackListing;
}

function getNextLowestTrackListing( min )
{
	var nextLowest = MAXINT;
	$("input[name='trackListing']").each( function() {
		var containingTR = $(this).parent().parent();
		var rowId = $(containingTR).attr('id');
		if( rowId != 'add' )
		{
			var trackListing = $(this).val();
			
			if( parseInt(trackListing) > min && parseInt(trackListing) < nextLowest )
				nextLowest = trackListing;
		}
	});
	
	return nextLowest;
}

function validateTrackListings()
{
	var valid = true;
	
	// make sure that there are no blank track listings
	$("input[name='trackListing']").each( function() {
		var trackListing = $(this).val();
		var songId = $("select[name='songId']",$(this).parent().parent()).val();
		if( songId != '' && (trackListing == null || trackListing == '') )
		{
			alert("Can't have a blank tracklisting");
			valid = false;
			return false; // break out of .each loop
		}
	});
	
	// make sure that there are no repeat track listings
	var previousValues = new Array();
	$("input[name='trackListing']").each( function() {
		var containingTR = $(this).parent().parent();
		var rowId = $(containingTR).attr('id');
		if( rowId != 'add' && rowId != '' )
		{
			var trackListing = $(this).val();
			//alert(trackListing + ' ' + $.inArray(trackListing, previousValues));
			if( $.inArray(trackListing, previousValues) != -1 )
			{
				alert("Can't have repeat tracklistings");
				valid = false;
				return false; // break out of .each loop
			}
			
			previousValues.push(trackListing);
		}
	});
	
	return valid;
}

function incrementTrackListing( context )
{
	var trackListing = $("input[name='trackListing']", $(context).parent()).val();
	var newValue = (trackListing*1 + 1);
	
	if( newValue < 1 )
		return;
	
	$("input[name='trackListing']", $(context).parent()).val(newValue);
}

function decrementTrackListing( context )
{
	var trackListing = $("input[name='trackListing']", $(context).parent()).val();
	var newValue = (trackListing - 1);
	
	if( newValue < 1 )
		return;
	
	$("input[name='trackListing']", $(context).parent()).val(newValue);
}