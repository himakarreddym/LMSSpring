<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
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
			<div class="container" style="text-align: center">
				<form:form method="post" action="editBranchInf" modelAttribute="LibraryBranch">
					${statusMessage}
					 <br/>Enter new Branch Information below : <br /> <br /> 
					<label>Branch name :   </label> 
					<form:input type="text" path="branchName"
						value="${LibraryBranch.branchName}" /><br /> <br /> 
					<label> Branch Address : </label>
					<form:input type="text" path="branchAddress"
						value="${LibraryBranch.branchAddress}" /><br />

					<!-- Branch name and branch id is coming but branch address is null -->

					<form:input type="hidden" path="branchId"
						value="${LibraryBranch.branchId}" /><br />

					<button type="submit" class="btn btn-primary btn-md">Update
						branch </button>
					<br />
					<br />


				</form:form>
			</div>
		</div>
	</div>
</div>
