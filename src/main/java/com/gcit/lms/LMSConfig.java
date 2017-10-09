package com.gcit.lms;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

@Configuration
public class LMSConfig {
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library?useSSL=false";
	private String user = "root";
	private String password = "12345";
		
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(password);
		return ds;
	}
	
	@Bean
	public JdbcTemplate template() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	@Bean
	public BookLoansDAO bldao(){
		return new BookLoansDAO();
	}
	@Bean
	public BookCopiesDAO bcdao(){
		return new BookCopiesDAO();
	}
	@Bean
	public BorrowerDAO brdao(){
		return new BorrowerDAO();
	}
	@Bean
	public GenreDAO gdao(){
		return new GenreDAO();
	}
	@Bean
	public LibraryBranchDAO lbdao(){
		return new LibraryBranchDAO();
	}
	@Bean
	public PublisherDAO pdao(){
		return new PublisherDAO();
	}
	
	//Platform transaction manager to manage the connections like Commit and roll back, DB open and close will
	//handle by spring and rest will take care by it.
	@Bean
	public PlatformTransactionManager txManager(){
		return new DataSourceTransactionManager(dataSource());
	}
	//Creating the new instance of Admin service also against singleton and it will not be recognized by the Spring
	@Bean
	public AdminService adminService(){
		return new AdminService();
	}
	@Bean
	public LibrarianService libService(){
		return new LibrarianService();
	}
	@Bean
	public BorrowerService borService(){
		return new BorrowerService();
	}

}
