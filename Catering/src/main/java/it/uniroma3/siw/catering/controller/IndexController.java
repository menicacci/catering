package it.uniroma3.siw.catering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/administration")
	public String getAdminPage(Model model) {
		return "administration.html";
	}
	
	@GetMapping("/index")
	public String getHomePage(Model model) {
		return "index";
	}
}
