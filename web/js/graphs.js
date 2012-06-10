
$(document).ready(function()
{	 	
	$.jqplot.config.enablePlugins = true;
	
	if(typeof(GraphUIHandler) == 'undefined')
	{
		alert('Graph UI Handler not loaded');
		return; 
	}
	
	if(typeof(GraphFormatter) == 'undefined') 
	{
		alert('Graph Formatter not loaded!');
		return; 
	}	
	
	var graphFormatter = new GraphFormatter(); 
	var graphUIHandler = new GraphUIHandler(); 
	
	graphFormatter.xAxisSource = graphUIHandler.xAxisSource = '#xaxis'; 
	graphFormatter.yAxisSource  = graphUIHandler.yAxisSource = '#yaxis'; 
	graphFormatter.graphType  = graphUIHandler.graphType = '#graphtype'; 
	graphFormatter.graphDataSource  = graphUIHandler.graphDataSource = '#graphdatasource'; 
	graphFormatter.graphStyle  = graphUIHandler.graphStyle = '#graphstyle'; 	
	graphFormatter.graphDataColumn  = graphUIHandler.graphDataColumn= '#graphdatacolumn';
	graphFormatter.graphDataColumnValue  = graphUIHandler.graphDataColumnValue = '#graphdatacolumnvalue'; 
	graphFormatter.graphSpecialization  = graphUIHandler.graphSpecialization = '#specialization'; 
	graphUIHandler.showTopResults = '#showtopresults'; 
	graphUIHandler.showLine = '#showline'; 
	
	graphUIHandler.dataEntryPanel = '#graphdataentry';
	graphUIHandler.graphStylingPanel = '#graphstyling'; 
	graphUIHandler.specializationPanel = '#showspecialization'; 
	graphUIHandler.xAxisPanel = '#xaxisfield'; 
	graphUIHandler.yAxisPanel = '#yaxisfield'; 
	graphUIHandler.showLine = '#showline'; 
	graphUIHandler.showTopResults = '#showtopresults'; 
	graphUIHandler.setupContextHelp();
	
	function cancelGraphRequest()
	{
		$('#status #error').hide(); 
    	graphFormatter.getCurrentRequest().abort();
	}
	
	/**
	 * makes an AJAX call to the GraphsController and plots the resulting graph 
	 */
	function fetchGraph(url, plot, formatOptions) 
	{
		// if there is another graph request going on, cancel it. 
	    if(graphFormatter.getCurrentRequest())
    	{
	    	cancelGraphRequest(); 
    	}
		
		// make sure there are graph styling options passed in 
		if(formatOptions == null) 
		{
			$('#status').html('<p id="error"> Error: There was a problem generating the graph: <a href="#" id="showerror">details ... </a> ' + '<div id="errordetails"> formatOptions was null </div></p>'); 
        	$('#errordetails').hide(); 	        	
        	$('#chartDisplayArea').hide(); 
        	return null; 
		}
		
		var dataString = graphFormatter.prepareGraphUrlString(); 

	    // if plot is a data string, then we are using a pregenerated graph 
	    if(plot)
    	{
	    	dataString = plot;
	    	var generatedParameters = graphFormatter.getParametersFromUrl();	    	
	    	setGraphOptionsBasedOnParameters(graphFormatter.getParametersFromUrl()); 
    	}	   
	    
	    graphFormatter.setCurrentRequest( $.ajax({ 
	        url: url,
	        data: dataString, 
	        dataType:'json',
	        
	        // data was not received: show transmit error 
	        error:function (xhr, ajaxOptions, thrownError)
	        {
	        	if(thrownError == 'abort') return; 
	        	
	        	$('#status').html('<p id="error"> Error: There was a problem generating the graph: <a href="#" id="showerror">details ... </a> ' + '<div id="errordetails">' +  thrownError + '</div></p>'); 
	        	$('#status #errordetails').hide(); 	        	
	        	$('#chartDisplayArea').hide();  
	        	return; 
	        },
	        	        
	        // data was transmitted: plot graph or show validation error
	        success: function(data)
	        {          	        		        	
	        	// data was null : server probably threw and logged an exception 
	        	if(data == null)
	        	{
	        		$('#status').html('<p id="error"> Error: the server had a problem sending valid data</p>');
	        		$('#chartDisplayArea').hide(); 
	        		return null; 
	        	}
	        		        	
	        	var xticks = data.xticks;  
	        	var yticks = data.yticks;
	        	
	        	graphFormatter.buildGraphAxisLabels();	 	       	        	
	        	var xlabel = data.xlabel; 
	        	var ylabel = data.ylabel;	        	
	        	var newLabels = graphFormatter.buildGraphAxisLabels(); 	        
	        	if(newLabels != null)
        		{
		        	xlabel = graphFormatter.buildGraphAxisLabels()[0]; 
		        	ylabel = graphFormatter.buildGraphAxisLabels()[1];
        		}
	        	
	        	var graphTitle = graphFormatter.buildGraphTitle();	        	
	        	if(graphTitle == null) graphTitle = data.title; 
	        	
	        	var roundxaxis = data.roundxaxis; 
	        	var roundyaxis = data.roundyaxis;
	        	
	        	// if there are more tick marks than these numbers, 
	        	// tick marks will be disabled. 
	        	var maxNumberOfXTickMarks = 20; 
	        	var maxNumberOfYTickMarks = 20; 
	        	// hide tick marks if there are too many data points 
	        	var showXTicks = xticks &&  xticks.length <= maxNumberOfXTickMarks; 
	        	var showYTicks = yticks  && yticks.length <= maxNumberOfYTickMarks; 	        	
	        	
	        	// show chart  (previously hidden) 
	        	$('#chartDisplayArea').show(); 
	        	// hide "fetching graph data..." text 
	        	$('#status #fetchgraphstatus').hide(); 
	        	
	        	// error occurred during server-side validation!
	        	if(data.message)
	        	{
	        		// hide chart section since it's a blank image
	        		$('#chartDisplayArea').hide(); 	
	        		$('#status').html('<p id="error"> Error: ' + data.message + '</p>');	        		
	        		return; 
	        	}	        		        	
	        	// data was empty because there was no column data. 
	        	else if (data.data && data.data.length == 0)
        		{
	        		$('#status').html('<p id="error"> The graph you requested contains no data. Try choosing another graph. </p>'); 
	        		$('#chartDisplayArea').hide(); 
	        		return; 
        		}
	        		        		        
	            $('#graphlink').html('http://jrrecordings.com/graphs?pregenerated=yes&' + dataString);
	            
	            var plotRenderer = $.jqplot.DefaultRenderer;
	            var showLegend = false; 
	            var lineVisible = true; 
	            var xFormatString = '%.1f'; 
	            var yFormatString = '%.1f';
	            var useYTicksForHighlighter = false; 
	            var useXTicksForHighlighter = false; 
	            
	            // if there were non-numeric tick marks, make them appear in the highlighter instead of the tick indices
	            if(xticks.length > 0)
	            	useXTicksForHighlighter =true; 
	            if(yticks.length > 0)
	            	useYTicksForHighlighter = true; 
	            
	            if(roundxaxis) 
	            	xFormatString = '%d'; 
	            if(roundyaxis)
	            	yFormatString = '%d'; 
	          
	            if(data.style == 'pie')
	            {
	            	plotRenderer = $.jqplot.PieRenderer; 
	            	showLegend = true; 
	            }

        		$.jqplot('chartDisplayArea',[data.data],
	        	{
			        title:graphTitle, 
			        seriesDefaults:{renderer:plotRenderer, rendererOptions:{ showDataLabels:true }}, 
			        legend:{show:showLegend, location: 'w'}, 
			        cursor:{zoom:true, showTooltip:false},
			        
			        highlighter:
		        	{
			        	tooltipAxes: 'xy',
			        	useAxesFormatters:true,
			        	useXTickMarks: useXTicksForHighlighter, 
			        	useYTickMarks: useYTicksForHighlighter
		        	}, 
		        	
					PercentageDisplay:
					{
						tooltipAxes: 'xy',
						useAxesFormatters:true,
						useXTickMarks: useXTicksForHighlighter, 
						useYTickMarks: useYTicksForHighlighter, 
						enabled: !showXTicks && formatOptions.showtopresults,
						tooltipLocation: 'n', 
						numberOfPoints: 5
					},
			        
			        series:
		        	[
		        	 { showLine: formatOptions.showline, color: formatOptions.color}
	                ], 
			        
	                grid:
                	{
	                	drawGridlines: showXTicks && showYTicks 
                	},
	                
			        axes:
			        {
			        	xaxis:
			        	{
			        		ticks:xticks,
			        		label:xlabel,
			        		tickOptions:  { formatString: xFormatString}, 
			        		showTicks: showXTicks
			        	},
        				yaxis:
        				{
        					ticks:yticks,
        					label:ylabel,
        					tickOptions:  { formatString: yFormatString}, 
        					showTicks: showYTicks
        				}
			        }	
			    });
	        }
	    })); 
	};
	
	/**
	 * Initiates the graph request 
	 */
    $(".getgraph").click(function() 
    {
    	$('#status').html('<p id="fetchgraphstatus">Fetching graph from server...</p>');    	
    	
    	$('#chartDisplayArea').empty();    	    	
    	$('#status #error').empty(); 
    	$('#graphlink').empty(); 
    	$('#graphlink').text("When you generate a graph, a permanent link to it will be displayed here"); 
    	    	
    	fetchGraph("graphs", null, buildStyleOptionsFromForm()); 
    }); 
    
    /**
     * shows or hide axes field options based on the type of graph selected
     */
    $("#graphtype").change(function()
	{
    	var graphType = $(this).val(); 
    	
    	graphUIHandler.changeGraphType(graphType); 
	});         
    
     // show the "details" link in case of network error (timeout) 
    // use .live because showerror has not been created yet
    $('#showerror').live('click', function()
	{
    	$('#errordetails').show(); 	 
    	return; 
	});      
    
    $('#graphlink').click(function()
	{
    	$(this).select(); 
	}); 
        
    function setDefaultSettings()
    {    	    
    	/// use default data source 
    	$('#graphdatasource').val('recording'); 
        graphUIHandler.switchGraphDataSource('recording'); 
        
        $('#cannedgraphs #can').children().removeAttr("selected"); 
    	
        graphUIHandler.changeGraphType('frequency'); 
	    
	    /**
	     * if the user entered the link to a pregenerated graph, we execute that immediately 
	     * instead of waiting for user input. 
	     */
		$('#status').html('<p id="fetchgraphstatus">Fetching graph from server...</p>'); 
		$('#chartDisplayArea').empty();
		
		// hack for IE 7 -- doesn't render one chart canvas so we have to render two (why(?))
		if ( $.browser.msie ) 
		{
		  var ieVersion = parseInt($.browser.version, 10);
		  
		  if(ieVersion < 8)
			$('#chartDisplayArea').html('<canvas width="992" height="300" style="position: absolute; left: 0px; top: 0px;" class="jqplot-base-canvas"></canvas>');
		}
	
		
		$('#status #error').empty();     	    
		$('#graphlink').empty();
		$('#graphlink').text("When you generate a graph, a permanent link to it will be displayed here");
		
		if($('#pregeneratedlink').val())
		{					
			// change graph parameters based on pregenerated link 
			var generatedParameters = graphFormatter.getParametersFromUrl();
			setGraphOptionsBasedOnParameters(generatedParameters); 
	    	graphUIHandler.changeGraphType(generatedParameters.type);
		}
    	
		fetchGraph("graphs", $('#pregeneratedlink').val(),buildStyleOptionsFromForm()); 
    }
    
    function updatePanelDisplay(minSource)
    {
    	minSource.slideToggle(); 
    }

    /**
     * builds an object representing the different styles that the user wants for the generated graph 
     */
    function buildStyleOptionsFromForm()
    {
    	var options = new Object(); 
    	options.showline = $('#graphstyleoptions #showline').is(":checked"); 
    	options.color = $('#graphstyleoptions #graphcolor').val(); 
    	options.showtopresults = $('#showtopresults').is(":checked"); 
    
    	return options; 
    }
   
    function setGraphOptionsBasedOnParameters(parameters)
    {
    	if(parameters == null) return; 
    	
    	if(parameters.fwvcolumnvalue) parameters.fwvcolumnvalue = parameters.fwvcolumnvalue.replace("_", " "); 
    	
    	if(parameters.type == 'frequency')
    		graphUIHandler.setFrequencyGraphOptionsBasedOnParameters(parameters);
    	else if (parameters.type == 'generic')
    		graphUIHandler.setGenericGraphOptionsBasedOnParameters(parameters); 
    	else if (parameters.type == 'frequencywithvalue')
    		graphUIHandler.setFWVGraphOptionsBasedOnParameters(parameters); 
    	else if (parameters.type == 'specialized')
    		graphUIHandler.setSpecializedGraphOptionsBasedOnParameters(parameters); 
    }    
 
    /**
     * switches to a datasource when the user selects one
     */
    $('#graphdatasource').click(function()
	{        	
    	graphUIHandler.switchGraphDataSource($(this).val());     	
    });         
    
    $('#cannedgraphs #can').click(function()
	{
    	setGraphOptionsBasedOnParameters($.parseJSON( $(this).val())); 
    	$(".getgraph").click(); 
	});     
    
    $('#graphcodearea span').click(function() 
	{
    	$('#graphcodearea textarea' ).slideToggle(); 
	}); 
    
    setupWelcomeGraph = function()
    {
    	var theGraph = graphFormatter.getRandomCannedGraph("#can"); 
    	var welcome = $('#graphwelcome h2'); 
    	
    	welcome.html('Welcome to the graphs page! Click here for the "' + theGraph.graphText + '"');
    	$('<p></p>').text(theGraph.graphParameters).hide().appendTo(welcome); 
    	
    	$(welcome).click(function()
		{
    		setGraphOptionsBasedOnParameters($.parseJSON( $(this).children().first().text() ));
    		$(".getgraph").click(); 
		}); 
    }(); 
    		
    graphFormatter.getRandomCannedGraph("#can"); 
    
    setDefaultSettings();         
});



