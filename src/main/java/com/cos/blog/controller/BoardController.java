package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	@GetMapping({"","/"})
	public String index() {
		return "index";	// yml 에 앞뒤 주소가 붙는다.
	}
}
