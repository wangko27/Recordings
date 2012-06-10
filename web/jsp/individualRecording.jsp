<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/formelement" prefix="fe"%>
<%@ taglib uri="/recordingsutil" prefix="ru"%>

<!-- prev/next buttons  -->
<c:import url="prevnext.jsp"/> 

<div id="id" style="display:none">${param.id}</div>

<!-- display title -->
<h2>
<ru:recordingTitle recording="${recording}" link="individualRecording?id=${param.id}" />
<c:if test="${not empty authenticated}">
<a href="editIndividualRecording?id=${param.id}" style="font-size:60%">edit</a>
</c:if>
</h2>

<fe:fittedTable tableElements="${recordingTableElements}" cssclass="summarytable" />
<fe:fittedTable tableElements="${songInstanceTableElements}" cssclass="songinstancetable" autoFontSize="true" />

<div style="clear:both"></div>

<div id="commentembedded">
	<c:import url="comments.jsp"/> 
</div>
