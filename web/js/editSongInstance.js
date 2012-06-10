$(document).ready(function() 
{
	// save button action
	$("input[name='saveSongInstances']").live('click', function() {
		saveAllSongInstances();
	});
	
	// delete button action
	$("input[value='x']").live('click', function() {
		if( validateTrackListings() )
		{
			updateSongInstance(this, 'delete');
			refreshSongInstances(getRecordingId());
		}
	});
	
	// refresh song instances action
	$("input[name='refresh']").live('click', function() {
		refreshSongInstances(getRecordingId());
	});
	
	$("input#addblanksongssubmit").click( function() {
		var blankSongs = $("#blanksongs").val();
		if( /^\d+$/.test(blankSongs) && blankSongs > 0 )
			addBlankSongs(blankSongs);
		else
			alert('needs to be higher than 0');
	});
});

function addBlankSongs( blankSongs )
{
	$.post('addBlankSongs', {
		id : getRecordingId(),
		blankSongs : blankSongs
	}, function(data) {
		refreshSongInstances(getRecordingId());
	});
}

function refreshSongInstances( id )
{
	showLoadingMessage('editSongInstances');
	
	$('#editSongInstances').load('editIndividualRecording?id=' + id + ' #editSongInstances',
		function(responseText, textStatus, XMLHttpRequest) {
			// textStatus will be either 'success' or 'error'
			if( textStatus == 'error' )
				$('#editSongInstances').html('<div>Problem connecting to server...check internet connection</div>');
			
			scrollToBottom();
	});
	
	setDefaultTrackListingForAddSongInstance();
}

function saveAllSongInstances()
{
	if( validateTrackListings() )
	{
		$("input[name='trackListing']").each( function() {
			var trackListing = $(this).val();
			if( trackListing != null && trackListing != '' )
				updateSongInstance(this, 'update');
		});
		
		refreshSongInstances(getRecordingId());
	}
}

function updateSongInstance( context, action )
{
	var songInstanceRow = new SongInstanceRow();
	songInstanceRow.fill(context);
	
	$.post('songInstanceEdit', {
		// params
		action : action,
		songInstanceId : songInstanceRow.songInstanceId,
		recordingId : songInstanceRow.recordingId,
		trackListing : songInstanceRow.trackListing,
		songId : songInstanceRow.songId,
		section : songInstanceRow.section
	}, function(data) {
		//refreshSongInstances(getRecordingId());
	});
}

function SongInstanceRow() 
{
	var recordingId = "";
	var trackListing = "";
	var songId = "";
	var section = "";
	var songInstanceId = "";
	
	this.fill = function( context ) {
		var containingTR = $(context).parent().parent();
		
		this.recordingId = getRecordingId();
		this.trackListing = $("input[name='trackListing']", containingTR).val();
		this.songId = $("select[name='songId']", containingTR).val();
		this.section = $("input[name='section']", containingTR).val();
		this.songInstanceId = $(containingTR).attr('id');
		
		if( this.songInstanceId == 'add' )
			this.songInstanceId = "";
		
		return this;
	};
}

function getRecordingId()
{
	return $("input[name='recordingId']").val();
}