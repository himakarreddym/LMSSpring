<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="boot.htm"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="boot.htm"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">

		<div class="container">
			<div style="padding: 20px;">
				<div class="form-group" align="center">
					${statusMessage}
					<h2>
						<label for="sel1"> Select Branches from list Below: </label>
					</h2>
					<select class="form-group" id="sel1"
						onchange="javascript:location.href='borrowermenu?cardNo=${cardNo}&branchId='+this.value">
						<loop:forEach items="${branches}" var="b">
							<loop:if test="${branchId == b.branchId}">
								<option value="${b.branchId}" selected>${b.branchName} </option>
							</loop:if>
							<loop:if test="${branchId != b.branchId}">
								<option value="${b.branchId}">${b.branchName} </option>
							</loop:if>
							<!-- <loop:if test="${branchId == b.branchId}">selected="selected"</loop:if> -->
						</loop:forEach>
					</select>
				</div>
			</div>


			<div class="form-group" align="center"
				style="width: 44%; float: left; padding-right: 10px;">
				<br /> <br />
				<h4>
					<label for="sel1"> Books available in this branch </label>
				</h4>

				<table class="table table-striped">
					<tr>
						<th>Book Name</th>
						<th>Current copies</th>
						<th>Edit</th>
					</tr>
					<loop:forEach var="b" items="${bookcopies}">
						<tr>
							<td>${b.book.title}<br /></td>
							<td>${b.copies}<br />
							</td>
							<td><form:form method="post" action="checkbook"
									modelAttribute="BookLoans">
									<form:input type="hidden" path="bookId"
										value="${b.book.bookId}" />
									<form:input type="hidden" path="branchId"
										value="${BookLoans.branchId}" />
									<form:input type="hidden" path="cardNo"
										value="${BookLoans.cardNo}" />
									<button type="submit" class="btn btn-primary btn-sm">CheckOut</button>
								</form:form></td>
						</tr>

					</loop:forEach>
				</table>
			</div>

			<div class="form-group" align="center"
				style="width: 56%; float: left;">
				<br /> <br />
				<h4>
					<label for="sel1"> Books you borrowed in this branch </label>
				</h4>
				<table class="table table-striped">
					<tr>
						<th>Book Name</th>
						<th>Date out</th>
						<th>Due date</th>
						<th>Edit</th>
					</tr>

					<loop:forEach var="bl" items="${bookloans}">
						<tr>
							<td>${bl.book.title}<br /></td>
							<td>${bl.dateOut}<br /></td>
							<td>${bl.dueDate}<br /></td>
							<td><form:form method="post" action="checkInBook"
									modelAttribute="BookLoans">
									<form:input type="hidden" path="bookId"
										value="${bl.book.bookId}" />
									<form:input type="hidden" path="branchId"
										value="${bl.branchId}" />
									<form:input type="hidden" path="cardNo" value="${bl.cardNo}" />
									<input type="hidden" name="dateout" value="${bl.dateOut}" />
									<input type="hidden" name="duedate" value="${bl.dueDate}" />
									<button type="submit" class="btn btn-primary btn-sm">CheckIn</button>
								</form:form></td>

						</tr>

					</loop:forEach>
				</table>
			</div>
		</div>
	</div>
</div>