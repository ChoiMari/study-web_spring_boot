package com.itwill.springboot3.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.springboot3.domain.Job;
import com.itwill.springboot3.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
	private final JobService jobService;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list()");
		
		List<Job> list = jobService.read();
		model.addAttribute("list",list);
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable(name = "id") String id, Model model) {
		log.info("details(id={})");
		 Job job = jobService.read(id);
		 model.addAttribute("job", job);
		return "/job/details";
	}
}
