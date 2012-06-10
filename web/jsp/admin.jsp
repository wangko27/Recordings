<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="admin">
	<c:choose>
		<c:when test="${not empty authenticated}">				
			<div id="loggedin">
				Logged in. <input type="button" id="logout" value="log-out"/> 
			</div> 
		</c:when>
	
		<c:otherwise>					
			<div class="admintoggle">
	   			Welcome, Guest. <a>Login</a>
			</div>		
				
			<div id="login" style="display:none">
				<form id="loginform">:
					<input type="password" id="password"/><input type="button" id="authenticate" value="Go"/>
				</form>				
			</div> 		
		</c:otherwise>
	</c:choose>

	<div id="error" class="error" style="display:none">
		Sorry, we couldn't log you in 
	</div>	
	
	<div id="progress" class = "progress" style="display:none">
		Loading...
	</div>
</div>
