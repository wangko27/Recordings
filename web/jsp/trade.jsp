<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/recordingsutil" prefix="ru"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-GB">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
  <title>Trade</title>
  
  <style>
  	body {
  		font-family:arial;
  		background-color:#F6CF8D;
  	}
  	h1, p {
  		margin:0;
  		padding:0;
  		font-family:arial;
  	}
  	h1 {
  		margin-top:10px;
  		font-size:26px;
  	}
  	span.sidenote {
  		font-size:80%;
  		font-family:"Times New Roman", tahoma;
  	}
  	div#links ul {
  		display:inline;
  		float:left;
  		margin:0;
  		padding:0;
  		font-size:80%;
  		font-family:tahoma;
  	}
  	div#links ul li {
  		display:inline;
  		padding-right:10px;
  	}
  	div#links ul li a {
  		color:blue;
  	}
  	div.recordingline {
  		margin-top:8px;
  		font-size:90%;
  	}
  	a {
  		text-decoration:none;
  	}
  </style>
</head>

<body>

<div id="links">
	<ul>
		<li><a href="http://jrrecordings.com">jrrecordings.com</a></li>
		<li><a href="http://icecoldnugrape.com">icecoldnugrape</a></li>
	</ul>
</div>

<div style="clear:both"></div>

<h1>Recordings for Trade</h1>
<p style="margin-bottom:20px;">
jonathandavidbrink@gmail.com
</p>

<c:set var="recordingtype" value="Show" />
<c:forEach var="recording" items="${recordingList}">
	<c:if test="${recording.recordingType.value ne recordingtype}">
		<div style="margin-top:10px;">&nbsp;</div>
		<c:set var="recordingtype" value="${recording.recordingType.value}" />
	</c:if>
	<div class="recordingline">
		<c:choose>
			<c:when test="${recording.recordingType.value eq 'Show'}">
				<a href="individualRecording?id=${recording.id}">${recording.formattedShortDateString} - ${recording.formattedLocation} - ${recording.formattedVenue}</a>
			</c:when>
			<c:otherwise>
				<ru:recordingTitle recording="${recording}" link="individualRecording?id=${recording.id}" />
			</c:otherwise>
		</c:choose>
		<c:if test="${not empty recording.jonnote}">
			<span class="sidenote">(${recording.jonnote})</span>
		</c:if>
	</div>
</c:forEach>

</body>

</html>