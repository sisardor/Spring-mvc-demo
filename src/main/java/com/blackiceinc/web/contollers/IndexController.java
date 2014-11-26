package com.blackiceinc.web.contollers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
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
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

@Controller
public class IndexController {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@ModelAttribute("myRequestObject")
	public void addStuffToRequestScope(HttpServletRequest request,HttpServletResponse response, Authentication authentication) {
		System.out.println("================= Inside of addStuffToRequestScope =================");
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
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/reg")
	@ResponseBody
	public String reg() {

		System.out.println("\n\nClearing Pooled Datasource");
        Set set = C3P0Registry.getPooledDataSources();
		System.out.println("Pooled datasource size: " + set.size());
		if ( set.size() >  0 ) {
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				
				PooledDataSource object = (PooledDataSource) iterator.next();
				System.out.println("Closing datasource: " + object.getDataSourceName());
				C3P0Registry.markClosed(object);
			}
		}

		return "reg";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, 
			HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
		    /* The user is logged in :) */
			redirectStrategy.sendRedirect(request, response, "/");
		    return null;
		}
		
		ModelAndView model = new ModelAndView();
		
		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
}
