$(document).ready(function() 
{
	updatedValueDefaultValueFill();
	mergeDefaultValueFill();
	
	// edit button handler
	$("table.lookupvaluetable input[value='edit']").live('click', function() {
		var category = $("th",$(this).closest("table")).text();
		var originalId = $("select[name='originalId']",$(this).closest("table")).val();
		var updatedValue = $("input[name='updatedValue']",$(this).closest("table")).val();

		editLookupValue(category, originalId, updatedValue);
	});
	
	// merge button handler
	$("input[value='merge']").live('click', function() {
		var category = $("th",$(this).closest("table")).text();
		var originalId = $("select[name='originalId']",$(this).closest("table")).val();
		var mergeId = $("select[name='mergeId']",$(this).closest("table")).val();
		
		mergeLookupValue(category, originalId, mergeId);
	});
	
	// make the merge select have the same value as the original select for convenience
	// (most merges will have similar song names)
	$("table.lookupvaluetable select[name='originalId']").live('change', function() {
		var mergeTable = $("select[name='mergeId']", $(this).closest("table"));
		$(mergeTable).val($(this).val());
		
		var updatedValueTextbox = $("input[name='updatedValue']", $(this).closest("table"));
		updatedValueTextboxMatchDropDown(updatedValueTextbox);
	});
	
	$("input[value='x'],", "table.notused").live('click', function() {
		var parentRow = $(this).closest("tr");
		var id = $("td:nth-child(2)",parentRow).text();
		var category = $("td:nth-child(3)",parentRow).text();
		
		deleteNotUsed(id, category);
	});
	
	$("input#addsongbutton").click( function() {
		var songname = $("input[name=newsong]").val();
		
		$.post('addSong', {
			value: songname
		}, function(data) { 
			alert("Done. Refresh page");
		});
	});
});

// blindly fill the contents of the edit input textbox
// with the contents of the song dropdown
function updatedValueDefaultValueFill()
{
	$("input[name='updatedValue']").each( function() {
		updatedValueTextboxMatchDropDown(this);
	});
}

// blindly fill the contents of the merge dropdown
// with the contents of the value of the song dropdown
function mergeDefaultValueFill()
{
	$("select[name='mergeId']").each( function() {
		var parentRow = $(this).closest("tr");
		var songText = $("select[name='originalId'] option:selected",parentRow).val();
		
		makeMergeDropDownMatch($(this), songText);
	});
}

function updatedValueTextboxMatchDropDown( updatedValueTextBox )
{
	var defaultValue = $("select[name='originalId'] option:selected",$(updatedValueTextBox).closest('table')).text();
	
	makeMergeDropDownMatch(updatedValueTextBox, jQuery.trim(defaultValue));
}

function makeMergeDropDownMatch( mergeDropDown, value )
{
	$(mergeDropDown).val(value);
}

function editLookupValue( category, originalId, updatedValue )
{
	$("#lookupValueEditSection").html("performing operation...");
	$("#lkpsuccess").html("");
	$('#lookupValueEditSection').load('lookupValues #lookupValueEditSection', {
		action: 'edit',
		category: category,
		originalId: originalId,
		updatedValue: updatedValue,
		mergeId: originalId /* hack so that the values can be pre-populated upon the response*/
	}, function(responseText, textStatus, XMLHttpRequest) {
		// textStatus will be either 'success' or 'error'
		if( textStatus == 'error' )
		{
			$('#lookupValueEditSection').html('<div>Problem connecting to server...check internet connection</div>');
		}
		else
		{
			showSuccessMessage();
			
			updatedValueDefaultValueFill();
			mergeDefaultValueFill();
		}
	});
}

function mergeLookupValue( category, originalId, mergeId )
{
	if( originalId != mergeId )
	{
		$("#lookupValueEditSection").html("performing operation...");
		$("#lkpsuccess").html("");
		$('#lookupValueEditSection').load('lookupValues #lookupValueEditSection', {
			action: 'merge',
			category: category,
			originalId: originalId,
			mergeId: mergeId
		}, function(responseText, textStatus, XMLHttpRequest) {
			// textStatus will be either 'success' or 'error'
			if( textStatus == 'error' )
			{
				$('#lookupValueEditSection').html('<div>Problem connecting to server...check internet connection</div>');
			}
			else
			{
				updatedValueDefaultValueFill();
				showSuccessMessage();
				mergeDefaultValueFill();
			}
		});
	}
	else
	{
		alert("You can't merge a " + category + " to itself");
	}
}

function deleteNotUsed( id, category )
{
	$('#notusedsection').load('lookupValues #notusedsection', {
		action: 'notuseddelete',
		id: id,
		category: category
	}, function(responseText, textStatus, XMLHttpRequest) {
		// textStatus will be either 'success' or 'error'
		if( textStatus == 'error' )
		{
			$('#notusedsection').html('<div>Problem connecting to server...check internet connection</div>');
		}
		else
		{
			showSuccessMessage();
		}
	});
}

function showSuccessMessage()
{
	$("#lkpsuccess").html('<div style="display:block;color:white;background-color:blue;border:1px solid black;font-size:130%">Operation Complete</div>');
}
