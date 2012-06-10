	graphsLogic = function()
	{			
		GraphFormatter = function() 
		{
			this.graphType= null;  
			this.xAxisSource = null; 
			this.yAxisSource = null; 	
			this.graphDataSource = null; 
			this.graphStyle = null; 
			this.graphDataColumn = null; 
			this.graphDataColumnValue = null; 
			this.graphSpecialization = null; 			
			this.currentRequest = null; 
		}; 
		
		GraphFormatter.prototype.setCurrentRequest = function(req)
		{
			this.currentRequest = req; 
		}; 
		
		GraphFormatter.prototype.getCurrentRequest = function()
		{
			return this.currentRequest; 
		}; 
		
		GraphFormatter.prototype.buildGraphTitle = function()
		{
			var graphType = $(this.graphType).val();
			var xAxisValue = $(this.xAxisSource + " :selected").text(); 
			var yAxisValue = $(this.yAxisSource + " :selected").text(); 
			
			if(graphType == 'frequency')
			{
				return "Number of  " + $(this.graphDataSource + " :selected").text()  + " per " + xAxisValue; 
			}
			else if (graphType == 'frequencywithvalue')
			{
				return "Number of  " + $(this.graphDataSource + " :selected").text() + " per " + xAxisValue + " with " + $(this.graphDataColumn + " :selected").text() + ' of ' + $(this.graphDataColumnValue).val();
			}
			else if (graphType == 'generic')
			{
				return $(this.graphDataSource + " :selected").text() + ":  " +  xAxisValue + " versus " + yAxisValue; 
			}
			else
				return null; 
		}; 
		
		GraphFormatter.prototype.buildGraphAxisLabels = function()
		{
			var graphType = $(this.graphType).val();
			var xAxisValue = $(this.xAxisSource + " :selected").text(); 
			var yAxisValue = $(this.yAxisSource + " :selected").text(); 
			
			if(graphType == 'frequency' || graphType == 'frequencywithvalue')
			{				
				return [xAxisValue, 'Frequency']; 							
			}
			else if (graphType == 'generic')
			{
				return [xAxisValue, yAxisValue]; 
			}
			else
				return null; 
		};
	
		GraphFormatter.prototype.prepareGraphUrlString = function()
		{
			var parameters = this.prepareGraphParameters(); 
			// data to submit to the servlet
		    var dataString =  "getgraph=true" + "&graphdatasource=" + parameters.graphdatasource +  "&specialization=" + parameters.specialization +  "&style=" + parameters.style + "&type=" + parameters.type +  "&x=" + parameters.x + "&y=" + parameters.y + "&fwvColumn=" + parameters.fwvColumn + "&fwvColumnValue=" + parameters.fwvColumnValue; 
		    return dataString; 
		};
		
		GraphFormatter.prototype.getParametersFromUrl = function()
		{
			// from http://stackoverflow.com/questions/901115/get-query-string-values-in-javascript/2880929#2880929
			var urlParams = {}; 
			(function() {
				var e, 
					a = /\+/g,
					r=/([^&=]+)=?([^&]*)/g,
					d = function(s) { return decodeURIComponent(s.replace(a, " ")); },
					q = window.location.search.substring(1); 
					
					while(e = r.exec(q))
					{
						urlParams[d(e[1]).toLowerCase()] = d(e[2]);  
					}
			})(); 
			
			return urlParams; 
		}; 
		
		GraphFormatter.prototype.prepareGraphParameters = function()
        {
            var parameters = new Object(); 
            parameters.graphdatasource = $(this.graphDataSource).val();
            parameters.style = $(this.graphStyle).val(); 
            parameters.type = $(this.graphType).val(); 
            parameters.x = $(this.xAxisSource).val(); 
            parameters.y = $(this.yAxisSource).val(); 
         
            if(parameters.type == 'frequencywithvalue')
            {
                parameters.fwvColumn = $(this.graphDataColumn).val();
                parameters.fwvColumnValue = $(this.graphDataColumnValue).val();  
            } 
            else if (parameters.type == 'specialized')
	        {
	                parameters.specialization = $(this.graphSpecialization).val(); 
	        }
            
            return parameters; 
        }; 
        
        GraphFormatter.prototype.getRandomCannedGraph = function(canSelector)
        {
        	var itemCount = $(canSelector + " option").size();        	
        	var canObject = $(canSelector + " option").eq( Math.random() * itemCount);        	
        	        
        	var linkText = $(canObject).text(); 
        	var linkGraph = $(canObject).val(); 
        	
        	return {graphText: linkText, graphParameters: linkGraph  }; 
        }; 
        
	}();