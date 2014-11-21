package com.blackiceinc.web.contollers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blackiceinc.account.dao.AccountDao;

@Controller
public class IndexController {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private AccountDao serviceDao;
	
	@RequestMapping(value="/")
	public String index() {

		return "index";
	}
	
	@RequestMapping(value="/admin")
	public String admin() {

		return "home";
	}
	
	@RequestMapping(value="/sardor")
	public String sardor() {

		return "home";
	}
}
