package com.demo.web;

import org.slf4j.Logger;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.dao.UserDao;
import com.demo.daoImpl.UserDaoImpl;
import com.demo.to.User;

@Controller
@RequestMapping(value = "/Register")
public class HomeController {
	@Autowired
	User user;
	
	@Autowired
	UserDao ud;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForm(Model model) {
		ModelAndView mav = new ModelAndView("register");
		logger.info("Registration");
		if (!model.containsAttribute("user")) {
			mav.addObject("user", user);
		} else
			mav.addObject(model);
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String registerCab(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			return "redirect:/Register";
		}	
		String s=ud.save(user);
		if(s.equalsIgnoreCase("Successful")){
		attr.addFlashAttribute("user", user);
		return "redirect:Success";}
		else
		{
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			attr.addFlashAttribute("error", s);
			return "redirect:/Register";
		}
	}
}
