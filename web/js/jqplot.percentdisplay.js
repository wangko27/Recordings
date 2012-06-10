/**
 * jqPlot
 * Pure JavaScript plotting plugin using jQuery
 *
 * Version: 1.0.0a_r701
 *
 * Copyright (c) 2009-2011 Chris Leonello
 * jqPlot is currently available for use in all personal or commercial projects 
 * under both the MIT (http://www.opensource.org/licenses/mit-license.php) and GPL 
 * version 2.0 (http://www.gnu.org/licenses/gpl-2.0.html) licenses. This means that you can 
 * choose the license that best suits your project and use it accordingly. 
 *
 * Although not required, the author would appreciate an email letting him 
 * know of any substantial use of jqPlot.  You can reach the author at: 
 * chris at jqplot dot com or see http://www.jqplot.com/info.php .
 *
 * If you are feeling kind and generous, consider supporting the project by
 * making a donation at: http://www.jqplot.com/donate.php .
 *
 * sprintf functions contained in jqplot.sprintf.js by Ash Searle:
 *
 *     version 2007.04.27
 *     author Ash Searle
 *     http://hexmen.com/blog/2007/03/printf-sprintf/
 *     http://hexmen.com/js/sprintf.js
 *     The author (Ash Searle) has placed this code in the public domain:
 *     "This code is unrestricted: you are free to use it however you like."
 * 
 */
