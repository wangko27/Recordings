<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/sam" prefix="sam" %>

<div>

<c:if test="${(embeddedPage eq 'individualRecording.jsp' || embeddedPage eq 'editIndividualRecording.jsp'|| embeddedPage eq 'song.jsp') && not empty sessionScope.recordingSearchBean}">
	<a href="home?pref=val">
	<span id="previoussearch">
	Previous Search
	</span>
	</a>
</c:if>

<h4>Find</h4>
<ul class="navlist"> 
	<li><a href="home?<sam:getParamString prefix="tab" name="defaultTab" val="Recordings" />&pref=sam">Recordings</a></li>
	<li><a href="home?<sam:getParamString prefix="tab" name="defaultTab" val="Songs" />&pref=sam">Songs</a></li>
	<li><a href="home?<sam:getParamString prefix="tab" name="defaultTab" val="Search" />&pref=sam">Search</a></li>
</ul>
</div>

<h4>Internal Links</h4>
<ul class="navlist">
	<li><a href="home?<sam:getParamString prefix="rf" name="recordingType" val="Album" />&<sam:getParamString prefix="tab" name="defaultTab" val="Recordings" />&pref=sam">Albums</a></li>
	<li><a href="graphs">Graphs</a></li>
	<li><a href="randomRecording">Random Recording</a></li>
	<li><a href="trade">Trade</a></li>
	<li><a href="about">About</a></li>
</ul>

<h4>External Links</h4>
<ul class="navlist">
	<li><a href="http://icecoldnugrape.com">icecoldnugrape.com</a></li>
	<li><a href="http://www.bobsnerdywebpage.com" title="Bob's site is the inspiration for this site! Put a message on his message board...go to his '2000s to current' page">Bob's Site</a></li>
	<li><a href="http://jojofiles.blogspot.com">jojoblog</a></li>
	<li><a href="http://homepage.mac.com/ramonrempel/JoJo/index.html">Ramon's Lyrics &amp; Tabs</a></li>
	<li><a href="http://www.highroadtouring.com/artists/jonathanrichman/">HighRoad Touring</a></li>
	<li><a href="http://www.gbvdb.com/home.asp">gbvdb.com</a></li>
</ul>

<c:if test="${not empty authenticated}">
<h4 style="color:red">Admin</h4>
<ul class="navlist" id="adminlist">
	<li><a href="createRecording">Create Recording</a></li>
	<li><a href="lookupValues">LK Manager</a></li>
</ul>
</c:if>
