<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="prevnext">
	<c:if test="${not empty nextId}">
		<a href="individualRecording?id=${nextId}">
			<img src="web/img/forward_enabled.jpg" class="arrowbutton" 
				 alt="Next ${recordingType}" title="Next ${recordingType}" />
		</a>
		
		<a href="individualRecording?id=${nextId}">Next ${recordingType}</a>
	</c:if>

	<c:if test="${not empty previousId}">
		<a href="individualRecording?id=${previousId}" class="prevlink">Previous ${recordingType}</a>
		<a href="individualRecording?id=${previousId}">
			<img src="web/img/back_enabled.jpg" class="arrowbutton"
				 alt="Previous ${recordingType}" title="Previous ${recordingType}" />
		</a>
	</c:if>
</div>
