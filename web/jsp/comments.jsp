<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% // attribute that is set by external controllers: commentSource  %>
<% // and commentData %>
<% // assumes jQuery is already loaded (should be true)  %>

<link rel="stylesheet" type="text/css" href="web/css/comments.css"/>
<script type="text/javascript" src="web/js/comments.js"></script>

<div id="comments">
	<form id="postcomment">
		<input type="hidden" id="commentSource" value="${commentSource}"/>
		<input type="hidden" id="commentSourceId" value="${commentSourceId}"/>
		<input type="hidden" id="commentControllerUrl" value="${commentControllerUrl}"/> 
		
		<div id="commentsposting" class="common_panel">
			<div id="commentheader">
				<div class="votes common_panel">
					<h2>Recommend?</h2>
					<img src="web/img/thumb-up.png" alt="Button to indicate Yes! for recommending" title="Yes" />
					<span>${commentSourceObject.upvotes - commentSourceObject.downvotes}</span>
					<img src="web/img/thumb-down.png" alt="Button to indicate No! for recommending" title="No" />
					<div>Processing...</div>
				</div>
			</div>
			<div style="float:left">
				<table>
					<tr>
						<th>Name:</th>
						<td>
							<input type="text" id="inusername" /> &nbsp;
						</td>
					</tr>
					<tr>
						<th>Thoughts:</th>
						<td><textarea id="intext" rows="2" cols="50%"></textarea></td>
					</tr>
					<tr>
						<td colspan="2" style="text-align:center">
							<input id="makenewpost" type="button" value="Post" />
						</td>
					</tr>
				</table>
			</div>
			<div style="clear:both"></div>
			<!-- <textarea id="securityanswer" name="name" style="display:none"></textarea>  -->
			<div class="error" style="display:none"><p id="errormessage"></p></div>
		</div> <!-- end of commentsposting -->
	</form>
	
	<div id="commentContent">
		<c:forEach var="comment" items="${commentData}">
			<c:if test="${fn:startsWith(comment.text,'SKIP') eq false}">
			<div id="comment_${comment.id}" class="comment common_panel comment${comment.bob}">				
				<!-- upvote -->
				<div class="commentVotes">
					${comment.voteRank}<input type="hidden" id="commentid" value=${comment.id}>
					<div>Processing</div>
				</div>
				
				<!-- if admin, delete button -->
				<c:if test="${not empty authenticated}">
					<input type="button" value="delete" id="${comment.id}" />
				</c:if>
				
				<!-- if admin, edit button -->
				<c:if test="${not empty authenticated}">
					<input type="button" value="edit" id="${comment.id}" />
				</c:if>
				
				<!-- username -->
				<div class="commentName">${comment.username}</div>
				
				<!-- don't show time if it's Bob's comment -->
				<c:if test="${comment.bob ne 1}">
					<div class="commentDate"><fmt:formatDate value="${comment.timestamp}" pattern="MMM d, yyyy hh:mma" /></div>
				</c:if>
				
				<div style="clear:both"></div>
				
				<!-- the comment text -->
				<div class="commentText ${comment.id}">
					${comment.text}
				</div>
				
				<div style="clear:both"></div>
				
				<div id="commenterror_${comment.id}" class="error" style="display:none"></div>
			</div>
			</c:if>
		</c:forEach>
	</div>
</div> <!-- end of comments div -->
