package com.demo.web;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class LoginController {
	@Autowired
	User user;

	@Autowired
	UserDao ud;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public ModelAndView showLogin(Model model) {
		ModelAndView mav = new ModelAndView("login");
		logger.info("Login");
		if (!model.containsAttribute("user")) {
			mav.addObject("user", user);
		} else
			mav.addObject(model);
		return mav;
	}

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String loginPeople(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes attr,HttpServletRequest request) {
		if (result.hasErrors()) {
			if(result.getFieldErrors().toString().contains("userName") || result.getFieldErrors().toString().contains("password"))
			{attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			return "redirect:/Login";}
		}
		String s = ud.authenticateUser(user);
		if (s.equalsIgnoreCase("Successful")) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", user.getUserName());
			return "redirect:/Home";
		} else {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			attr.addFlashAttribute("user", user);
			attr.addFlashAttribute("error", s);
			return "redirect:/Login";
		}
	}

	@RequestMapping(value="/logout" , method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/";
	}

}
