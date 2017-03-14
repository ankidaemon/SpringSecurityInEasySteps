package com.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.dao.UserDao;
import com.demo.to.User;

@Controller
@Scope("session")
public class HomeController {

	@Autowired
	UserDao ud;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView visitHome() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}

	@RequestMapping(value = "/signupRequest", method = RequestMethod.GET)
	public String registration(Model model) {
        model.addAttribute("userTo", new User());
        return "signup";
    }
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView singUp(@ModelAttribute("userTo") User user,RedirectAttributes attr) {
		ModelAndView mav = new ModelAndView();
		String s = ud.save(user);
		if (s.equalsIgnoreCase("Successful")) {
			mav.addObject("user", user);
			mav.setViewName("login");
			return mav;
		} else {
			attr.addFlashAttribute("user", user);
			attr.addFlashAttribute("error", s);
			mav.setViewName("signup");
			return mav;
		}
	}
}