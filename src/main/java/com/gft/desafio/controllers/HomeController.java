package com.gft.desafio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("mensagem", "Tudo QUASE Gostoso");
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
