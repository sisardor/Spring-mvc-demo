package com.blackiceinc.web.contollers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.blackiceinc.account.dao.AccountDao;
import com.blackiceinc.account.model.Account;
import com.blackiceinc.beans.UserInfoBean;
import com.blackiceinc.utils.SpringUtils;

@Controller
public class IndexController {
	
	@ModelAttribute("myRequestObject")
	public void addStuffToRequestScope() {
		System.out.println("================= Inside of addStuffToRequestScope =================");
		//UserInfoBean  user = SpringUtils.getBean("userInfoBean");
		//return bean;
	}



	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private AccountDao serviceDao;

	@RequestMapping(value = "/")
	public String index() {

		return "home";
	}

	@RequestMapping(value = "/admin")
	public String admin() {

		return "home";
	}
	

	@RequestMapping(value = "/main")
	@ResponseBody
	public String sardor(Model model, HttpServletRequest request, HttpSession session) {
		List<Account> list = serviceDao.findAll();

		for(Account acc : list) {
			System.out.println(acc);
			
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication.getDetails());
		System.out.println("session id " + session.getId());
		return "home " + authentication.getName() + " " + authentication.isAuthenticated();
	}
	@RequestMapping(value = "/guest")
	@ResponseBody
	public String guest() {
		List<Account> list = serviceDao.findAll();

		for(Account acc : list) {
			System.out.println(acc);
			
		}
		

		return "guest";
	}
	
	@RequestMapping(value = "/reg")
	@ResponseBody
	public String reg() {
		List<Account> list = serviceDao.findAll();

		for(Account acc : list) {
			System.out.println(acc);
			
		}
		

		return "reg";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
}
