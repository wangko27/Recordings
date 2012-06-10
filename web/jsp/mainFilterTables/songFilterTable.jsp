<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="songTable" class="display"> 
 <thead> 
  <tr>
	   <th style="width:30%" title="Click to sort by song">Song</th>
	   <th title="Click to sort by album">Album</th>
	   <th style="width:7%" title="Click to sort by first played">Started</th>
	   <th style="width:7%" title="Click to sort by last played">Stopped</th>
	   <th style="width:7%" title="Click to sort by how common">Rareness</th>
	   <th style="width:7%" title="Click to sort by the number of recordings that exist of this song">Count</th>
  </tr>
 </thead>
 <tbody>
	 <c:choose>
		<c:when test="${not empty songList}">
			<c:forEach var="song" items="${songList}">
				<tr> 
					<td><a href="song?id=${song.id}">${song.value}</a></td>
					<td>
						<a href="song?id=${song.id}">
						<c:if test="${not empty song.album1}">${song.album1}</c:if><c:if test="${not empty song.album2}">, ${song.album2}</c:if><c:if test="${not empty song.album3}">, ${song.album3}</c:if>
						</a>
					</td>
					<td align="center"><a href="song?id=${song.id}">${song.firstPlayed}</a></td>
					<td align="center"><a href="song?id=${song.id}">${song.lastPlayed}</a></td>
					<td align="center"><a href="song?id=${song.id}">${song.frequencyDisplay}</a></td>
					<td align="center"><a href="song?id=${song.id}">${song.count}</a></td>
				</tr> 
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="6" align="center">No Results</td>
			</tr>
		</c:otherwise>
	</c:choose>
 </tbody>
</table>
