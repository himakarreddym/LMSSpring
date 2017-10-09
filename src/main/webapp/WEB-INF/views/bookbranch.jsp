<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@include file="boot.htm" %>
<%@page import="java.util.ArrayList"%>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%AdminService service = new AdminService();
LibrarianService libService = new LibrarianService();
List<Book> books = service.readBooks();

 int branchId = Integer.parseInt(request.getAttribute("branchId").toString());
LibraryBranch branch = libService.readbranchbyPK(branchId);
List<Integer> bookIds = new ArrayList<Integer>();
	for(BookCopies bc : branch.getBookcopies()){
		bookIds.add(bc.getBookId());
	} 
%>
<div class="container" style="text-align: center;margin-top:45px;">
	${statusMessage} <br>	<br>
		 <%-- Div class for select tag --%> 
		<button type="button"
					onclick="javascript:location.href='viewbranches.jsp'"
					class="btn btn-danger btn-md">View Branches </button> <br> <br><br>
		<label for="sel1">Enter number of copies for the books from list Below: </label>
		<div class="form-group" style="padding-top: 20px;padding-left: 10%;">
		 <table class="table table-striped" style="width:90%;">
		<tr>
			<th>Book Title</th>
			<th>Number of copies</th>
			<th>ADD</th>
		</tr>
		<%
			for (Book book : books) {
				 if(! bookIds.contains(book.getBookId()))  
				{
		%>
		<form method="post" action="addbranchBook">
		<tr>
			<td><%=book.getTitle()%></td>
			<td>
			<input type="number" name="noofCopies" id="copies"><br /><br />
			<input type="hidden" name="bookId" value="<%=book.getBookId()%>"><br/>
			<input type="hidden" name="branchId" value="<%=branchId%>"><br/>
			</td>
			<td><button type="submit"
					
					class="btn btn-danger btn-sm">Add </button></td>
		</tr> 
		</form>
		<%
				}	} 
		%>
	</table>
			
		 </div>
		
		
</div>