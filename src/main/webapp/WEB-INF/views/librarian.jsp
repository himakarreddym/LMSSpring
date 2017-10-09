<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="boot.htm"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div>

			<div class="container" style="margin-top: 1%; text-align: center">
				<div class="form-group">
					${statusMessage}
					<h2>
						<label for="sel1"> Select Branches from list Below: </label>
					</h2>
					<select class="form-group" id="sel1" 
						onchange="javascript:location.href='librarian?branchId='+this.value">
						<loop:forEach items="${branches}" var="b">
				<loop:if test="${branchId == b.branchId}"><option value="${b.branchId}" selected>${b.branchName} </option></loop:if>
				<loop:if test="${branchId != b.branchId}"><option value="${b.branchId}">${b.branchName} </option></loop:if>
						</loop:forEach>
					</select>
					<br />
					<br />
					<button type="button"
						onclick="javascript:location.href='updatebranch?branchId=${branchId}'"
						class="btn btn-info btn-sm">Update Information</button>

				</div>

				<div class="form-group" align="center">
					<br /> <br />
					<h2>
						<label for="sel1"> Select Book and copies from list
							Below: </label>
					</h2>
					<table class="table table-striped">
						<tr>
							<th>Book Name</th>
							<th>Current copies</th>
							<th>Edit</th>
						</tr>

						<loop:forEach var="b" items="${bookcopies}">
							<tr>
								<td><a
									href="bookinf?bookId=${b.bookId}&branchId=${b.branchId}">
										${b.book.title} <br />
								</a></td>
								<td>${b.copies} <br />
								</td>

								<td><button type="button"
										onclick="javascript:location.href='editbookcopies?bookId=${b.bookId}&branchId=${b.branchId}'"
										class="btn btn-primary btn-sm">Update copies</button></td>

							</tr>

						</loop:forEach>
						<tr>
					</table>

				</div>
			</div>

		</div>
	</div>
</div>