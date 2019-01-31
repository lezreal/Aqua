package com.raktar2.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.raktar2.entities.Rendeles;
import com.raktar2.entities.User;
import com.raktar2.repository.UserRepository;
import com.raktar2.service.EmailService;
import com.raktar2.service.UserService;

@Controller
public class JobController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;

	
	
	String email= "kadas.laszlo@gmail.com";
	
	@RequestMapping("/dataToReg")   // ez az ADATAIM oldalról jön
	public String datatoreg(@ModelAttribute("user") User user, @RequestParam("county") String city, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info(user.getName());
		
		log.info(user.getPhone());
		log.info(user.getStreet());
		log.info(user.getComment());
		
		User u = userService.findByEmail(auth.getName());
		u.setName(user.getName());
		//log.info(user.getCity());
		
		if (city.equals("n") && (user.getCity().equals("") || user.getCity()==null)) {
			model.addAttribute("badcity", "");
			return "datas";
		} else {
		
			if (!city.equals("n")) u.setCity(city); else u.setCity(user.getCity()); 
			
			
//		if (!user.getCity().isEmpty()) {
//			log.info("GET CITY: "+user.getCity());
//			u.setCity(user.getCity());
//		} else {
//			log.info("SIMA CITY: "+city); 
//			u.setCity(city);
//		}
		
		u.setComment(user.getComment());
		u.setPhone(user.getPhone());
		u.setStreet(user.getStreet());
		
		userService.addUserToDb(u);
		model.addAttribute("adatokok", "");
		return "index";
		}
	}
	

	@RequestMapping("/regToDb")
	public String regToDb(@ModelAttribute("user") User user, @RequestParam("repass") String password2, Model model) {

		log.info(user.getEmail());
		log.info(user.getPassword());
		log.info(password2);

		if (!user.getPassword().equals(password2)) {
			model.addAttribute("jelszohiba", "");
		} else {
			log.debug("Itt járok");
			model.addAttribute("sikeresreg", "");
			user.setPassword("{noop}" + user.getPassword());
			String code = act_code();
			user.setActivator(code);
			emailService.sendMessage(user.getEmail(), code);
			userService.addUserToDb(user);

		}

		return "registration";
	}
	
	
	@RequestMapping(path="/activation/{code}")
	public String activation(@PathVariable("code") String code, Model model) {
		User u = userService.findByActivator(code);
		if (u==null) model.addAttribute("acthiba", "Sajnáljuk, nem találtunk ilyen regisztrációt!");
		else {
			model.addAttribute("actcorrect", "");
			u.setEnabled(true);
			u.setActivator("");
			userService.addUserToDb(u);
		}
		return "activation";
	}
	

	@GetMapping("/datum")
	public String datum(Model model, @RequestParam("date") String date, @RequestParam("idoponttol") String idoponttol, @RequestParam("idopontig") String idopontig) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("ITT JÁROK");
		if (Integer.parseInt(idoponttol.substring(0, 2))<Integer.parseInt(idopontig.substring(0, 2))) log.info("KORREKT"); else  
		{
			model.addAttribute("rosszidopont", "");
			return "datepicker";
		}
		
		if (date==null || date.isEmpty()) {
			model.addAttribute("rosszdatum", "");
			return "datepicker";
		}
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String mai = df.format(new Date());  // mai dátum
		log.info("Mai dátum: "+mai);   		
		log.info("Kért dátum: "+date);
		// LE KELL KEZELNI AZ AZNAPI DÁTUMOT
		if (datumcheck(mai, date)==0) 
		{
			log.info("UGYANAZ A NAP");
			if (LocalTime.now().isAfter( LocalTime.parse( "08:40") )) {
				log.info("MÁR késő");
				model.addAttribute("mainapnemjo", "");
				return "datepicker";
			} else log.info("FASZA");
			
		}
			else 
			if (datumcheck(mai, date)==1) log.info("NAGYOBB AZ ÚJ NAP"); else {
				model.addAttribute("rosszdatum", "");
				log.info("ROSSZ A DÁTUM");
				return "datepicker";
			}
				
		
		log.info(idoponttol);
		log.info(idopontig);
		
		String uj_date=date.substring(6)+"/"+date.substring(0, 5);
		
		model.addAttribute("datum", ""+uj_date);
		model.addAttribute("idotartomany", ""+idoponttol+" - "+idopontig);
		model.addAttribute("idotartomanyig", idopontig);
		
		String city =userService.findByEmail(auth.getName()).getCity().toUpperCase();
		String street =userService.findByEmail(auth.getName()).getStreet().toUpperCase();
		
		model.addAttribute("szallcim", city+", "+street);
		model.addAttribute("comment", userService.findByEmail(auth.getName()).getComment());
		
		return "datesuccess";
	}
	
	@GetMapping("/datumfix")   // megrendelés rögzítése a STÁTUSZ kijelzés után
	public String datum(@RequestParam("datum") String date, @RequestParam("ido") String ido, @RequestParam("szall_cim_uj") String szall_cim_uj, @RequestParam("comment_uj") String comment_uj) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Rendeles rend = new Rendeles();
		rend.setDate(date);
		rend.setUser(userService.findByEmail(auth.getName()));
		rend.setTime(ido);
		rend.setComment(comment_uj);
		rend.setAddress(szall_cim_uj);
		
		
		userService.addRendelesToDb(rend);
		return "index";
	}
	
	
public byte datumcheck(String mai, String holnapi) {
		byte result;
		StringBuffer maid = new StringBuffer();
		StringBuffer holnapid = new StringBuffer();
		maid.append(mai.substring(6)).append(mai.substring(0,2)).append(mai.substring(3, 5));
		holnapid.append(holnapi.substring(6)).append(holnapi.substring(0,2)).append(holnapi.substring(3, 5));
		if (maid.toString().compareTo(holnapid.toString())<0) return 1; else
		if (maid.toString().compareTo(holnapid.toString())==0) return 0; else
		return 2;
	}

	private String act_code() {

		char[] tomb = new char[16];
		Random rng = new Random();
		for (int i = 0; i < tomb.length; i++) {
			tomb[i] = (char) ('a' + rng.nextInt(26));
		}

		String code = new String(tomb);

		return code;
	}

}
