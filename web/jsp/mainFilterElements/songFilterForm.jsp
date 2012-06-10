<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/sam" prefix="sam" %>

<input type="hidden" name="pref" value="${param.pref}" />

<sam:getParam var="defaultFirstAppeared" val="${sessionScope.smartSongSearchBean.firstPlayed}" samVal="s_sf_firstAppeared" pref="${param.pref}" />
<sam:getParam var="defaultLastAppeared" val="${sessionScope.smartSongSearchBean.lastPlayed}" samVal="s_sf_lastAppeared" pref="${param.pref}" />
<sam:getParam var="defaultFrequency" val="${sessionScope.smartSongSearchBean.frequency}" samVal="s_sf_frequency" pref="${param.pref}" />
<sam:getParam var="defaultAlbum1" val="${sessionScope.smartSongSearchBean.album1}" samVal="s_sf_album1" pref="${param.pref}" />

<div class="filterform">
	<div class="buttonwrapper searchbutton" style="top:15px;">
		<a class="ovalbutton"><span>Search</span></a>
	</div>
	<div class="clearbutton">Clear</div>
	<div class="filterelements">
		<!-- start of song tab filter -->
		<div class="col">
		    <div class="row">
		      <span class="label">First Appeared</span>
		      <span class="formw">
		      	<select name="firstAppeared">
		      		<option value="">
						All
					</option>
					<c:forEach var="year" begin="1970" end="2011">
					<option value="${year}" <c:if test="${year eq defaultFirstAppeared}">selected</c:if>>
						${year}
					</option>
					</c:forEach>
				</select>
		      </span>
		    </div>
		    <div class="row">
		      <span class="label">Last Appeared</span>
		      <span class="formw">
		      	<select name="lastAppeared">
		      		<option value="">
						All
					</option>
					<c:forEach var="year" begin="1970" end="2011">
					<option value="${year}" <c:if test="${year eq defaultLastAppeared}">selected</c:if>>
						${year}
					</option>
					</c:forEach>
				</select>
		      </span>
		    </div>
		</div>
		<div class="col">
		    <div class="row">
		      <span class="label">How Common:</span>
		      <span class="formw">
		      	<select name="howCommon">
		      		<option value="">
						All
					</option>
					<c:forEach var="frequency" items="${frequencies}">
						<option value="${frequency.value}" <c:if test="${frequency.value eq defaultFrequency}">selected</c:if>>
							${frequency.display}
						</option>
					</c:forEach>
				</select>
		      </span>
		    </div>
		</div>
		<div class="col">
		    <div class="row">
		      <span class="label">Album:</span>
		      <span class="formw">
		      	<select name="album1">
		      		<option value="">
						All
					</option>
					<c:forEach var="album" items="${albums}">
						<option value="${album}" <c:if test="${album eq defaultAlbum1}">selected</c:if>>
							${album}
						</option>
					</c:forEach>
				</select>
		      </span>
		    </div>
		</div>
	 <!-- end of song tab filter -->
	</div>
</div>

<div style="clear:both"></div>