(function($) {				

	       
    /**
     * Class: $.jqplot.PercentageDisplay
     * Displays labels for the top X results. 
     * 
     * To use this plugin, include the js
     * file in your source:
     * 
     * > <script type="text/javascript" src="plugins/jqplot.percentdisplay.js"></script>
     * 
     *      
     *      usage: 
     *      
     *      PercentageDisplay:
    		{
        		tooltipAxes: 'xy',
	        	useAxesFormatters:true,
	        	tooltipLocation: 'n', 
	        	numberOfPoints: 10
    		},
    		
    		
     */
    $.jqplot.PercentageDisplay = function(options) {
        // Group: Properties
        //
        //prop: show
        // true to show the highlight.
        this.show = $.jqplot.config.enablePlugins;
        // prop: markerRenderer
        // Renderer used to draw the marker of the highlighted point.
        // Renderer will assimilate attributes from the data point being highlighted,
        // so no attributes need set on the renderer directly.
        // Default is to turn off shadow drawing on the highlighted point.
        this.markerRenderer = new $.jqplot.MarkerRenderer({shadow:false});
        // prop: showMarker
        // true to show the marker
        this.showMarker  = true;
        // prop: lineWidthAdjust
        // Pixels to add to the lineWidth of the highlight.
        this.lineWidthAdjust = 2.5;
        // prop: sizeAdjust
        // Pixels to add to the overall size of the highlight.
        this.sizeAdjust = 5;
        // prop: showTooltip
        // Show a tooltip with data point values.
        this.showTooltip = true;
        // prop: tooltipLocation
        // Where to position tooltip, 'n', 'ne', 'e', 'se', 's', 'sw', 'w', 'nw'
        this.tooltipLocation = 'nw';
        // prop: fadeTooltip
        // true = fade in/out tooltip, flase = show/hide tooltip
        this.fadeTooltip = true;
        // prop: tooltipFadeSpeed
        // 'slow', 'def', 'fast', or number of milliseconds.
        this.tooltipFadeSpeed = "fast";
        // prop: tooltipOffset
        // Pixel offset of tooltip from the highlight.
        this.tooltipOffset = 2;
        // prop: tooltipAxes
        // Which axes to display in tooltip, 'x', 'y' or 'both', 'xy' or 'yx'
        // 'both' and 'xy' are equivalent, 'yx' reverses order of labels.
        this.tooltipAxes = 'both';
        // prop; tooltipSeparator
        // String to use to separate x and y axes in tooltip.
        this.tooltipSeparator = ', ';
        // prop; tooltipContentEditor
        // Function used to edit/augment/replace the formatted tooltip contents.
        // Called as str = tooltipContentEditor(str, seriesIndex, pointIndex)
        // where str is the generated tooltip html and seriesIndex and pointIndex identify
        // the data point being highlighted. Should return the html for the tooltip contents.
        this.tooltipContentEditor = null;
        // prop: useAxesFormatters
        // Use the x and y axes formatters to format the text in the tooltip.
        this.useAxesFormatters = true;
        // prop: tooltipFormatString
        // sprintf format string for the tooltip.
        // Uses Ash Searle's javascript sprintf implementation
        // found here: http://hexmen.com/blog/2007/03/printf-sprintf/
        // See http://perldoc.perl.org/functions/sprintf.html for reference.
        // Additional "p" and "P" format specifiers added by Chris Leonello.
        this.tooltipFormatString = '%.5P';
        // prop: formatString
        // alternative to tooltipFormatString
        // will format the whole tooltip text, populating with x, y values as
        // indicated by tooltipAxes option.  So, you could have a tooltip like:
        // 'Date: %s, number of cats: %d' to format the whole tooltip at one go.
        // If useAxesFormatters is true, values will be formatted according to
        // Axes formatters and you can populate your tooltip string with 
        // %s placeholders.
        this.formatString = null;
        // prop: yvalues
        // Number of y values to expect in the data point array.
        // Typically this is 1.  Certain plots, like OHLC, will
        // have more y values in each data point array.
        this.yvalues = 1;
        // prop: bringSeriesToFront
        // This option requires jQuery 1.4+
        // True to bring the series of the highlighted point to the front
        // of other series.
        this.bringSeriesToFront = false;
        
        this._tooltipElements = []; 
        this.numberOfPoints = 5; 

        this.minimumDataValue = 99999; 
        
        // prop: useTickMarks
        // set true to enable displaying of the tick mark corresponding 
        // to the data point value instead of just a number 
        this.useYTickMarks = false;
        this.useXTickMarks = false;
        
        this.enabled = true; 

        $.extend(true, this, options);
    };
    
    // called with scope of plot
    $.jqplot.PercentageDisplay.init = function (target, data, opts){
        var options = opts || {};
        // add a PercentageDisplay attribute to the plot
        this.plugins.PercentageDisplay = new $.jqplot.PercentageDisplay(options.PercentageDisplay);             
    };
    
    // called within scope of series
    $.jqplot.PercentageDisplay.parseOptions = function (defaults, options) {
        // Add a showHighlight option to the series 
        // and set it to true by default.
        this.showHighlight = true;
    };
    
    // called within context of plot
    // create a canvas which we can draw on.
    // insert it before the eventCanvas, so eventCanvas will still capture events.
    $.jqplot.PercentageDisplay.postPlotDraw = function() {
        this.plugins.PercentageDisplay.highlightCanvas = new $.jqplot.GenericCanvas();
        
        this.eventCanvas._elem.before(this.plugins.PercentageDisplay.highlightCanvas.createElement(this._gridPadding, 'jqplot-highlight-canvas', this._plotDimensions));
        this.plugins.PercentageDisplay.highlightCanvas.setContext();
        
        var p = this.plugins.PercentageDisplay;
        
        for(i =0;i<p.numberOfPoints;i++)
        {
        	elem = $('<div class="jqplot-highlighter-tooltip" style="position:absolute;display:none"></div>');
        	p._tooltipElements[i] = elem;
        	this.eventCanvas._elem.before(elem);
        }        
        
        drawLabelsForPoints(this); 
    };
    
    $.jqplot.preInitHooks.push($.jqplot.PercentageDisplay.init);
    $.jqplot.preParseSeriesOptionsHooks.push($.jqplot.PercentageDisplay.parseOptions);
    $.jqplot.postDrawHooks.push($.jqplot.PercentageDisplay.postPlotDraw);   
    
    function showTooltip(plot, series, neighbor) {    	    	
        // neighbor looks like: {seriesIndex: i, pointIndex:j, gridData:p, data:s.data[j]}
        // gridData should be x,y pixel coords on the grid.
        // add the plot._gridPadding to that to get x,y in the target.
        var hl = plot.plugins.PercentageDisplay;
        
        if(!hl.enabled) return;
        
        var elem = hl._tooltipElements.splice(0, 1)[0];         
        if(!elem) return;                       
        
        if (hl.useAxesFormatters) {
            var xf = series._xaxis._ticks[0].formatter;
            var yf = series._yaxis._ticks[0].formatter;
            var xfstr = series._xaxis._ticks[0].formatString;
            var yfstr = series._yaxis._ticks[0].formatString;
            var str;
            var xstr = xf(xfstr, neighbor.data[0]);
            var ystrs = [];
		
            if(hl.useXTickMarks && series._xaxis._ticks[neighbor.data[0]])
        	{
            	xstr = xf(xfstr, series._xaxis._ticks[neighbor.data[0]].label); 
        	}
                        
            for (var i=1; i<hl.yvalues+1; i++) {
				if(hl.useYTickMarks && series._yaxis._ticks[neighbor.data[1]]) 
					ystrs.push(yf(yfstr, series._yaxis._ticks[neighbor.data[1]].label)); 
				else
					ystrs.push(yf(yfstr, neighbor.data[i]));
            }
            
            if (hl.formatString) {
                switch (hl.tooltipAxes) {
                    case 'both':
                    case 'xy':
                        ystrs.unshift(xstr);
                        ystrs.unshift(hl.formatString);
                        str = $.jqplot.sprintf.apply($.jqplot.sprintf, ystrs);
                        break;
                    case 'yx':
                        ystrs.push(xstr);
                        ystrs.unshift(hl.formatString);
                        str = $.jqplot.sprintf.apply($.jqplot.sprintf, ystrs);
                        break;
                    case 'x':
                        str = $.jqplot.sprintf.apply($.jqplot.sprintf, [hl.formatString, xstr]);
                        break;
                    case 'y':
                        ystrs.unshift(hl.formatString);
                        str = $.jqplot.sprintf.apply($.jqplot.sprintf, ystrs);
                        break;
                    default: // same as xy
                        ystrs.unshift(xstr);
                        ystrs.unshift(hl.formatString);
                        str = $.jqplot.sprintf.apply($.jqplot.sprintf, ystrs);
                        break;
                } 
            }
            else {
                switch (hl.tooltipAxes) {
                    case 'both':
                    case 'xy':
                        str = xstr;
                        for (var i=0; i<ystrs.length; i++) {
                            str += hl.tooltipSeparator + ystrs[i];
                        }
                        break;
                    case 'yx':
                        str = '';
                        for (var i=0; i<ystrs.length; i++) {
                            str += ystrs[i] + hl.tooltipSeparator;
                        }
                        str += xstr;
                        break;
                    case 'x':
                        str = xstr;
                        break;
                    case 'y':
                        str = ystrs.join(hl.tooltipSeparator);
                        break;
                    default: // same as 'xy'
                        str = xstr;
                        for (var i=0; i<ystrs.length; i++) {
                            str += hl.tooltipSeparator + ystrs[i];
                        }
                        break;
                    
                }                
            }
        }
        else {
            var str;
            if (hl.tooltipAxes == 'both' || hl.tooltipAxes == 'xy') {
                str = $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[0]) + hl.tooltipSeparator + $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[1]);
            }
            else if (hl.tooltipAxes == 'yx') {
                str = $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[1]) + hl.tooltipSeparator + $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[0]);
            }
            else if (hl.tooltipAxes == 'x') {
                str = $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[0]);
            }
            else if (hl.tooltipAxes == 'y') {
                str = $.jqplot.sprintf(hl.tooltipFormatString, neighbor.data[1]);
            } 
        }
        if ($.isFunction(hl.tooltipContentEditor)) {
            // args str, seriesIndex, pointIndex are essential so the hook can look up
            // extra data for the point.
            str = hl.tooltipContentEditor(str, neighbor.seriesIndex, neighbor.pointIndex, plot);
        }
        elem.html(str);
        var gridpos = {x:neighbor.gridData[0], y:neighbor.gridData[1]};
        var ms = 0;
        var fact = 0.707;
        if (series.markerRenderer.show == true) { 
            ms = (series.markerRenderer.size + hl.sizeAdjust)/2;
        }
        switch (hl.tooltipLocation) {
            case 'nw':
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true) - hl.tooltipOffset - fact * ms;
                var y = gridpos.y + plot._gridPadding.top - hl.tooltipOffset - elem.outerHeight(true) - fact * ms;
                break;
            case 'n':
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true)/2;
                var y = gridpos.y + plot._gridPadding.top - hl.tooltipOffset - elem.outerHeight(true) - ms;
                break;
            case 'ne':
                var x = gridpos.x + plot._gridPadding.left + hl.tooltipOffset + fact * ms;
                var y = gridpos.y + plot._gridPadding.top - hl.tooltipOffset - elem.outerHeight(true) - fact * ms;
                break;
            case 'e':
                var x = gridpos.x + plot._gridPadding.left + hl.tooltipOffset + ms;
                var y = gridpos.y + plot._gridPadding.top - elem.outerHeight(true)/2;
                break;
            case 'se':
                var x = gridpos.x + plot._gridPadding.left + hl.tooltipOffset + fact * ms;
                var y = gridpos.y + plot._gridPadding.top + hl.tooltipOffset + fact * ms;
                break;
            case 's':
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true)/2;
                var y = gridpos.y + plot._gridPadding.top + hl.tooltipOffset + ms;
                break;
            case 'sw':
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true) - hl.tooltipOffset - fact * ms;
                var y = gridpos.y + plot._gridPadding.top + hl.tooltipOffset + fact * ms;
                break;
            case 'w':
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true) - hl.tooltipOffset - ms;
                var y = gridpos.y + plot._gridPadding.top - elem.outerHeight(true)/2;
                break;
            default: // same as 'nw'
                var x = gridpos.x + plot._gridPadding.left - elem.outerWidth(true) - hl.tooltipOffset - fact * ms;
                var y = gridpos.y + plot._gridPadding.top - hl.tooltipOffset - elem.outerHeight(true) - fact * ms;
                break;
        }
        elem.css('left', x);
        elem.css('top', y);
        if (hl.fadeTooltip) {
            // Fix for stacked up animations.  Thnanks Trevor!
            elem.stop(true,true).fadeIn(hl.tooltipFadeSpeed);
        }
        else {
            elem.show();
        }
        elem = null;
        
    }
    
    function drawLabelsForPoints(plot) 
    {
    	var hl = plot.plugins.PercentageDisplay; 
    	
    	var sortedData = []; 
    	    	
    	for(i =0;i<plot.series[0].data.length;i++)
		{    		
    		var newPoint = new Object(); 
    		newPoint.data = plot.series[0].data[i];
    		newPoint.seriesIndex = 0; 
    		newPoint.gridData = plot.series[0].gridData[i];     		
    		    		
    		sortedData.push(newPoint);    		    	    			    
		}    	
    	
    	sortedData.sort(function(a, b) { return b.data[1] - a.data[1]; });     	    	   
    	    	
    	for(i=0;i<hl.numberOfPoints;i++)
    	{    		
			showTooltip(plot, plot.series[0], sortedData[i]);
    	}	
    }
})(jQuery);