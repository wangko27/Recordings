<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    

<!-- include any pregenerated graph link if the user had saved this graph from before  
	if there isn't a pregenerated link, suggest that the user start with one --> 
<c:choose> 
	<c:when test="${!empty pregeneratedlink}">
		<input type="hidden"  id="pregeneratedlink" value ="${pregeneratedlink}""/>
	</c:when>
	<c:otherwise>
		<div id="graphwelcome" class="helpbox common_darkerbrown">
			<h2>Welcome to the graphs page! Why not start <a href="graphs?pregenerated=yes&getgraph=true&graphdatasource=songinstance&specialization=undefined&style=line&type=frequencywithvalue&x=Value&y=Month&fwvColumn=Year&fwvColumnValue=1988">by clicking here</a> for the most popular songs in 1988? </h2>
	
		</div>
	</c:otherwise>
</c:choose>

<div id="graphdisplay">
	<div id="status"></div>
	<div id="chartDisplayArea"></div> 	
</div>

<div id="graphcodearea" >
	<span>Share this graph</span>
	
	<textarea id="graphlink" rows="3">When you generate a graph, a permanent link to it will be displayed here</textarea>
</div>

<hr>

<div class="getgraph">Generate Graph</div> 
 
<div id="graphform">    

	<div id="cannedgraphs" class="panel common_panel">
		<div class="panellink">Canned Graphs</div>
		<div id="minimizable">
			<table>
				<tr>
					<td>Select a canned graph option to have it generated</td>
					
					<td class="contexthelp">
						<p class="helptitle">Canned Graphs</p>
						<p>Use canned graphs to explore how the system works.</p> 						
						<p>When you click on a canned graph, the graph options will change to match the graph you clicked.</p> 
						<p>To graph it, click the <b>generate graph</b> button.</p>  
					</td> 
				</tr>
				
				<tr>
					<td> 
						<select size="6" id="can">
							<c:forEach var="cannedgraphentry" items="${cannedgraphs}">
								<option value=${cannedgraphentry.value}>${cannedgraphentry.key}</option>
							</c:forEach>
						</select>
					</td>					
				</tr>
			</table>
		</div>
	</div>		

	<div id="graphoptions" class="panel common_panel">
		<div class="panellink">Graph Options</div>
		<div id="minimizable">
					
			<table class="graphoptions">
				<tr>
					<td>Graph of:</td>
					<td>
						<select id="graphdatasource"> 
							<option value="recording">Recordings</option>
							<option value="recordingcomments">Recording Comments </option>
							<option value="songinstance">Songs</option>
						</select>
					</td>
					<td class="contexthelp">
						<p class="helptitle">Graph Data Source</p>
						<p>Select what kind of items you would like to graph.</p> 
					</td>
				</tr>					
											
				<tr> 		
					<td> Graph type: </td>			
					<td> 
						<select id="graphtype"> 
							<option value="frequency"> Frequency </option>
							<option value="frequencywithvalue"> Frequency against value </option>
							<option value="generic"> Generic </option>
							<option value="specialized"> Specialized</option>
						</select>
					</td>			
					<td class="contexthelp">
						<p class="helptitle">Graph Type</p>
						<p>Choose from the following graph types:</p> 						
						<UL>
							<LI> <b>Frequency</b> -- Number of items per x axis field (year, month, etc.)
								<br> <i>ex: "Number of recordings per month"'</i></LI>
							<LI> <b>Frequency against Value</b>-- Number of items per year/month/city/etc. but
							also all sharing a common value
								<br><i>ex: "Number of recordings per year with good quality"</i> </LI>
							<LI> <b>Generic</b> -- A basic graph that plots one variable versus another 
								<br><i>ex: "Recordings: Year vs. City"</i></LI>
							<LI> <b>Specialized</b> -- Graphs that are too specialized to be more general. Choose graphs of this type from the canned graphs list  
								<br><i>ex: "Number of new songs per year"</i></LI>
						</UL>
					</td>
				</tr>
				
				<tr id="showspecialization" style="display:none">
					<td>
						Specialization: 
					</td>
					<td>
						<select id="specialization">
							<option value="firstappearance">New songs per Year</option>
						</select> 
					</td>
					<td class="contexthelp">
						<p class="helptitle">Specialization</p>
						Choose the specific type of specialized graph. 
					</td>
				</tr>			
						
				<tr id="graphstyling">
					<td>Style:</td>
					<td> 				
						<select id="graphstyle"> 
							<option value="line"> Line Graph </option>
							<option value="pie"> Pie Chart </option>			
						</select>
					</td>	
					
					<td class="contexthelp">
						<p class="helptitle">Graph Style</p>
						
						<p>Certain graph types such as <b>frequency</b> and <b>frequency against value</b> can be displayed as either line graphs or pie charts. Pick the one that gives the best results for you.  </p>
					</td>
				</tr>
			
			
			
				<tr>
					<td>X axis field:</td>
					<td> 
						<select id="xaxis">						
						</select>
					</td>										
					
					<td class="contexthelp">
						<p class="helptitle">Axis Fields</p>
						
						<p>Each axis field represents a variable for the data source you have chosen. These variables will be graphed based on the graph type you've chosen'</p>
					</td>
				</tr>
			
				<tr id="yaxisfield">
					<td>
					Y axis field: 
					</td> 
					<td>						
						<select id="yaxis">					
						</select>
					</td>
					<td class="contexthelp">
						<p class="helptitle">Axis Fields</p>
						
						<p>Each axis field represents a variable for the data source you have chosen. These variables will be graphed based on the graph type you've chosen'</p>
					</td>
				</tr>
			</table>
			
		</div>
	</div>
	
	<div style="clear:both"></div>

	
	<div id="graphstyleoptions" class="panel common_panel">
		<div class="panellink">Graph Style Options</div>
	
		<div id="minimizable">	
			<table> 
				<tr> 				
					<td>Color:</td>
					<td> 
						<select id="graphcolor">
							<option value="#000000">Black </option> 
							<option value="#FF0000">Red </option>
							<option value="#0000FF">Blue </option>					
						</select>
					</td>					
					<td class="contexthelp">
						<p class="helptitle">Color</p>
						<p>
							For line graphs, choose the color of the line. 
						</p>
					</td>
				</tr>
				
				<tr>
					<td>
						Connect points 
					</td>
					<td>
						<input type="checkbox" id="showline" checked>
					</td>
					<td class="contexthelp">
						<p class="helptitle">Connect Points</p>
						
						<p>
							Choose this option when it makes sense to connect the points on graph. On some <b> generic </b> graphs, this option might make the graph more unreadable. 
						</p>
					</td>
				</tr>				
			
				<tr>
					<td>
						Label top 5 results for large data sets:
					</td>
					<td> 												
						<input type="checkbox" id="showtopresults"/>  						
					</td>
					<td class="contexthelp">
						<p class="helptitle">Labeling Top Results</p>
						
						For graphs that have a large number of data points, choosing this option will show labels beside the top 5 points.   						
					</td>
				</tr>						
			
			</table>
		</div>		
	</div>
	
	<div id="graphdataentry" class="panel common_panel" style="display:none">
		<div class="panellink" id="minimizegraphdata">Graph Data Entry Options</div>
		
		<div id="minimizable">
			<table>	
				<tr>
					<td> 
						Common field: 
					</td>
					<td> 
						<select id="graphdatacolumn">			
						</select>
					</td>	
					<td class="contexthelp">
						<p class="helptitle">Common Field</p>
						
						<p>
							Choose the field that all will be the filter for the items you are graphing. For example, to filter results by a specific city on recordings, choose the <b>city</b> field and enter a city name into the <b>value</b> text box. 
						</p>
					</td>
				</tr>					
				<tr> 
					<td>
						Value: 
					</td>
					<td> 
						<input type="text" value="column value" id="graphdatacolumnvalue"/>
					</td>
					
					<td class="contexthelp">
						<p class="helptitle">Common Value</p>
						
						<p>
							Enter a value in the text box that all items (recordings, songs, etc.) must have in order to be graphed. 
						</p>
					</td>
				</tr>			
			</table> 
		</div>			
	</div>
</div>
