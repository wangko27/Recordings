		
graphsUI = function()
{
	GraphUIHandler = function()
	{
		this.graphType= null;  
		this.xAxisSource = null; 
		this.yAxisSource = null; 	
		this.graphDataSource = null; 
		this.graphStyle = null; 
		this.graphDataColumn = null; 
		this.graphDataColumnValue = null; 
		this.graphSpecialization = null;	
		this.showTopResults = null; 
		this.showLine = null; 
		
		this.xAxisPanel = null; 
		this.yAxisPanel = null; 
		this.specialiationPanel = null; 
		this.dataEntryPanel = null; 
		this.graphStylingPanel = null; 
	}; 
	
	 /**
     * adds an option item to a select
     * @param select the select to add to 
     * @param key the option's actual value
     * @param value the option's display value
     */
    GraphUIHandler.prototype.addToSelect = function(select, key, value)
    {
    	select.
        append($("<option></option>").
        attr("value",key).
        text(value)); 
    };
	
    /**
     * creates a select option with the valid recording comment columns
     */
    GraphUIHandler.prototype.buildRecordingCommentAxes = function(axisTarget)
    {
    	addToSelect = this.addToSelect; 
    	axisTarget.children().remove();
    	addToSelect(axisTarget, "Username", "Username");
    	addToSelect(axisTarget, "Text", "Text"); 
    	addToSelect(axisTarget, "Bob", "Bob's comment");
    	addToSelect(axisTarget, "Timestamp", "Date posted"); 
	  	addToSelect(axisTarget, "VoteRank", "Votes"); 
    };
    
    GraphUIHandler.prototype.buildSongInstanceAxes = function(axisTarget)
    {
    	this.buildRecordingAxes(axisTarget); 
    	
    	addToSelect = this.addToSelect; 
    	
    	addToSelect(axisTarget, "Value", "Title");
    	addToSelect(axisTarget, "TrackListing", "Track Number");
    	addToSelect(axisTarget, "Year", "Year");
    	addToSelect(axisTarget, "City", "City"); 
    	//votes acquired from recordings 
    	//addToSelect(axisTarget, "Votes", "Votes");     	
    };
    
    /**
     * creates a select option with the valid recording columns
     */
    GraphUIHandler.prototype.buildRecordingAxes = function(axisTarget)
    {    	
    	addToSelect = this.addToSelect; 
    	
    	axisTarget.children().remove(); 
    	addToSelect(axisTarget, "Month", "Month"); 
    	addToSelect(axisTarget, "Year", "Year");   
    	addToSelect(axisTarget, "Sublocation", "Sublocation"); 
    	addToSelect(axisTarget, "Name", "Name");
    	addToSelect(axisTarget, "Station", "Station"); 
    	addToSelect(axisTarget, "Link", "Link"); 
    	addToSelect(axisTarget, "Venue", "Venue"); 
    	addToSelect(axisTarget, "City", "City");
    	addToSelect(axisTarget, "Country", "Country");
    	addToSelect(axisTarget, "Media", "Media"); 
    	addToSelect(axisTarget, "Quality", "Quality"); 
    	addToSelect(axisTarget, "Format", "Format"); 
    	addToSelect(axisTarget, "RecordingType", "Recording Type"); 
    	addToSelect(axisTarget, "Votes", "Votes"); 
    };
    
    GraphUIHandler.prototype.changeGraphType = function(theGraphType)
    {    	
    	$(this.graphType).children().removeAttr("selected");
    	$(this.graphType).find('[value=' + theGraphType + ']').attr("selected", "selected");    	
    	
    	$(this.showTopResults).attr("checked", "checked"); 
    	
    	if(theGraphType == 'generic')
    	{
    		$(this.yAxisPanel).show();    
    		$(this.showTopResults).removeAttr("checked"); 
    		$(this.showLine).removeAttr("checked"); 
    	}
    	else
    	{
    		$(this.yAxisPanel).hide();
    	}
    	    	
    	if(theGraphType == 'frequency')    		
    		$(this.graphStylingPanel).show(); 
    	else
    		$(this.graphStylingPanel).hide(); 
    	
    	if(theGraphType == 'frequencywithvalue')
		{
    		$(this.dataEntryPanel).show(); 
		}
    	else
    		$(this.dataEntryPanel).hide(); 
    	
    	if(theGraphType == 'specialized')
		{
    		$(this.xAxisPanel).show();
    		$(this.yAxisPanel).show();
    		$(this.specializationPanel).show(); 
		}
    	else
    		$(this.specializationPanel).hide(); 
    }; 
    
    GraphUIHandler.prototype.setSpecializedGraphOptionsBasedOnParameters = function(parameters)
    {
    	if(parameters == null) return;    	
    	$(this.graphDataSource).children().removeAttr("selected");
    	$(this.graphDataSource).find('[value=' + parameters.graphdatasource + ']').attr("selected", "selected"); 
    	$(this.graphType).children().removeAttr("selected"); 
    	$(this.graphType).find('[value=' + parameters.type + ']').attr("selected", "selected");
    	$(this.graphSpecialization).children().removeAttr("selected"); 
    	$(this.graphSpecialization).find('[value=' + parameters.specialization + ']').attr("selected", "selected");
    	$(this.showTopResults).attr("checked","checked");
    	$(this.showLine).attr("checked","checked"); 
    	this.switchGraphDataSource(parameters.graphdatasource);
    	this.changeGraphType('specialized'); 
    }; 
    
    GraphUIHandler.prototype.setGenericGraphOptionsBasedOnParameters = function(parameters)
    {
    	if(parameters == null) return; 
    	
    	this.switchGraphDataSource(parameters.graphdatasource); 
    	$(this.xAxisSource).children().removeAttr("selected"); 
    	$(this.xAxisSource).find('[value=' + parameters.x + ']').attr("selected", "selected");
    	$(this.yAxisSource).children().removeAttr("selected"); 
    	$(this.yAxisSource).find('[value=' + parameters.y + ']').attr("selected", "selected");
    	$(this.graphDataSource).children().removeAttr("selected"); 
    	$(this.graphDataSource).find('[value=' + parameters.graphdatasource + ']').attr("selected", "selected");
    	$(this.graphType).children().removeAttr("selected"); 
    	$(this.graphType).find('[value=' + parameters.type + ']').attr("selected", "selected");
    	$(this.showLine).removeAttr("checked"); 
    	$(this.showTopResults).removeAttr("checked"); 
    	this.changeGraphType('generic'); 
    }; 
    
    GraphUIHandler.prototype.setFWVGraphOptionsBasedOnParameters = function(parameters)
    {
    	if(parameters == null) return; 
    	
    	this.switchGraphDataSource(parameters.graphdatasource); 
    	$(this.xAxisSource).children().removeAttr("selected"); 
    	$(this.xAxisSource).find('[value=' + parameters.x + ']').attr("selected", "selected");
    	$(this.graphDataSource).children().removeAttr("selected"); 
    	$(this.graphDataSource).find('[value=' + parameters.graphdatasource + ']').attr("selected", "selected");
    	$(this.graphType).children().removeAttr("selected"); 
    	$(this.graphType).find('[value=' + parameters.type + ']').attr("selected", "selected");
    	$(this.showLine).attr("checked","checked"); 
    	$(this.graphDataColumn).children().removeAttr("selected"); 
    	$(this.graphDataColumn).find('[value=' + parameters.fwvcolumn + ']').attr("selected", "selected");
    	$(this.graphDataColumnValue).val(parameters.fwvcolumnvalue);
    	$(this.graphDataColumnValue).attr("checked","checked"); 
    	this.changeGraphType('frequencywithvalue'); 
    }; 
    
    GraphUIHandler.prototype.setFrequencyGraphOptionsBasedOnParameters = function(parameters)
    {
    	if(parameters == null) return; 
    	
    	this.switchGraphDataSource(parameters.graphdatasource); 
    	$(this.xAxisSource).children().removeAttr("selected"); 
    	$(this.xAxisSource).find('[value=' + parameters.x + ']').attr("selected", "selected");
    	$(this.graphDataSource).children().removeAttr("selected"); 
    	$(this.graphDataSource).find('[value=' + parameters.graphdatasource + ']').attr("selected", "selected");
    	$(this.graphType).children().removeAttr("selected"); 
    	$(this.graphType).find('[value=' + parameters.type + ']').attr("selected", "selected");
    	$(this.showLine).attr("checked","checked");
    	$(this.showLine).attr("checked","checked");
    	this.changeGraphType('frequency'); 
    }; 
    
    GraphUIHandler.prototype.setupContextHelp = function()
    {
    	var contextHelpUsers = $('.contexthelp'); 
    	
    	for(i=0;i<contextHelpUsers.length;i++)
		{
    		var contextHelpPopup = $(contextHelpUsers[i]);
    		var text = contextHelpPopup.html(); 
    		contextHelpPopup.empty(); 
    		contextHelpPopup.append('<div style="display:none">' + text + '</div>');    		
    		contextHelpPopup.append($('<span class="helppopup">?</span>'));     		
    		$('.helpbox').live('click', function() { $('.helpbox').remove(); }); 
    		
    		$(contextHelpUsers[i]).click(function()
			{    			    			
    			$('.helpbox').remove();     			    			
    			var newError = $('<div></div>'); 
    			newError.addClass("helpbox"); 
    			newError.addClass("common_darkerbrown"); 
    			//$(newError).offset($(this).parent().offset());    			
    			$(newError).show(); 
    			$(newError).html($(this).children().first().html());
    			
    			// TODO: make this go to the minimizable parent 
    			$(this).parent().parent().parent().parent().append(newError);    			    			    			  
			}); 
		}
    }; 
    
    GraphUIHandler.prototype.switchGraphDataSource = function(dataSource)
    {
    	if(dataSource == 'recording')
		{
    		this.buildRecordingAxes($(this.xAxisSource));
    		this.buildRecordingAxes($(this.yAxisSource)); 
    		this.buildRecordingAxes($(this.graphDataColumn)); 
		}
    	else if (dataSource == 'recordingcomments')
		{
    		this.buildRecordingCommentAxes($(this.xAxisSource));
    		this.buildRecordingCommentAxes($(this.yAxisSource));
    		this.buildRecordingCommentAxes($(this.graphDataColumn)); 
		}
    	else if (dataSource == 'songinstance')
		{
    		this.buildSongInstanceAxes($(this.xAxisSource));
    		this.buildSongInstanceAxes($(this.yAxisSource));
    		this.buildSongInstanceAxes($(this.graphDataColumn)); 
		}
    }; 
}(); 