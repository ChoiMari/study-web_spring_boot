package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Region;
import com.itwill.springboot3.service.RegionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {
	
	private final RegionService regionService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		List<Region> list = regionService.read();
		model.addAttribute("list", list);
		
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id")Integer id, Model model) {
		log.info("details(id={})",id);
		
		Region region = regionService.read(id);
		
		model.addAttribute("region", region);
		
		return "/region/details";
		
	}

}
