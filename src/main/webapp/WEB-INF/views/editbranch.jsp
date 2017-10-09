<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.Publisher"%>
<%@include file="boot.htm"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div>
			<div class="container">
				<form:form method="post" action="editbranch1"
					modelAttribute="LibraryBranch">
		${statusMessage}
		<br />Enter LibraryBranch Name to Edit: 
		<form:input type="text" path="branchName"
						value="${LibraryBranch.branchName}" />
					<br />
					<br />Enter LibraryBranch Address to Edit: 
					<form:input type="text" path="branchAddress"
						value="${LibraryBranch.branchAddress}" />
					<br />
					<form:input type="hidden" path="branchId"
						value="${LibraryBranch.branchId}" />
					<br />
					<button type="submit" class="btn btn-primary btn-sm">Update
						LibraryBranch</button>
					<br />
					<br />


				</form:form>
			</div>
		</div>
	</div>
</div>
