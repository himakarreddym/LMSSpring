<%@ taglib prefix="loop" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container" style="text-align: center; margin-top: 45px;">
	<h3>Add New Book</h3>
	<form:form method="post" action="addBook" modelAttribute="Book">
		${statusMessage} <br />Enter Book Title: <form:input type="text"
			path="title" /><br />
		<br />
		<br /> <br />
		<%-- Div class for select tag --%>
		<div class="form-group" align="center"
			style="width: 30%; float: left; padding-right: 30px;">
			<label for="sel1">Select Publisher from list Below: </label>
			<select class="form-group" id="sel1" name="publisherId">
				<loop:forEach items="${publishers}" var="pub">
					<option value="${pub.publisherId}">${pub.publisherName} </option>
				</loop:forEach>
			</select>
		</div>

		<div class="form-group" align="center"
			style="width: 35%; float: left; padding-right: 30px;">
			<label for="sel1">Select Authors from list Below: </label>
			<select class="form-group" name="authorIds" multiple="multiple"
				size="10">
				<loop:forEach items="${authors}" var="a">
					<option value="${a.authorId}"> ${a.authorName} </option>
				</loop:forEach>
			</select>
		</div>


		<div class="form-group" align="center"
			style="width: 35%; float: left; padding-right: 10px;">
			<label for="sel1">Select Genre from list Below: </label>
			<select class="form-group" name="genreIds" multiple="multiple"
				size="10">
				<loop:forEach items="${genres}" var="g">
					<option value="${g.genreId}"> ${g.genreName} </option>
				</loop:forEach>
			</select>
		</div>
		<div style="text-align: center">
			<button type="submit" class="btn btn-primary btn-md">Save
				Book</button>
		</div>
	</form:form>
</div>