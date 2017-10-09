package com.gcit.lms.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;

@SuppressWarnings({"rawtypes" })
public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>>{

	

	public void saveBorrower(Borrower borrower) throws SQLException {
		template.update("INSERT INTO tbl_borrower (name,address,phone) VALUES (?,?,?)", new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone() });
	}
	
//	public void saveBookBorrower(Borrower borrower) throws SQLException {
//		for(Book b: borrower.getBooks()){
//			save("INSERT INTO tbl_book VALUES (?, ?,?)", new Object[] { b.getBookId(), b.getTitle(),borrower.getCardNo()});
//		}
//	}
	
	public Integer saveBorrowerWithID(Borrower borrower) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_borrower (name,address,phone) VALUES (?,?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps =  connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, borrower.getName());
                ps.setString(2, borrower.getAddress());
                ps.setString(3, borrower.getPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBorrower(Borrower borrower) throws SQLException {
		template.update("UPDATE tbl_borrower SET name = ?,address=?,phone = ? WHERE cardNo = ?",
				new Object[] { borrower.getName(),borrower.getAddress(),borrower.getPhone() ,borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws SQLException {
		template.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public Integer getBorrowersCount() throws SQLException {
		return template.queryForObject ("SELECT count(*) as COUNT FROM tbl_borrower",Integer.class);
	}

	public List<Borrower> readBorrowers(String name) throws SQLException {
		String sql = null;
		if(name !=null && !name.isEmpty()){
			name = "%"+name+"%";
			sql="SELECT * FROM tbl_borrower WHERE name like ?";
			sql+=pagenation();
			return template.query(sql, new Object[]{name},this);
		}else{
			sql="SELECT * FROM tbl_borrower";
			sql+=pagenation();
			return template.query(sql,this);
		}
		
	}
	public List<Borrower> readBorrowers1(String name,int pageNo) throws SQLException {
		setPageNo(pageNo);
		String sql = null;
		if(name !=null && !name.isEmpty()){
			name = "%"+name+"%";
			sql="SELECT * FROM tbl_borrower WHERE name like ?";
			sql+=pagenation();
			return template.query(sql, new Object[]{name},this);
		}else{
			sql="SELECT * FROM tbl_borrower";
			sql+=pagenation();
			return template.query(sql,this);
		}
		
	}
	public Borrower readBorrowerByPK(Integer cardNo) throws SQLException {
		List<Borrower> borrowers = template.query("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[]{cardNo},this);
		if(borrowers.size() > 0){
			return borrowers.get(0);
		}
		return null;
	}
	public List<Borrower> readBorrowerBybranch(Integer branchId,Integer cardNo) throws SQLException {
			return (template.query("SELECT * FROM tbl_borrower WHERE cardNo = ? AND branchId = ?", new Object[]{cardNo,branchId},this));
	
	}
	
	public Borrower readAllBorrowersByCard(BookLoans bl) throws SQLException {
		List<Borrower> borrowers= template.query("SELECT * FROM tbl_borrower where cardNo = ?", new Object[]{bl.getCardNo()}, this);
		if(borrowers!=null){
			return borrowers.get(0);
		}
		return null;
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
			borrowers.add(a);
		}
		
		return borrowers;
	}
	public String pagenation() {
	if(getPageNo()!=null){
		Integer index = (getPageNo() -1) * getPageSize();
		return " LIMIT "+index+","+getPageSize();
	}
	return null;
	}

}
