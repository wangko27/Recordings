<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="lkpsuccess"></div>

<style>
	h2 {
		display:inline;
		margin-right:5px;
	}
	table.lookupvaluetable {
		width:900px;
	}
	table.lookupvaluetable input, table.lookupvaluetable select {
		font-size:70%;
	}
	table.lookupvaluetable th {
		width:6%;
		font-size:75%;
		text-align:left;
		border:1px solid silver;
	}
	table.lookupvaluetable td {
		border:1px solid silver;
		text-align:center;
		padding:1px;
	}
	table.lookupvaluetable th, table.lookupvaluetable td {
		border-right-width:0px;
		border-left-width:0px;
		border-bottom-width:0px;
	}
	table.lookupvaluetable th {
		border-left:1px solid silver;
	}
	table.lookupvaluetable select {
		width:250px;
	}
</style>

<h2><a href="lookupValues">Lookup Value Manager</a></h2><a href="lookupValues">Cancel</a>

<div id="lookupValueEditSection">
<c:forEach var="lookupValueList" items="${lookupValues}" varStatus="index">
	<table class="lookupvaluetable">
		<tr>
			<th>${categories[index.count-1]}</th>
			<td>
				<select name="originalId" <c:if test="${categories[index.count-1] eq 'Song'}">size="10"</c:if>>
					<c:forEach var="lookupValue" items="${lookupValueList}">
					<option value="${lookupValue.id}"
						<c:if test="${categories[index.count-1] eq category and lookupValue.id eq mergeId}">
							selected="selected"
						</c:if>
					>
						${lookupValue.value}
					</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="text" name="updatedValue" style="width:180px;"	/>
				<input type="button" value="edit" style="width:30px;" />
			</td>
			<td>
				<select name="mergeId" <c:if test="${categories[index.count-1] eq 'Song'}">size="10"</c:if>>
					<c:forEach var="lookupValue" items="${lookupValueList}">
					<option value="${lookupValue.id}">
						${lookupValue.value}
					</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<input type="button" value="merge" style="width:40px;" />
			</td>
		</tr>
	</table>
</c:forEach>
</div>

<br />

<style>
table.notused {
	width:600px;
}
table.notused tr:hover {
	background-color:silver;
}
table.notused th, table.notused td {
	border:1px solid silver;
}
</style>

<h3>Not-Used Lookup Values</h3>

<div id="notusedsection">
<table class="notused">
	<tr>
		<th style="width:40px;">&nbsp;</th>
		<th style="width:40px;text-align:center">id</th>
		<th style="width:20%">category</th>
		<th style="text-align:left">value</th>
	</tr>
	<c:forEach var="notUsed" items="${notUsedSimpleValues}">
	<tr>
		<td style="width:40px;text-align:center"><input type="button" value="x" /></td>
		<td style="width:40px;text-align:center">${notUsed.id}</td>
		<td style="text-align:center">${notUsed.category}</td>
		<td>${notUsed.value}</td>
	</tr>
	</c:forEach>
</table>

<h3>Add Song</h3>
<input type="text" name="newsong" />
<input type="button" value="add song" id="addsongbutton" />
</div>
