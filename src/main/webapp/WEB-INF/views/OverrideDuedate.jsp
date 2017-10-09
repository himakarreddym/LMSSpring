<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.gcit.lms.entity.BookLoans"%>
<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.entity.BookCopies"%>
<%@include file="boot.htm" %>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%AdminService adminServive = new AdminService();
List<BookLoans> bookLoans = adminServive.readBookLoans1();
Integer totalCount = adminServive.getbookLoansCount();
int numOfPages = 0;
if(totalCount%10 > 0){
	numOfPages = totalCount/10 +1;
}else{
	numOfPages = totalCount/10;
}
List<BookLoans> bookloans = new ArrayList<>();
if(request.getAttribute("bookloans")!=null){
	bookloans = (List<BookLoans>)request.getAttribute("bookloans");
}else{
	bookloans = adminServive.readBookLoansPage(1);
}
SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYY, HH:mm");

%>
<div class="container" style="text-align: center;margin-top:45px;">
		${statusMessage}
		<div>
		<label for="sel1">Select Book to Override the due date from list Below: </label>
		<nav aria-label="Page navigation example">
		<ul class="pagination">
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Previous"> <span aria-hidden="true">&laquo;</span> <span
					class="sr-only">Previous</span>
			</a></li>
			<%for(int i=1; i<=numOfPages; i++){ %>
			<li class="page-item"><a class="page-link" href="pageBookLoans?pageNo=<%=i%>"><%=i%></a></li>
			<%} %>
			<li class="page-item"><a class="page-link" href="#"
				aria-label="Next"> <span aria-hidden="true">&raquo;</span> <span
					class="sr-only">Next</span>
			</a></li>
		</ul>
	</nav>
        <table class="table table-striped">
    		<tr>
    			<th> Book Title </th>
    			<th> Branch name </th>
    			<th> Borrower name </th>
    			<th> Date Out</th>
    			<th> Due Date </th>
    			<th> Override </th>
    		</tr>
    		<tr>	
    		<% for (BookLoans bl : bookloans) { %>
    			<form  method="post" action="override">
    		<input type="hidden" name="bookId" value="<%=bl.getBookId()%>">
    		<input type="hidden" name=branchId value="<%=bl.getBranchId()%>">
    		<input type="hidden" name="CardNo" value="<%=bl.getCardNo()%>">
    		<input type="hidden" name="dateOut" value="<%=bl.getDateOut()%>">
    		<input type="hidden" name="dueDate" value="<%=bl.getDueDate()%>">
    		<tr>
    			<td>
    			<% 	out.println(bl.getBook().getTitle()); %>	 <br />
    			 </td>
    			 <td>
    			<% 	out.println(bl.getBranch().getBranchName()); %>	 <br />
    			 </td>
    			 <td>
    			<% 	out.println(bl.getBorrower().getName()); %>	 <br />
    			 </td>
    			 <td>
    			<% 	out.println(dateFormat.format(bl.getDateOut())); %>	 <br />
    			 </td>
    			 <td>
    			<% 	out.println(dateFormat.format(bl.getDueDate())); %>	 <br />
    			 </td>
    			 <td><button type="submit"
					class="btn btn-danger btn-sm">Override</button>
					</td>
    		</tr>
			</form>	
		<%	}%>
		</tr>
         </table> 
    </div>
</div>