package com.raktar2.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raktar2.entities.Rendeles;
import com.raktar2.entities.User;
import com.raktar2.repository.RendelesRepository;
import com.raktar2.repository.UserRepository;
import com.raktar2.service.UserService;

@Controller
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RendelesRepository rendelesRepo;
	
	
	@RequestMapping("/")
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getName()!="anonymousUser") model.addAttribute("loggedin", userService.findByEmail(auth.getName()).getName());
		return "index";
	}
	
	@RequestMapping("/registration")
	public String registration(Model model) {
//		model.addAttribute("jelszohiba", "");
//		model.addAttribute("letezoemail", "");
	//	model.addAttribute("sikeresreg", "");
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@RequestMapping("/belep")
	public String login() {
		return "auth/login";
	}
	
	@RequestMapping("/datas")   // BETÖLTI AZ ADATAIM OLDALT
	public String datas(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user", userService.findByEmail(auth.getName()));
		return "datas";
	}
	
	@RequestMapping("/datepicker")
	public String datepicker() {
		return "datepicker";
	}
	
	@RequestMapping("/datesuccess")
	public String datesuccess() {
		return "datesuccess";
	}
	
	@RequestMapping("/history")
	public String history(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<Rendeles> rendlist = rendelesRepo.findAllByUser(userService.findByEmail(auth.getName()));
		log.info("Rendelés méret: "+rendlist.size());
		
		model.addAttribute("rendeles",  rendlist);
		return "history";
	}
	
}
