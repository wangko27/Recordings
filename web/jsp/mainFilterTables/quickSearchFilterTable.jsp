<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table id="quickSearchTable" class="display"> 
 <thead> 
  <tr>
	   <th title="Click to sort by match">Matches</th>
  </tr>
 </thead>
 <tbody>
 	<c:choose>
		<c:when test="${not empty quickSearchBeanList}">
		 	<c:forEach var="quickSearchBean" items="${quickSearchBeanList}">
				<tr> 
					<c:set var="link" value="" />
					<c:choose>
						<c:when test="${quickSearchBean.category eq 'song'}">
							<c:set var="link" value="song?id=${quickSearchBean.id}" />
						</c:when>
						<c:otherwise>
							<c:set var="link" value="individualRecording?id=${quickSearchBean.id}" />
						</c:otherwise>
					</c:choose>
					<td><a href="${link}">${quickSearchBean.formattedValue} <em>(${quickSearchBean.category})</em></a></td>
				</tr> 
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="1" align="center">No Results</td>
			</tr>
		</c:otherwise>
	</c:choose>
 </tbody>
</table>
