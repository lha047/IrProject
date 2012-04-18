package uib.info323.twitterAWSM;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uib.info323.twitterAWSM.model.search.ISearch;

/**
 * 
 * 
 * @author
 *
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	
	@Autowired
	ISearch search;
	
	@RequestMapping(value = "/tag")
	public ModelAndView search(@RequestParam String q) {
		
		ModelAndView mav = new ModelAndView("tagSearchResults");
		mav.addObject("query", q);
		
		return mav;
		
	}
	
}
