package com.blackiceinc.web.contollers;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackiceinc.account.dao.AccountDao;
import com.blackiceinc.account.model.Account;

@Controller
public class IndexController {

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
	

	@RequestMapping(value = "/sardor")
	@ResponseBody
	public String sardor() {
		List<Account> list = serviceDao.findAll();

		for(Account acc : list) {
			System.out.println(acc);
			
		}
		

		return "home";
	}

	@RequestMapping(value = "/login")
	public String login() {

		return "login";
	}
}
