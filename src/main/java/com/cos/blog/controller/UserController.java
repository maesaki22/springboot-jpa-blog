package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 밑에 /auth는 후 에 인증이 안된 사용자들이 출입할수 있는 경로를 /auth/X 허용
// 주소가  /이면 index.jsp 접근 허용
// static 이하에 있는 /js/ , /css , /image 쪽을 접근허용

@Controller
public class UserController {
	// http://localhost:8000/blog/user/joinForm
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm"; 		//http://localhost:8000/blog/user/joinForm.jsp
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
}
