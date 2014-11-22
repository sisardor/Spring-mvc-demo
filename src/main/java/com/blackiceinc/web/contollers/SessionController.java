package com.blackiceinc.web.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blackiceinc.beans.Cart;

@Controller
@Scope("request")
public class SessionController {
	@Autowired
	private Cart cart;

	@RequestMapping("/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam("id") int id) {
		
		
		return "returning cart";
	}
}