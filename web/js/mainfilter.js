/*
 * File: mainfilter.js
 * All behavior that is needed for events related to the main filter
 * such as:
 *    - tab click events
 *    - search button events within each tab section
 *    - clear button events within each tab section
 */
$(document).ready(function() 
{
	// search button events (for recordings and songs tabs)
	$("span:contains('Search')", "a.ovalbutton").click(function() {
		if( $("#recordingtab").is(":visible") )
			handleRecordingSearch( (new RecordingFilter()).fill(), 'search' );
		else if( $("#songtab").is(":visible") )
			handleSongSearch( (new SongFilter()).fill(), 'search' );
	});
	
	// every key press for quick search section
	$("input[name='quicksearch']").keyup(function() {
		var quickSearchValue = $(this).val();
		handleQuickSearch(quickSearchValue, 'search');
	});
	
	// clear button on quick search screen
	$("input[name='clearquicksearch']").click(function() {
		$(this).prev().val('');
		handleQuickSearch('', 'search');
		$(this).prev().focus();
	});
	
	// clear form events
	$(".filterform div:contains('Clear')").click(function() {
		clearForm($(this).parent());
	});

	/* Events that occur when the tabs are pressed */
	// recordings tab
	$("span:contains('Recordings')", "ul#maintablist").click( function() {
		handleRecordingSearch( (new RecordingFilter()).fill(), 'tab' );
	});
	// songs tab
	$("span:contains('Songs')", "ul#maintablist").click( function() {
		handleSongSearch( (new SongFilter()).fill(), 'tab' );
	});
	// quick search tab
	$("span:contains('Search')", "ul#maintablist").click( function() {
		var quickSearchValue = $(this).prev().val();
		handleQuickSearch(quickSearchValue, 'tab');
	});
	
	// handle showing/hiding and styling the tabs section when a tab is clicked
	$("#maintablist span").click( function() {
		handleTabVisibility($(this));
	});
} );

/*
 * All actions that occur upon the recording tab being clicked
 */
function handleRecordingSearch( recordingFilters, sourceEvent )
{
	showRecordingTableLoadingImage('mainrecordingtable');
	
	$('#mainrecordingtable').load('recordingTable', {
		year: recordingFilters.year,
		month: recordingFilters.month,
		date: recordingFilters.date,
		country: recordingFilters.country,
		city: recordingFilters.city,
		venue: recordingFilters.venue,
		recordingType: recordingFilters.recordingType,
		quality: recordingFilters.quality,
		sourceEvent: sourceEvent
	}, function(responseText, textStatus, XMLHttpRequest) {
		// textStatus will be either 'success' or 'error'
		if( textStatus == 'error' )
			$('#mainrecordingtable').html('<div>Problem connecting to server...check internet connection</div>');
		else
			$("#recordingTable").tablesorter();
	});
}

/*
 * All actions that occur upon the song tab being clicked
 */
function handleSongSearch( songFilters, sourceEvent )
{
	showRecordingTableLoadingImage('mainrecordingtable');
	
	var pref = songFilters.pref;
	if( sourceEvent == 'search' )
		pref = 'val';
	
	$('#mainrecordingtable').load('smartSong', {
		 firstAppeared: songFilters.firstAppeared,
		 lastAppeared: songFilters.lastAppeared,
		 howCommon: songFilters.howCommon,
		 album1: songFilters.album1,
		 sourceEvent: sourceEvent,
		 pref: pref
	}, function(responseText, textStatus, XMLHttpRequest) {
		// textStatus will be either 'success' or 'error'
		if( textStatus == 'error' )
			$('#mainrecordingtable').html('<div>Problem connecting to server...check internet connection</div>');
		else
			$("#songTable").tablesorter();
	});
}

/*
 * All actions that occur upon the quick search tab being clicked
 */
