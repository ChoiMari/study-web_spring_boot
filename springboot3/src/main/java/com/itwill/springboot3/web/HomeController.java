package com.itwill.springboot3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	
    @GetMapping("/")
    public String home() {
        log.info("home()");
        
        return "index";
    }
    
    //내가 한 것
	@GetMapping("/home")
	public String home2() {
		
		return "home";
	}

}
