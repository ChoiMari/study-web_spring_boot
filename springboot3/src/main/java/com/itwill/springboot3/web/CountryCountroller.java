package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Country;
import com.itwill.springboot3.service.CountryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryCountroller {
	
	private final CountryService countryService;

	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		List<Country> list = countryService.read();
		
		model.addAttribute("list", list);
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id")String id, Model model) {
		log.info("details(id={})",id);
		Country country= countryService.read(id);
		model.addAttribute("country", country);
		
		return "/country/details";
	}
	
}
