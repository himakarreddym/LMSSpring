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
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.LibraryBranch;
import com.mysql.jdbc.Statement;

@SuppressWarnings({ "rawtypes" })
public class LibraryBranchDAO extends BaseDAO implements ResultSetExtractor<List<LibraryBranch>> {


	public void saveLibraryBranches(LibraryBranch braches) throws SQLException {
		template.update("INSERT INTO tbl_library_branch (branchName,branchAddress) VALUES (?,?)", new Object[] { braches.getBranchName(),braches.getBranchAddress() });
	}
	
//	public void saveBookLibraryBranches(LibraryBranches braches) throws SQLException {
//		for(Book b: braches.getBooks()){
//			save("INSERT INTO tbl_book VALUES (?, ?,?)", new Object[] { b.getBookId(), b.getTitle(),braches.getBranchId()});
//		}
//	}
	
	public Integer saveLibraryBranchesWithID(LibraryBranch braches) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_library_branch (branchName,branchAddress) VALUES (?,?)";
		template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps =   connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, braches.getBranchName());
                ps.setString(2, braches.getBranchAddress());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
    

	public void updateLibraryBranches(LibraryBranch braches) throws SQLException {
		template.update("UPDATE tbl_library_branch SET branchName = ?,branchAddress=? WHERE branchId = ?",
				new Object[] { braches.getBranchName(),braches.getBranchAddress() ,braches.getBranchId() });
	}

	public void deleteLibraryBranches(LibraryBranch braches) throws SQLException {
		template.update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { braches.getBranchId() });
	}
	
	

	public List<LibraryBranch> readBranchs(String branchName) throws SQLException {
		String sql = null;
		if(branchName !=null && !branchName.isEmpty()){
			branchName = "%"+branchName+"%";
			sql="SELECT * FROM tbl_library_branch WHERE branchName like ?";
			return template.query(sql, new Object[]{branchName},this);
		}else{
			sql="SELECT * FROM tbl_library_branch";
			return template.query(sql, this);
		}
		
	}
	public List<LibraryBranch> readBranch(String branchName,Integer pageNo) throws SQLException {
		setPageNo(pageNo);
		String sql = null;
		if(branchName  !=null && !branchName.isEmpty()){
			branchName  = "%"+branchName+"%";
			sql="SELECT * FROM tbl_library_branch WHERE branchName like ?";
			sql+=pagenation();
			return template.query(sql, new Object[]{branchName},this);

		}else{
			sql="SELECT * FROM tbl_library_branch";
			sql+=pagenation();
			return template.query(sql, this);
		}
		
	}
	
	public LibraryBranch readbranchByPK(Integer branchId) throws SQLException {
		List<LibraryBranch> branches = template.query("SELECT * FROM tbl_library_branch WHERE branchId = ?", new Object[]{branchId},this);
		if(branches!=null){
			return branches.get(0);
		}
		return null;
	}
	
	public Integer getbarnchesCount() throws SQLException {
		return template.queryForObject("SELECT count(*) as COUNT FROM tbl_library_branch", null,Integer.class);
	}
	
	public LibraryBranch readBranchByCard(BookLoans bl) throws SQLException {
		List<LibraryBranch> branches= template.query("SELECT * FROM tbl_library_branch where branchId = ?", new Object[]{bl.getBranchId()}, this);
		if(branches!=null){
			return branches.get(0);
		}
		return null;
	}
	public LibraryBranch readBranchBycopies(BookCopies bc) throws SQLException {
		List<LibraryBranch> branches= template.query("SELECT * FROM tbl_library_branch where branchId = ?", new Object[]{bc.getBranchId()}, this);
		if(branches!=null){
			return branches.get(0);
		}
		return null;
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> brachess = new ArrayList<>();
		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			brachess.add(a);
		}
		
		return brachess;
	}
	public String pagenation() {
		if(getPageNo()!=null){
			Integer index = (getPageNo() -1) * getPageSize();
			return " LIMIT "+index+","+getPageSize();
		}
		return null;
		}

}
