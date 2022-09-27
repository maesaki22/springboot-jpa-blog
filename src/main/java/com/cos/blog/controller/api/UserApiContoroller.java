package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController		// data 리턴
public class UserApiContoroller {
	
	@Autowired
	private UserService userService;
	
	// 회원가입 버튼이 눌러지면 user.js에서 호출( POST / url: /api/user  :: data를 json으로 보내 User 객체에 담아준다.
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		//System.out.println("save 호출됨");
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
