<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/recentchanges" prefix="rc"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet"  type="text/css" href="css/recentchanges.css"/>
<script type="text/javascript" src="js/jquery.scrollTo-1.4.2-min.js"></script>
<script type="text/javascript" src="js/jquery.serialScroll-1.2.2-min.js"></script>
<script type="text/javascript" src="js/recentchanges.js"></script>  	

<div id="recentchanges">
	<p class="title">Recent Changes</p>	
	
	<div id="changelist">		
		<span class="loading">Loading...</span>
	
		<!--  loaded via AJAX  -->
		<c:forEach var="item" items="${recentchanges}">
			<c:if test="${fn:startsWith(item.summary,'SKIP') eq false}">
			<div class="changeitem common_darkerbrown">
				<span class="itemtitle">${item.title}</span> 
				<p class="summary common_panel">
					<rc:recentChangesFormatter item="summary" summary="${item.summary}" summaryLength="145"/>   
				</p>
				<span class="info"><rc:recentChangesFormatter item="summary" summary="${item.author}" summaryLength="5"/> On <rc:recentChangesFormatter item="date" date="${item.publishDate }"/> </span>
				<p class="link" style="display:none">${item.link}</p>
			</div>	
			</c:if>			
		</c:forEach>
	</div>

	<!-- 
		<input type="button" id="up" value="Up"/>
		<input type="button" id="down" value="Down"/>
	-->
</div>
