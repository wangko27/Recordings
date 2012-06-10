<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-GB">
<head>
	<meta name="description" content="Welcome to the interactive Jonathan Richman recording database! Browse recordings, recommend, and comment. "/> 
	<meta name="keywords" content="Jonathan, Richman, Recording, Concert"/> 

	<title>Jonathan Richman Recordings</title>
	<c:forEach var="cssInclude" items="${embeddedCSS}">
	<link rel="stylesheet"  type="text/css" href="${cssInclude}"/>  
	</c:forEach>  
	<link rel="stylesheet"  type="text/css" href="css/pageshell.css"/>
	<link rel="stylesheet"  type="text/css" href="css/header.css"/>
	<link rel="stylesheet"  type="text/css" href="css/admin.css"/>
	<link rel="stylesheet"  type="text/css" href="css/common.css"/>	
		
	<c:forEach var="jsInclude" items="${embeddedJs}">
		<script type="text/javascript" charset="utf-8" src="<c:out value="${jsInclude }"/>"> </script>  
	</c:forEach>
	<script type="text/javascript" src="js/pageshell.js"></script>
	<script type="text/javascript" src="js/admin.js"></script>		  	
	
	<!--[if lt IE 9]>
		<script src="http://ie7-js.googlecode.com/svn/version/2.1(beta4)/IE9.js"></script>
	<![endif]-->
	
</head>
<body>
	<c:if test="${not empty errorMessage}">
	<div id="error" class="common_errorcolor">
		<p>${errorMessage}</p>
	</div>
	</c:if>

	
<div id="header">
	<c:import url="header.jsp"/>
</div>
<div id="subheader">
	<div><c:import url="admin.jsp"/></div>
	<p>
		This is the interactive Jonathan Richman recording database
		
		<c:if test="${not empty authenticated }">
			<c:import url="build.jsp"/>	  
		</c:if>
	</p>		
</div>
<div id="belowheader" class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<c:import url="${embeddedPage}" />
			<div class="errorbox common_errorcolor" id="errorpopup"><div id="message"></div><div id="close">(Click to close)</div></div>
		</div>
		<div class="col2">
			<c:import url="leftNavigation.jsp" />
			<c:import url="recentchanges.jsp"/> 
			<div id="sidepics">
				<img src="img/sidePic1.png" />
			</div>
		</div>
	</div>
</div>
<div id="footer">
	<c:import url="footer.jsp"/>
</div>

</body>
</html>
