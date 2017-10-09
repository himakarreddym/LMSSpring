<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="boot.htm"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>

<div class="container">

	<form:form method="post" action="editBook" modelAttribute="Book">
		${statusMessage} <br />Enter new Book Title : 
		<form:input type="text" path="title" /><br />
		 <form:input type="hidden" path="bookId" /><br />
		<div style="width: 100%; float: left;">

			<div class="form-group" align="center"
				style="width: 50%; display: inline-block; float: left;">
				<label for="sel1">Select new Books from list Below: </label> <select
					class="form-group" id="sel1" multiple="multiple" size="10"
					name="authorIds" style="width: 80%;">
					<loop:forEach var="a" items="${authors}">
						<option value="${a.authorId}">${a.authorName}</option>
					</loop:forEach>
				</select>
			</div>

			<div align="center"
				style="width: 50%; display: inline-block; float: left;">
				<table class="table table-striped">
					<tr>
						<th>Books Written by the Author</th>
						<th>Delete</th>
					</tr>
					<loop:forEach var="a" items="${authorBooks}">
						<tr>
							<td>${a.authorName} <br />
							</td>
							<td><button type="button"
									onclick="javascript:location.href='deleteBookAuthor?authorId=${a.authorId}&bookId=${book.bookId}'"
									class="btn btn-danger btn-sm">Delete</button></td>
						</tr>
					</loop:forEach>
				</table>

			</div>
		</div>
		<br />
		<div style="width: 100%; float: left; margin-top: 40px;">
			<div class="form-group" align="center"
				style="width: 50%; display: inline-block; float: left;">
				<label for="sel1">Select new Genres from list Below: </label> <select
					class="form-group" id="sel1" multiple="multiple" size="10"
					name="genreIds" style="width: 80%;">
					<loop:forEach var="g" items="${genres}">
						<option value="${g.genreId}">${g.genreName}</option>
					</loop:forEach>
				</select>
			</div>

			<div align="center"
				style="width: 50%; display: inline-block; float: left;">
				<table class="table table-striped">
					<tr>
						<th>Available Genres for the Book</th>
						<th>Delete</th>
					</tr>
					<loop:forEach var="gb" items="${bookGenres}">
						<tr>
							<td>${gb.genreName} <br />
							</td>
							<td><button type="button"
									onclick="javascript:location.href='deleteGenreBook1?genreId=${gb.genreId}&bookId=${book.bookId}'"
									class="btn btn-danger btn-sm">Delete</button></td>
						</tr>
					</loop:forEach>
				</table>

			</div>
		</div>

		<div style="width: 100%; float: left; margin-top: 40px;">
			<div class="form-group" align="center"
				style="width: 50%; display: inline-block; float: left;">
				<label for="sel1">Select new Publisher from list Below: </label> <select
					class="form-group" id="sel1" name="publisherId" style="width: 80%">
					<loop:forEach var="p" items="${publishers}">
					<loop:if test="${book.publisher.publisherId != p.publisherId}">
								<option value="${p.publisherId}">${p.publisherName} </option>
							</loop:if>
						<loop:if test="${book.publisher.publisherId == p.publisherId}">
							<option value="${p.publisherId}" selected>${p.publisherName} </option>
							
						</loop:if>
					</loop:forEach>
				</select>
			</div>

			<div align="center"
				style="width: 50%; display: inline-block; float: left;">
				<table class="table table-striped">
					<tr>
						<th>Current Publisher for the Book</th>
						<th>Delete</th>
					</tr>
					<loop:if test="${not empty book.publisher}">
						<tr>
							<td>${book.publisher.publisherName}</td>
							<td><button type="button"
									onclick="javascript:location.href='deletebookPublisher?bookId=${book.bookId}'"
									class="btn btn-danger btn-sm">Delete</button></td>
						</tr>
					</loop:if>
				</table>
			</div>
		</div>
		<div style="text-align: center;">
			<button type="submit" class="btn btn-primary btn-md">Update
				Author</button>
			<br />
			<br />
		</div>

		<br />

	</form:form>
</div>
