package com.gcit.lms;

import java.sql.SQLException;
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

import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class PublisherController {
	
	@Autowired
	AdminService adminService;
	
	private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@RequestMapping(value = "/publisher", method = RequestMethod.GET)
	public String gottoPublisher(Locale locale, Model model) {
		return "publisher";
	}
	
	@RequestMapping(value = "/addpublisher", method = RequestMethod.GET)
		 public ModelAndView showForm(Model model) {
		        return new ModelAndView("addpublisher", "Publisher", new Publisher());
		    }
	
	
	@RequestMapping(value = "/addPublisher1", method = RequestMethod.POST)
	public String addpublisher(@ModelAttribute("Publisher")Publisher publisher,BindingResult result, ModelMap model) throws SQLException {
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
        }
		adminService.savePublisher(publisher);
		return "redirect:/viewpublishers";
	}
	 
	@RequestMapping(value = "/viewpublishers", method = RequestMethod.GET)
	public String gottoviewpublishers(Locale locale, Model model,@RequestParam(value ="pageNo",required=false) Integer pageNo) {
		Integer totalCount =-1;
		if(pageNo == null) {
			pageNo=1;
		}
			try {
				totalCount = adminService.getPublishersCount();
				int numOfPages = 0;
				if (totalCount % 4 > 0) {
					numOfPages = totalCount / 4 + 1;
				} else {
					numOfPages = totalCount / 4;
				}
				model.addAttribute("publishers", adminService.readPublisher(null, pageNo));
				model.addAttribute("totalCount", totalCount);
				model.addAttribute("numOfPages", numOfPages);
				model.addAttribute("pageNo",pageNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return "viewpublishers";
	}
	
	@RequestMapping(value = "/editpublisher", method = RequestMethod.GET)
	 public ModelAndView editpublisher(Model model,@RequestParam(value ="publisherId",required=false) Integer publisherId) {
		Publisher publisher = new Publisher();
		try {
			publisher= adminService.readPublisherByPK(publisherId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        return new ModelAndView("editpublisher", "Publisher", publisher);
	    }


	@RequestMapping(value = "/editpublisher1", method = RequestMethod.POST)
	public String editPublisher1(@ModelAttribute("Publisher")Publisher publisher,BindingResult result, ModelMap model) throws SQLException {
		
		if (result.hasErrors()) {
			logger.info("error",result.getFieldError());
	   }
		adminService.savePublisher(publisher);
		return "redirect:/viewpublishers";
	}
	@RequestMapping(value = "/deletePublisher", method = RequestMethod.GET)
	 public String deleteBorrower(Model model,@RequestParam(value ="publisherId",required=false) Integer publisherId) {
		Publisher publisher = new Publisher();
		try {
			publisher.setPublisherId(publisherId);
			adminService.deletePublisher(publisher);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/viewpublishers";
	    }
}
