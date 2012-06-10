<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/formelement" prefix="fe"%>
<%@ taglib uri="/recordingsutil" prefix="ru"%>

<c:import url="prevnext.jsp"/> 

<style>
	#edittable {
		text-align:left;
	}
</style>

		<form method="POST" action="editIndividualRecording?id=${param.id}">
		
		<input type="hidden" name="recordingId" value="${recording.id}" />
		
		<!-- display title -->
		<h2>
			<ru:recordingTitle recording="${recording}" link="individualRecording?id=${param.id}" />
		</h2>
		
		<input type="button" name="scrollToBottom" value="bottom" class="basicnav" />
		
		<table id="edittable">
			<tr>
				<th>Month</th>
				<td>
					<select name="month" class="shortinput">
						<option value="" <c:if test="${empty recording.month}">selected</c:if>>
							?
						</option>
						<c:forEach var="count" begin="1" end="12">
						<option value="${count}" <c:if test="${count eq recording.month}">selected</c:if>>
							<fmt:parseDate var="d" value="${count}/1/1999" type="date" dateStyle="short" />
							<fmt:formatDate value="${d}" pattern="MMMM" />
						</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>Date</th>
				<td>
					<select name="date" class="shortinput">
						<option value="" <c:if test="${empty recording.date}">selected</c:if>>
							?
						</option>
						<c:forEach var="count" begin="1" end="32">
						<option value="${count}" <c:if test="${count eq recording.date}">selected</c:if>>
							${count}
						</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>Year</th>
				<td>
					<select name="year" class="shortinput">
						<option value="" <c:if test="${empty recording.year}">selected</c:if>>
							?
						</option>
						<c:forEach var="count" begin="1965" end="2040">
						<option value="${count}" <c:if test="${count eq recording.year}">selected</c:if>>
							${count}
						</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>Venue</th>
				<td>
					<select name="venueid" class="mediuminput">
						<option value="" <c:if test="${empty recording.venue}">selected</c:if>>
							?
						</option>
						<c:forEach var="venue" items="${venues}">
						<option value="${venue.id}" <c:if test="${venue eq recording.venue}">selected</c:if>>
							${venue.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newVenue" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Quality</th>
				<td>
					<select name="qualityid" class="mediuminput">
						<option value="" <c:if test="${empty recording.quality}">selected</c:if>>
							?
						</option>
						<c:forEach var="quality" items="${qualities}">
						<option value="${quality.id}" <c:if test="${quality eq recording.quality}">selected</c:if>>
							${quality.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newQuality" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Format</th>
				<td>
					<select name="formatid" class="mediuminput">
						<option value="" <c:if test="${empty recording.format}">selected</c:if>>
							?
						</option>
						<c:forEach var="format" items="${formats}">
						<option value="${format.id}" <c:if test="${format eq recording.format}">selected</c:if>>
							${format.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newFormat" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Recording Type</th>
				<td>
					<select name="recordingtypeid" class="mediuminput">
						<option value="" <c:if test="${empty recording.recordingType}">selected</c:if>>
							?
						</option>
						<c:forEach var="recordingType" items="${recordingTypes}">
						<option value="${recordingType.id}" <c:if test="${recordingType eq recording.recordingType}">selected</c:if>>
							${recordingType.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newRecordingType" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Media</th>
				<td>
					<select name="mediaid" class="mediuminput">
						<option value="" <c:if test="${empty recording.media}">selected</c:if>>
							?
						</option>
						<c:forEach var="media" items="${medias}">
						<option value="${media.id}" <c:if test="${media eq recording.media}">selected</c:if>>
							${media.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newMedia" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>City</th>
				<td>
					<select name="cityid" class="mediuminput">
						<option value="" <c:if test="${empty recording.city}">selected</c:if>>
							?
						</option>
						<c:forEach var="city" items="${cities}">
						<option value="${city.id}" <c:if test="${city eq recording.city}">selected</c:if>>
							${city.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newCity" class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Country</th>
				<td>
					<select name="countryid" class="mediuminput">
						<option value="" <c:if test="${empty recording.country}">selected</c:if>>
							?
						</option>
						<c:forEach var="country" items="${countries}">
						<option value="${country.id}" <c:if test="${country eq recording.country}">selected</c:if>>
							${country.value}
						</option>
						</c:forEach>
					</select>
					<input type="text" name="newCountry"  class="mediuminput" />
				</td>
			</tr>
			<tr>
				<th>Name</th>
				<td>
					<input type="text" name="name" value="${recording.name}" class="mediuminput" />
					&nbsp;
					<input type="submit" value="Submit Recording Changes" />
				</td>
			</tr>
			<tr>
				<th>Jon</th>
				<td>
					<input type="checkbox" name="jon" <c:if test="${recording.jon eq 1}">checked</c:if> />
					&nbsp;
					<input type="text" name="jonnote" class="mediuminput" value="${recording.jonnote}" />
				</td>
			</tr>
		</table>
		
		<script type="text/javascript">
			$(document).ready(function()
			{
				$("#deletediv").click( function() {
					$("#NOlink").show();
					$("#NOlink").append("<br />");
					$("#deletelink").show();
					$("#areyousure").show();
					$("#deletediv").hide();
				});
			});	
		</script>
		
		<div id="deletediv" style="display:inline;">delete</div>
		<div id="areyousure" style="display:none;">Are you sure?</div>
		<a id="NOlink" style="display:none;color:blue;font-size:140%" href="editIndividualRecording?id=${recording.id}">NO, Don't Delete!</a>
		<a id="deletelink" style="display:none;color:red;font-size:60%" href="deleteRecording?id=${recording.id}">Yes, Delete This Recording</a>
		
		<br />
		
		<div id="editSongInstances">
		<table>
			<tr>
				<th>&nbsp;</th>
				<th>Track</th>
				<th>Song</th>
				<th>Set</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach var="songInstance" items="${recording.songInstances}" varStatus="loopStatus">
				<tr id="${songInstance.id}" <c:if test="${songInstance.section ge 2}">style="background-color:silver"</c:if>>
					<td>
						<c:choose>
							<c:when test="${loopStatus.last}">
								<input type="button" value="x" />
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<input type="button" name="down" value="-" />
						<input type="text" name="trackListing" value="${songInstance.trackListing}" style="width:20px" />
						<input type="button" name="up" value="+" />
					</td>
					<td>
						<select name="songId" style="width:300px;" tabindex="${loopStatus.count}">
							<option value="">
							</option>
							<c:forEach var="song" items="${songs}">
								<option value="${song.id}" <c:if test="${songInstance.song.id eq song.id}">selected</c:if>>
									${song.value}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="text" name="section" value="${songInstance.section}" style="width:20px" />
					</td>
				</tr>	
			</c:forEach>
			<tr id="add">
				<td>
					&nbsp;
				</td>
				<td>
					<input type="button" name="down" value="-" />
					<input type="text" name="trackListing" value="" style="width:20px" />
					<input type="button" name="up" value="+" />
				</td>
				<td>
					<select name="songId" style="width:300px;" tabindex="998">
						<option value="">
						</option>
						<c:forEach var="song" items="${songs}">
							<option value="${song.id}">
								${song.value}
							</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="text" name="section" value="" style="width:20px" />
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
		</div>
		
		<input type="button" name="saveSongInstances" value="Save Song Instances" tabindex="999" />
		<input type="button" name="reorder" value="Re-order Tracklistings" />
		<input type="button" name="refresh" value="Refresh" />
		<input type="button" name="scrollToTop" value="top" class="basicnav" />
		<a href="individualRecording?id=${param.id}">page</a>
		
		<br /><br /><br />
		
		<div style="background-color:silver;border:1px solid black;padding:3px;float:left">
			Add blank songs: <input type="textbox" style="width:20px" id="blanksongs" value="0" />
			<input type="button" value="add" id="addblanksongssubmit" />
		</div>
		
		<br /><br />
		</form>
