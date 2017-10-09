<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="ref.html"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div>

			<div class="container" style="text-align: center;">

				<form method="post" action="editauthor">
					${statusMessage} <br />Enter Author Name to Edit: <input
						type="text" name="authorName" value="${authorName}" /> <br /> <input
						type="hidden" name="authorId" value="${authorId}" /> <br /> <label
						for="sel1">Select Books from list Below: </label><br>
					<br>
					<div class="form-group"
						style="display: inline-block; padding-left: 5%">
						<select class="form-group" id="sel1" multiple="multiple" size="10"
							name="bookIds">
							<loop:forEach items="${books}" var="b">
								<option value="${b.bookId}"> ${b.title} </option>
							</loop:forEach>
						</select>
					</div>
					<br /> <br />
					<button type="submit" class="btn btn-primary btn-sm">Update
						Author</button>
					<br /> <br />
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
										onclick="javascript:location.href='deleteAuthorBook?authorId=${authorId}&bookId=${ab.bookId}'"
										class="btn btn-danger btn-sm">Delete</button>
								</td>
							</tr>
						</loop:forEach>
					</table>
					<br />
				</form>


			</div>

		</div>
	</div>
</div>