function handleQuickSearch( quickSearchValue, sourceEvent )
{
	showRecordingTableLoadingImage('mainrecordingtable');
	
	if( quickSearchValue == null )
		quickSearchValue='';
	
	$('#mainrecordingtable').load('quickSearch', {
		quickSearchValue : quickSearchValue,
		sourceEvent : sourceEvent
	}, function(responseText, textStatus, XMLHttpRequest) {
		// textStatus will be either 'success' or 'error'
		if( textStatus == 'error' )
			$('#mainrecordingtable').html('<div>Problem connecting to server...check internet connection</div>');
		else
			$("#quickSearchTable").tablesorter();
	});
}

// show the loading message
function showLoadingMessage( id ) {
	$("#" + id).html('<div>loading...</div>');
}

function showRecordingTableLoadingImage( id ) {
	$("#" + id).html('<table class="display" style="color:gray"><tr><td>loading...</td></tr></table>');
}

// clear all select elements within a passed-in container element (like a div)
function clearForm( context ) 
{
	$("select", context).each(function() {
		$(this).attr('selectedIndex', 0);
	});
}

/*
 * Get the context of the tab with the passed-in text
 * and call the function that handles the tab-click events
 */
function setDefaultTab( defaultTabText )
{
	var tabContext = $("span.spanlink:contains('" + defaultTabText + "')");
	
	// load the correct main table
	if( defaultTabText == 'Recordings' )
		handleRecordingSearch( (new RecordingFilter()).fill(), 'search' );
	else if( defaultTabText == 'Songs' )
		handleSongSearch( (new SongFilter()).fill(), 'search' );
	else if( defaultTabText == 'Search' )
		handleQuickSearch('', 'search');
	
	handleTabVisibility(tabContext);
}

/*
 * Handle swapping out the right tab sections
 * and highlighting the selected tab
 */
function handleTabVisibility( context )
{
	// the index of the tab selected when the tabs are returned as an array
	// this makes it easy to relate the tab buttons to the tab sections
	var tabNumber = getTabNumber($(context).text());
	
	// iterate through each tab button (really a span)
	// and either remove or add the selected class
	var count = 0;
	$("span.spanlink").each( function() {
		if( count++ != tabNumber )
			$(this).removeClass("selected");
		else
			$(this).addClass("selected");
	});
	
	// iterate through all the tab sections and only show 
	// the tab that has been clicked on
	var count = 0;
	$("div.common_panel","div.mainfilter").each( function() {
		if( count++ != tabNumber )
			$(this).hide();
		else
			$(this).show();
	});
}

/*
 * The index of the tab selected when the tabs are returned as an array
 */
function getTabNumber( tabText )
{
	var tabNumber = 0;
	
	var count = 0;
	$("span.spanlink").each( function() {
		var text = $(this).text();
		
		if( tabText == text )
		{
			tabNumber = count; 
			return;
		}
		
		count++;
	});
	
	return tabNumber;
}

/*
 * Class: SongFilter
 * Represents the song filter fields
 */
function SongFilter() 
{
	this.firstAppeared = "";
	this.lastAppeared = "";
	this.howCommon = "";
	this.album1 = "";
	this.pref= "";
	
	this.fill = function() {
		this.firstAppeared = $("select[name='firstAppeared'] option:selected").val();
		this.lastAppeared = $("select[name='lastAppeared'] option:selected").val();
		this.howCommon = $("select[name='howCommon'] option:selected").val();
		this.album1 = $("select[name='album1'] option:selected").val();
		this.pref = $("input[name='pref']").val();
		
		return this;
	};
}

/*
 * Class: RecordingFilter
 * Represents the recording filter fields
 */
function RecordingFilter() 
{
	this.year = "";
	this.month = "";
	this.date = "";
	this.country = "";
	this.city = "";
	this.venue = "";
	this.recordingType = "";
	this.quality = "";
	
	this.fill = function() {
		this.year = $("select[name='year'] option:selected").val();
		this.month = $("select[name='month'] option:selected").val();
		this.date = $("select[name='date'] option:selected").val();
		this.country = $("select[name='country'] option:selected").val();
		this.city = $("select[name='city'] option:selected").val();
		this.venue = $("select[name='venue'] option:selected").val();
		this.recordingType = $("select[name='recordingType'] option:selected").val();
		this.quality = $("select[name='quality'] option:selected").val();
		
		return this;
	};
}
