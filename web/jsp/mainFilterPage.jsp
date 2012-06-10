<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/sam" prefix="sam" %>

<!-- determine default tab -->
<sam:getParam var="defaultTab" val="${sessionScope.session_tab}" samVal="s_tab_defaultTab" pref="${param.pref}" defaultVal="Recordings" />
<!-- Saves the selected tab in session -->
<script type="text/javascript">
$(document).ready(function()
{
	// invoke idtabs
	// pass the default selected tab as a value that 
	// is saved in session whenever a tab is clicked on
	//$(".mainfilter ul").idTabs("<c:out value="${defaultTab}" />");
	setDefaultTab("<c:out value="${defaultTab}" />");
	
	// when a tab is selected then get the href value (minus the # character)
	// and then pass that value to a function that will make 
	// a call to the server to store that value in session
	$("li span","ul#maintablist").click( function() {
		var text = $(this).text();
		setTabInSession(text);
	});
});
function setTabInSession( tab )
{
	// will save the tab value in session
	$.post('/Recordings/tabsession', {
		tab: tab
	});
}
</script>

<div class="mainfilter">
  <ul id="maintablist">
    <li><span class="spanlink <c:if test="${defaultTab eq 'Recordings'}">selected</c:if>">Recordings</span></li> 
    <li><span class="spanlink <c:if test="${defaultTab eq 'Songs'}">selected</c:if>">Songs</span></li>
    <li><span class="spanlink <c:if test="${defaultTab eq 'Search'}">selected</c:if>">Search</span></li>
  </ul>
  <div id="recordingtab" class="common_panel" <c:if test="${defaultTab ne 'Recordings'}">style="display:none"</c:if>>
  	<c:import url="mainFilterElements/recordingFilterForm.jsp" />
  </div>
  <div id="songtab" class="common_panel" <c:if test="${defaultTab ne 'Songs'}">style="display:none"</c:if>>
    <c:import url="mainFilterElements/songFilterForm.jsp" />
  </div>
  <div id="searchtab" class="common_panel" <c:if test="${defaultTab ne 'Search'}">style="display:none"</c:if>>
    <c:import url="mainFilterElements/searchFilterForm.jsp" />
  </div>
</div>

<div style="clear:both"></div>