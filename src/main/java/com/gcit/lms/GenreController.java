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
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class GenreController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(GenreController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/genre", method = RequestMethod.GET)
	public String gottoAdmin(Locale locale, Model model) {
		return "genre";
	}
	@RequestMapping(value = "/addgenre", method = RequestMethod.GET)
		 public ModelAndView showForm(Model model) {
			 try {
				model.addAttribute("books",adminService.readBooks());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        return new ModelAndView("addgenre", "Genre", new Genre());
		    }
	@RequestMapping(value = "/addgenre", method = RequestMethod.POST)
	public String addGenre(@ModelAttribute("Genre")Genre genre,
			@RequestParam(value ="bookIds",required=false) String[] bookIds,BindingResult result, ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
        }
		if(bookIds !=null) {
			genre.setBooks(adminService.getBooks(bookIds));
			}
	adminService.saveGenre(genre);
		model.clear();
		return "redirect:/viewgenres";
	}
	 
	@RequestMapping(value = "/viewgenres", method = RequestMethod.GET)
	public String gottoviewgenres(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getGenresCount();
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("genres", adminService.readGenre(null, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewgenres";
	}
	
	@RequestMapping(value = "/editgenre", method = RequestMethod.GET)
	 public ModelAndView GoToeditGenre(Model model,@RequestParam(value ="genreId",required=false) Integer genreId) {
	Genre genre = new Genre();
	try {
		genre = adminService.readGenreByPK(genreId);
		List<Book> availablebooks = new ArrayList<Book>();
		List<Book> books=adminService.readBooks();
		List<Book> genrebooks = genre.getBooks();
		for (Book book : books) {
			if(!genrebooks.contains(book)) {
				availablebooks.add(book);
			}
		}
		model.addAttribute("books",availablebooks);
		
		model.addAttribute("genreBooks",genre.getBooks());
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("editgenre", "Genre", genre);
	    }
	@RequestMapping(value = "/editgenre", method = RequestMethod.POST)
	 public String GoToGenrePost(@ModelAttribute("Genre")Genre genre,
				@RequestParam(value ="bookIds",required=false) String[] bookIds,BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
       }
		if(bookIds != null) {
			try {
				genre.setBooks(adminService.getBooks(bookIds));
				adminService.saveGenre(genre);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.clear();
		return "redirect:/viewgenres";
	}
	@RequestMapping(value = "/deleteGenre", method = RequestMethod.GET)
	 public String deleteGenre(Model model,@RequestParam(value ="genreId",required=false) Integer genreId ) {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		try {
			adminService.deleteGenre(genre);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:viewgenres";
	    }
	@RequestMapping(value = "/deleteGenreBook", method = RequestMethod.GET)
	 public String deleteBorrower(Model model,@RequestParam(value ="genreId",required=false) Integer genreId,
			 @RequestParam(value ="bookId",required=false) Integer bookId ) {
		Genre genre = new Genre();
		genre.setGenreId(genreId);
		try {
			adminService.deleteBookGenre(genre,bookId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("genreId",genreId);
		return "redirect:editgenre";
	    }
	
	
}
