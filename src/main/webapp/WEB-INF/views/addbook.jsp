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
	<form method="post" action="addBook">
		${statusMessage} <br />Enter Book Title: <input type="text"
			name="bookName"><br />
		<br />
		<br /> <br />
		<%-- Div class for select tag --%>
		<div class="form-group" align="center"
			style="width: 30%; float: left; padding-right: 30px;">
			<label for="sel1">Select Publisher from list Below: </label>
			<form:select class="form-group" id="sel1" path="publishers">
				<loop:forEach items="${publishers}" var="pub">
					<form:option value="${pub.publisherId}">${pub.publisherName} </form:option>
				</loop:forEach>
			</form:select>
		</div>

		<div class="form-group" align="center"
			style="width: 35%; float: left; padding-right: 30px;">
			<label for="sel1">Select Authors from list Below: </label>
			<form:select class="form-group" path="authors" multiple="multiple"
				size="10">
				<loop:forEach items="${authors}" var="a">
					<form:option value="${a.authorId}"> ${a.authorName} </form:option>
				</loop:forEach>
			</form:select>
		</div>


		<div class="form-group" align="center"
			style="width: 35%; float: left; padding-right: 10px;">
			<label for="sel1">Select Genre from list Below: </label>
			<form:select class="form-group" path="genres" multiple="multiple"
				size="10">
				<loop:forEach items="${genres}" var="g">
					<form:option value="${g.genreId}"> ${g.genreName} </form:option>
				</loop:forEach>
			</form:select>
		</div>
		<div style="text-align: center">
			<button type="submit" class="btn btn-primary btn-md">Save
				Book</button>
		</div>
	</form>
</div>