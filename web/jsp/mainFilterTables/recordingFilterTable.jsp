<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="recordingTable" class="display"> 
	<thead> 
		<tr>
			<th title="Click to sort by date">Date</th>
	   		<th title="Click to sort by location">Location</th>
	   		<th title="Click to sort by venue">Venue</th>
	   		<th title="Click to sort by quality">Quality</th>
	   		<th title="Click to sort by type">Type</th>
	   	</tr>
	</thead>
	<tbody>
	<c:choose>
		<c:when test="${not empty recordingList}">
			<c:forEach var="recording" items="${recordingList}">
		 		<c:choose>
		 			<c:when test="${recording.recordingType.value eq 'Album' or recording.recordingType.value eq 'Single' or recording.recordingType.value eq 'Studio Bootleg' or recording.recordingType.value eq 'Interview' or recording.recordingType.value eq 'Compilation' or recording.recordingType.value eq 'TV' or recording.recordingType.value eq 'Radio'}">
				 		<tr>
				 			<td><a href="individualRecording?id=${recording.id}">${recording.formattedShortDateString}</a></td>
				 			<td colspan="3" align="center"><a href="individualRecording?id=${recording.id}" class="albumhighlight">${recording.name}</a></td>
				 			<td align="center"><a href="individualRecording?id=${recording.id}">${recording.recordingType.value}</a></td>
				 		</tr>
				 	</c:when>
				 	<c:otherwise>
				 		<tr>
				 			<td><a href="individualRecording?id=${recording.id}">${recording.formattedShortDateString}</a></td>
				 			<td><a href="individualRecording?id=${recording.id}">${recording.formattedShortLocation}</a></td>
				 			<td><a href="individualRecording?id=${recording.id}">${recording.formattedVenue}</a></td>
				 			<td><a href="individualRecording?id=${recording.id}">${recording.quality.value}</a></td>
				 			<td align="center"><a href="individualRecording?id=${recording.id}">${recording.recordingType.value}</a></td>
				 		</tr>
				 	</c:otherwise>
		 		</c:choose>
		 	</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="5" align="center">No Results</td>
			</tr>
		</c:otherwise>
	</c:choose>
	</tbody>
</table>