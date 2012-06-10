<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/formelement" prefix="fe"%>
<%@ taglib uri="/recordingsutil" prefix="ru"%>

<h2><a href="song?id=${param.id}">${song.value}</a></h2>

<fe:fittedTable tableElements="${songTableElements}" cssclass="summarytable" />

<div id="id" style="display:none">${song.id}</div>

<div id="associatedrecordings">
	<p>
		<strong>Associated Recordings:</strong>
	</p>
	<ul>
		<c:forEach var="recording" items="${recordings}">
		<li>
			<ru:recordingTitle recording="${recording}" link="individualRecording?id=${recording.id}" />
		</li>
		</c:forEach>
	</ul>
</div>

<div id="commentembedded">
	<c:import url="comments.jsp"/> 
</div>