package com.gcit.lms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BookController {

	@Autowired
	AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public String gottoAdmin(Locale locale, Model model) {
		return "book";
	}

	@RequestMapping(value = "/addbook", method = RequestMethod.GET)
	public ModelAndView showForm(Model model) {
		try {
			model.addAttribute("authors", adminService.readAuthors());
			model.addAttribute("publishers", adminService.readPublishers());
			model.addAttribute("genres", adminService.readGenres());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("addbook", "Book", new Book());
	}

	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String addBook(@ModelAttribute("Book") Book book,
			@RequestParam(value = "authorIds", required = false) String[] authorIds,
			@RequestParam(value = "genreIds", required = false) String[] genreIds,
			@RequestParam(value = "publisherId", required = false) Integer publisherId, BindingResult result,
			ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error", result.getFieldError());
		}
		Publisher pub = new Publisher();
		pub.setPublisherId(publisherId);
		book.setPublisher(pub);
		try {
			if (authorIds != null) {
				book.setAuthors(adminService.getAuthors(authorIds));
			}
			if (genreIds != null) {
				book.setGenres(adminService.getGenres(genreIds));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		adminService.saveBook(book);
		model.clear();
		return "redirect:/viewbooks";
	}

	@RequestMapping(value = "/viewbooks", method = RequestMethod.GET)
	public String gottoviewbooks(Locale locale, Model model,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) {
		Integer totalCount = -1;
		if (pageNo == null) {
			pageNo = 1;
		}
		try {
			totalCount = adminService.getBooksCount();
			int numOfPages = 0;
			if (totalCount % 4 > 0) {
				numOfPages = totalCount / 4 + 1;
			} else {
				numOfPages = totalCount / 4;
			}
			model.addAttribute("books", adminService.readBooks(null, pageNo));
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("numOfPages", numOfPages);
			model.addAttribute("pageNo", pageNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewbooks";
	}

	@RequestMapping(value = "/editbooks", method = RequestMethod.GET)
	public ModelAndView GoToeditBooks(Model model, @RequestParam(value = "bookId", required = false) Integer bookId) {
		Book book = new Book();
		try {
			book = adminService.readBookByPK(bookId);
			List<Author> authors = adminService.readAuthors();
			List<Author> availableAuthors = new ArrayList<Author>();
			List<Author> authorBooks = book.getAuthors();
			for (Author a : authors) {
				if (!authorBooks.contains(a)) {
					availableAuthors.add(a);
				}
			}
			List<Publisher> publishers = adminService.readPublishers();
			Publisher bookPublisher = book.getPublisher();
			List<Genre> genres = adminService.readGenres();
			List<Genre> bookGenres = book.getGenres();
			List<Genre> availableGenres = new ArrayList<Genre>();
			for (Genre g : genres) {
				if (!bookGenres.contains(g)) {
					availableGenres.add(g);
				}
			}
			logger.info("Publishers"+publishers.size());

			model.addAttribute("authorBooks", authorBooks);
			model.addAttribute("bookGenres", bookGenres);
			model.addAttribute("authors", availableAuthors);
			model.addAttribute("genres", availableGenres);
			model.addAttribute("book", book);
			model.addAttribute("publishers", publishers);
			model.addAttribute("bookPublisher", bookPublisher);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("editbooks", "Book", book);
	}

	@RequestMapping(value = "/editBook", method = RequestMethod.POST)
	public String GoToAuthorPost(@ModelAttribute("Book") Book book,
			@RequestParam(value = "authorIds", required = false) String[] authorIds,
			@RequestParam(value = "genreIds", required = false) String[] genreIds,
			@RequestParam(value = "publisherId", required = false) Integer publisherId, BindingResult result,
			ModelMap model) {
		if (result.hasErrors()) {
			logger.info("error", result.getFieldError());
		}
		Publisher pub = new Publisher();
		pub.setPublisherId(publisherId);
		book.setPublisher(pub);

		try {
			if (authorIds != null) {
				book.setAuthors(adminService.getAuthors(authorIds));
			}
			if (genreIds != null) {
				book.setGenres(adminService.getGenres(genreIds));
			}
			adminService.saveBook(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.clear();
		return "redirect:/viewbooks";
	}
	@RequestMapping(value = "/deleteBook", method = RequestMethod.GET)
	 public String deleteAuthor(Model model,@RequestParam(value ="bookId",required=false) Integer bookId  ) {
		Book book = new Book();
		book.setBookId(bookId);
		try {
			adminService.deleteBook(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:viewauthors";
	    }
	@RequestMapping(value = "/deleteBookAuthor", method = RequestMethod.GET)
	 public String deleteBookAuthor(Model model,@RequestParam(value ="authorId",required=false) Integer authorId,
			 @RequestParam(value ="bookId",required=false) Integer bookId ) {
		Author author = new Author();
		author.setAuthorId(authorId);
		try {
			adminService.deleteBookAuthor(author,bookId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("bookId",bookId);
		return "redirect:editbooks";
	    }
	@RequestMapping(value = "/deleteGenreBook1", method = RequestMethod.GET)
	 public String deleteBookGenre(Model model,@RequestParam(value ="genreId",required=false) Integer genreId,
			 @RequestParam(value ="bookId",required=false) Integer bookId ) {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		try {
			adminService.deleteBookGenre(genre,bookId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("bookId",bookId);
		return "redirect:editbooks";
	    }
	@RequestMapping(value = "/deletebookPublisher", method = RequestMethod.GET)
	 public String deleteBookPublisher(Model model,
			 @RequestParam(value ="bookId",required=false) Integer bookId ) {
		Book book = new Book();
		book.setBookId(bookId);
		Publisher pub = new Publisher();
		pub.setPublisherId(null);
		book.setPublisher(pub);
		try {
			adminService.upadatebookPublisher(book);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("bookId",bookId);
		return "redirect:editbooks";
	    }

}
