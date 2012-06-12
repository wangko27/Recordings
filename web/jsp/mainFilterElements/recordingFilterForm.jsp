<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/sam" prefix="sam" %>

<input type="hidden" name="pref" value="${param.pref}" />

<sam:getParam var="defaultYear" val="${sessionScope.recordingSearchBean.recording.year}" samVal="s_rf_year" pref="${param.pref}" />
<sam:getParam var="defaultMonth" val="${sessionScope.recordingSearchBean.recording.month}" samVal="s_rf_month" pref="${param.pref}" />
<sam:getParam var="defaultDate" val="${sessionScope.recordingSearchBean.recording.date}" samVal="s_rf_date" pref="${param.pref}" />
<sam:getParam var="defaultCountry" val="${sessionScope.recordingSearchBean.recording.country.value}" samVal="s_rf_country" pref="${param.pref}" />
<sam:getParam var="defaultCity" val="${sessionScope.recordingSearchBean.recording.city.value}" samVal="s_rf_city" pref="${param.pref}" />
<sam:getParam var="defaultVenue" val="${sessionScope.recordingSearchBean.recording.venue.value}" samVal="s_rf_venue" pref="${param.pref}" />
<sam:getParam var="defaultRecordingType" val="${sessionScope.recordingSearchBean.recording.recordingType.value}" samVal="s_rf_recordingType" pref="${param.pref}" />
<sam:getParam var="defaultQuality" val="${sessionScope.recordingSearchBean.recording.quality.value}" samVal="s_rf_quality" pref="${param.pref}" />

<jsp:useBean id="currentDate" class="java.util.Date" />
<fmt:formatDate var="currentYear" value="${currentDate}" pattern="yyyy" />

<div class="filterform">
	<div class="buttonwrapper searchbutton" style="top:25px;">
		<a class="ovalbutton"><span>Search</span></a>
	</div>
	<div class="clearbutton">Clear</div>
	<div class="filterelements">
		<!-- start of recordings tab filter -->
		<div class="shortcol">
		   <div class="row">
			 <span class="label">Year</span>
			 <span class="formw">
				<select name="year">
					<option value="">All</option>
					<c:forEach var="year" begin="1970" end="${currentYear}">
						<option value="${year}" <c:if test="${year eq defaultYear}">selected</c:if>>
							${year}
						</option>
					</c:forEach>
				</select>
			 </span>
		   </div>
		   <div class="row">
			 <span class="label">Month</span>
			 <span class="formw">
				<select name="month" class="shortinput">
					<option value="">All</option>
					<c:forEach var="count" begin="1" end="12">
						<option value="${count}" <c:if test="${count eq defaultMonth}">selected</c:if>>
							<fmt:parseDate var="d" value="${count}/1/1999" type="date" dateStyle="short" />
							<fmt:formatDate value="${d}" pattern="MMMM" />
						</option>
					</c:forEach>
				</select>
			 </span>
		   </div>
		   <div class="row">
			 <span class="label">Date</span>
			 <span class="formw">
				<select name="date">
					<option value="">All</option>
					<c:forEach var="date" begin="1" end="32">
						<option value="${date}" <c:if test="${date eq defaultDate}">selected</c:if>>
							${date}
						</option>
					</c:forEach>
				</select>
			  </span>
			</div>
		</div>
		<div class="col longinput">
			<div class="row">
			  <span class="label">Country</span>
			  <span class="formw">
				<select name="country">
					<option value="">All</option>
					<c:forEach var="country" items="${countries}">
						<option value="${country.value}" <c:if test="${country eq defaultCountry}">selected</c:if>>
							${country.value}
						</option>
					</c:forEach>
				</select>
			 </span>
		   </div>
		   <div class="row">
			 <span class="label">City</span>
			 <span class="formw">
				<select name="city">
					<option value="">All</option>
					<c:forEach var="city" items="${cities}">
						<option value="${city.value}" <c:if test="${city eq defaultCity}">selected</c:if>>
							${city.value}
						</option>
					</c:forEach>
				</select>
			  </span>
			</div>
		</div>
		<div class="col longinput">
			<div class="row">
			  <span class="label">Venue</span>
			  <span class="formw">
				<select name="venue">
					<option value="">All</option>
					<c:forEach var="venue" items="${venues}">
						<option value="${venue.value}" <c:if test="${venue eq defaultVenue}">selected</c:if>>
							${venue.value}
						</option>
					</c:forEach>
				</select>
			 </span>
		   </div>
		   <div class="row">
			 <span class="label">Type</span>
			 <span class="formw">
				<select name="recordingType">
					<option value="">All</option>
					<c:forEach var="recordingType" items="${recordingTypes}">
						<option value="${recordingType.value}" <c:if test="${recordingType eq defaultRecordingType}">selected</c:if>>
							${recordingType.value}
						</option>
					</c:forEach>
				</select>
			 </span>
		   </div>
		   <div class="row">
			 <span class="label">Quality</span>
			 <span class="formw">
				<select name="quality">
					<option value="">All</option>
					<c:forEach var="quality" items="${qualities}">
						<option value="${quality.value}" <c:if test="${quality eq defaultQuality}">selected</c:if>>
							${quality.value}
						</option>
					</c:forEach>
				</select>
			  </span>
			</div>
		</div>
	  <!-- end of recordings tab filter -->
	</div>
</div>

<div style="clear:both"></div>