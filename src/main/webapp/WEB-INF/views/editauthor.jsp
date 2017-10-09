<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="ref.html"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>


<div class="container" style="text-align:center;">

	<form:form method="post" action="editAuthor" modelAttribute="Author">
		${statusMessage}
		<br />Enter Author Name to Edit: 
		<form:input type="text" path="authorName" value="${Author.authorName}" />
		<br />
		<form:input type="hidden" path="authorId" value="${Author.authorId}" />
		<br />
		<label for="sel1">Select Books from list Below: </label>
		<div class="form-group"
			style="display: inline-block; padding-left: 5%">
			<select class="form-group" id="sel1" multiple="multiple" size="10"
				name="bookIds">
				<loop:forEach items="${books}" var="b">
					<%-- <loop:when test="${authorBooks.contains(b) }"></loop:when>
					<loop:otherwise> --%>
						<option value="${b.bookId}"> ${b.title} </option>
					<%-- </loop:otherwise> --%>
				</loop:forEach>
			</select>
		</div>
		<br />
		<br />
		<button type="submit" class="btn btn-primary btn-sm">Update
			Author</button>
		<br />
		<br />
		<table class="table table-striped">
			<tr>
				<th>Books Written by the Author</th>
				<th>Delete</th>
			</tr>
			<loop:forEach items="${authorBooks}" var="ab">
				<tr>
					<td>${ab.title}</td>
					<td>
						<button type="button"
							onclick="javascript:location.href='deleteAuthorBook?authorId=${Author.authorId}'&bookId=${b.bookId}"
							class="btn btn-danger btn-sm">Delete</button>
					</td>
				</tr>
			</loop:forEach>
		</table>
		<br />
	</form:form>


</div>
    