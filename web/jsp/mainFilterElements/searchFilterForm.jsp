<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="searchdiv">
	Start typing a song, album, or location: 
	<c:choose>
		<c:when test="${not empty sessionScope.quickSearchBean.value and sessionScope.quickSearchBean.value != 'undefined'}"> 
			<input type="text" name="quicksearch"  id="quicksearchtextbox" value="${sessionScope.quickSearchBean.value}" />
		</c:when>
		<c:otherwise>
			<input type="text" name="quicksearch"  id="quicksearchtextbox" value="" />
		</c:otherwise>
	</c:choose>
	
	<input type="button" name="clearquicksearch" value="Clear" />
</div>