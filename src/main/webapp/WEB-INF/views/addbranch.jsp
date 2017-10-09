<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="jumbotron jumbotron-billboard">
	<div class="img"></div>
	<div class="container">
		<div>

			<div class="container" style="text-align: center">
				<br>
				<br>
				<h2>Add New Library Branch</h2>
				<br>
				<br>
				<form:form method="post" action="addbranch1" modelAttribute="LibraryBranch">
		${statusMessage}
		<div style="text-align: center">
						<table class="table table-striped" style="text-align: center">
							<tr>
								<td><label> Enter Library branch Name: </label></td>
								<td><form:input type="text" path="branchName"/></td>
							</tr>
							<tr>
								<td><label> Enter Library branch Address: </label></td>
								<td><form:input type="text" path="branchAddress"/></td>
							</tr>

						</table>
						<button type="submit" class="btn btn-primary btn-md">Save
							Branch</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>