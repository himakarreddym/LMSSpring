package com.gcit.lms.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;


@SuppressWarnings({ "rawtypes" })
public class BookLoansDAO extends BaseDAO implements ResultSetExtractor<List<BookLoans>>{


	public void saveBookLoans(BookLoans bookloans) throws SQLException {
		template.update("INSERT INTO tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate,dateIn) VALUES (?,?,?,?,?,?)", 
			new Object[] { bookloans.getBookId(),bookloans.getBranchId(),bookloans.getCardNo(),bookloans.getDateOut(),bookloans.getDueDate(),bookloans.getDateIn() });
	}
	public List<BookLoans> readAllbookLoans() throws SQLException {
		return template.query("SELECT * FROM tbl_book_loans",this);
	}
//	public void saveBookBookLoans(BookLoans bookloans) throws SQLException {
//		for(Book b: bookloans.getBooks()){
//			save("INSERT INTO tbl_book VALUES (?, ?,?)", new Object[] { b.getBookId(), b.getTitle(),bookloans.getCardNo()});
//		}
//	}
	
//	public Integer saveBookLoansWithID(BookLoans bookloans) throws SQLException {
//		return saveWithID("INSERT INTO tbl_book_loans (name,address,phone) VALUES (?,?,?)", new Object[] { bookloans.getName(),bookloans.getAddress(),bookloans.getPhone() });
//	}

	public void updateBookLoans(BookLoans bookloans) throws SQLException {
		template.update("UPDATE tbl_book_loans SET dueDate=?,dateIn = ? WHERE bookId = ? AND branchId = ? AND cardNo= ? And dateOut =? ",
			new Object[] { bookloans.getDueDate(),bookloans.getDateIn() ,bookloans.getBookId(),bookloans.getBranchId(),bookloans.getCardNo(),bookloans.getDateOut() });
	}
	public void updatedueDate(BookLoans bookloans) throws SQLException {
		template.update("UPDATE tbl_book_loans SET dueDate=? WHERE bookId = ? AND branchId = ? AND cardNo= ? And dateOut =? ",
			new Object[] { bookloans.getDueDate(),bookloans.getBookId(),bookloans.getBranchId(),bookloans.getCardNo(),bookloans.getDateOut() });
	}

	public void deleteBookLoans(BookLoans bookloans) throws SQLException {
		template.update("DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo= ? And dateOut =?", 
				new Object[] { bookloans.getBookId(),bookloans.getBranchId(),bookloans.getCardNo(),bookloans.getDateOut() });
	}
	
	public List<BookLoans> readBookLoansPage(Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		String sql = null;
		sql="SELECT * FROM tbl_book_loans WHERE dateIn is null";
		sql+=pagenation();
			return template.query(sql,this);
		
	}
	

	public List<BookLoans> readBookLoans(BookLoans bookloans) throws SQLException {
		if(bookloans.getBookId() != 0 && bookloans.getBranchId() != 0 && bookloans.getCardNo() != 0){
			return template.query("SELECT * FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo= ?", 
					new Object[]{bookloans.getBookId(),bookloans.getBranchId(),bookloans.getCardNo()},this);
		}else{
			return template.query("SELECT * FROM tbl_book_loans",this);
		}
		
	}

	public List<BookLoans> readBookLoans1() throws SQLException {
		return template.query("SELECT * FROM tbl_book_loans WHERE dateIn is null",this);
	
}
	public Integer getbookLoansCount() throws SQLException {
		return template.queryForObject("SELECT count(*) as COUNT FROM tbl_book_loans WHERE dateIn is null",Integer.class);
	}
	public List<BookLoans> readBookLoansBycard (BookLoans bookloans) throws SQLException {
			return template.query("SELECT * FROM tbl_book_loans WHERE branchId = ? AND cardNo= ? AND dateIn is null", 
					new Object[]{bookloans.getBranchId(),bookloans.getCardNo()},this);
	}
	public List<BookLoans> readAllBooksLoansBycard(Borrower bor) throws SQLException {
		return template.query("SELECT * FROM tbl_book_loans where cardNo IN (select cardNo from tbl_borrower where cardNo = ?)", new Object[]{bor.getCardNo()}, this);
	}
	
	
	
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookloanss = new ArrayList<>();
		while(rs.next()){
			BookLoans a = new BookLoans();
			a.setBookId(rs.getInt("bookId"));
			a.setBranchId(rs.getInt("branchId"));
			a.setCardNo(rs.getInt("cardNo"));
			a.setDateOut(rs.getTimestamp("dateOut"));
			a.setDueDate(rs.getTimestamp("dueDate"));
			a.setDateIn(rs.getTimestamp("dateIn"));
			bookloanss.add(a);
		}
		
		return bookloanss;
	}
	public String pagenation() {
	if(getPageNo()!=null){
		Integer index = (getPageNo() -1) * getPageSize();
		return " LIMIT "+index+","+getPageSize();
	}
	return null;
	}

}
