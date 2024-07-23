package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Location;
import com.itwill.springboot3.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
	
	private final LocationService locationService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		List<Location> list = locationService.read();
		model.addAttribute("list", list);
		
	}
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id") Integer id, Model model) {
		log.info("details(id={})");
		Location location = locationService.read(id);
		model.addAttribute("location", location);
		return "/location/details";
	}

}
