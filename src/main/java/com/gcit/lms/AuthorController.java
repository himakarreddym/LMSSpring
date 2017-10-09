package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AuthorController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to home method", locale);
		return "welcome";
	}
	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public String gottoAdmin(Locale locale, Model model) {
		return "author";
	}
	@RequestMapping(value = "/addauthor", method = RequestMethod.GET)
		 public ModelAndView showForm(Model model) {
		Author author = new Author();
		try {
			List<Book>books = adminService.readBooks();
				model.addAttribute("books",books);
				author.setBooks(books);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        return new ModelAndView("addauthor", "Author", author);
		    }
	@RequestMapping(value = "/addauthor1", method = RequestMethod.POST)
	public String addAuthor(@ModelAttribute("Author")Author author,
			@RequestParam(value ="bookIds",required=false) String[] bookIds,BindingResult result, ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
        }
		if(bookIds != null) {
			author.setBooks(adminService.getBooks(bookIds));
		}
		adminService.saveAuthor(author);
		model.clear();
		return "redirect:/viewauthors";
	}
	 
	@RequestMapping(value = "/viewauthors", method = RequestMethod.GET)
	public String gottoviewauthors(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getAuthorsCount(null);
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("authors", adminService.readAuthors(null, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewauthors";
	}
	@RequestMapping(value = "/searchAuthor", method = RequestMethod.GET)
	public String gottosearchAuthors(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo,
			@RequestParam(value ="searchString",required=false) String searchString) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getAuthorsCount(searchString);
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("authors", adminService.readAuthors(searchString, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
				model.addAttribute("searchString",searchString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewauthors";
	}
	
	
	
	@RequestMapping(value = "/editauthor", method = RequestMethod.GET)
	 public ModelAndView GoToeditAuthor(Model model,@RequestParam(value ="authorId",required=false) Integer authorId) {
	Author author = new Author();
	try {
		author = adminService.readAuthorByPK(authorId);
		List<Book> availablebooks = new ArrayList<Book>();
		List<Book> books=adminService.readBooks();
		List<Book> authorbooks = author.getBooks();
		for (Book book : books) {
			if(!authorbooks.contains(book)) {
				availablebooks.add(book);
			}
		}
		model.addAttribute("Author",author);
		model.addAttribute("books",availablebooks);
		
		model.addAttribute("authorBooks",author.getBooks());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("editauthor", "Author", author);
	    }
	
}
